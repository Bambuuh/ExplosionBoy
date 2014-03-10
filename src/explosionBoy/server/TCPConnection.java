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
	
	public TCPConnection(Socket socket) {
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
				Json json = gson.fromJson(msgIn, Json.class);
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
		while (!socket.isClosed()) {
			recive();
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

}
