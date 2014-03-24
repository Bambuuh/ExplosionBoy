package explosionBoy.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;

import explosionBoy.client.Json;


public class TCPConnection implements Runnable {

	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private Gson gson;
	private long time;
	private Json json;
	private boolean closeThisConnection;
	
	public TCPConnection(Socket socket) {
		this.json = new Json();
		this.closeThisConnection = false;
		time = System.currentTimeMillis();
		gson = new Gson();
		this.socket = socket;
		try {
			this.out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			System.err.println("Failed to get outputstream from socket: "+e.getMessage());
		}
		try {
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.err.println("Failed to get inputstream from socket: "+e.getMessage());
		}
	}
	
	private void recive(){
		String msgIn = null;
		try {
			msgIn = in.readLine();
			if (msgIn != null) {
				json = gson.fromJson(msgIn, Json.class);
			}
		} catch (IOException e) {
			System.err.println("ERROR reciving message: "+e.getMessage());
		}
	}
	
	public void send(String msg){
		out.println(msg);
		out.flush();
	}
	
	@Override
	public void run() {
		while (!out.checkError()) {
			recive();
			if (System.currentTimeMillis()>time+1000) {
				json.setDirection("PING");
				send(gson.toJson(json, Json.class));
				time = System.currentTimeMillis();
			}
		}
		closeThisConnection = true;
		closeConnections();
	}
	
	public void closeConnections(){
		out.close();
		try {
			in.close();
			socket.close();
			System.out.println("Connection to "+socket.getInetAddress()+" is closed!");
		} catch (IOException e) {
			System.err.println("Could not close connection: "+e.getMessage());
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}

	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public Gson getGson() {
		return gson;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}

	public boolean isCloseThisConnection() {
		return closeThisConnection;
	}

	public void setCloseThisConnection(boolean closeThisConnection) {
		this.closeThisConnection = closeThisConnection;
	}

}
