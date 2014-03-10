package explosionBoy.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import org.newdawn.slick.Image;

import com.google.gson.Gson;

import explosionBoy.levelobjects.LevelObject;

public class ServerTCP implements Runnable {

	private Socket serverCon;
	private PrintWriter out;
	private BufferedReader in;
	private boolean connected;
	private String conMsg;
	private Game game;
	private Gson gson;

	public ServerTCP(Game game) {
		gson = new Gson();
		this.game = game;
		try {
			serverCon = new Socket("localhost", 9877);
			out = new PrintWriter(serverCon.getOutputStream());
			in = new BufferedReader(new InputStreamReader(serverCon.getInputStream()));
			connected = true;
		} catch (IOException e) {
			System.err.println("Could not create connection: "+e.getMessage());
			try {
				serverCon.close();
				out.close();
				in.close();
			} catch (IOException e1) {
				System.err.println("Could not close connection "+e1.getMessage());
			}
		}
		if (!out.checkError()) {
			conMsg = "Connected to server!";
		}
		else {
			conMsg = "Could not connect to server! :(";
		}
		System.out.println(conMsg);
	}
	private void recive(){
		String msgIn = null;
		try {
			msgIn = in.readLine();
			if (msgIn != null) {
				Json json = gson.fromJson(msgIn, Json.class);
				handleJson(json);
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
		while (!serverCon.isClosed()) {
			recive();
		}
		try {
			in.close();
		} catch (IOException e) {
			System.err.println("Error closing instream"+e.getMessage());
		}
		System.out.println("Thread Closed");
	}

	public void handleJson(Json json){
		switch (json.getDirection()) {
		case "PSETTINGS":
			game.setPlayerID(json.getpID());
			game.getConnection().setGameID(json.getgID());
			break;
		case "BOXARRAY":
			ArrayList<LevelObject> boxArray = new ArrayList<LevelObject>();
			Image img = game.getAnimation().getTiles().getSubImage(128, 0, 32, 32);
			for (Json box : json.getjArr()) {
				boxArray.add(new LevelObject(img, box.getxPos(), box.getyPos(), true, true, false));
			}
			System.out.println("ArraySize: "+boxArray.size());
			game.getLevel().getObjectsToAdd().addAll(boxArray);
			break;
		case "NEWPLAYER":
			boolean isNewPlayer = true;
			for (Controller controller : game.getControllArray()) {
				if (controller.id==json.getpID()) {
					isNewPlayer=false;
					System.out.println("SETTING UP NEW PLAYER! :D");
					break;
				}
			}
			if (isNewPlayer) {
				SnakeBoy snake = new SnakeBoy(40, 40, game.getAnimation(), json.getpID());
				game.getControllArray().add(new Controller(snake, game.getBombArray(), game.getAddBombArray()));
			}
		default:
			break;
		}
	}
	public void closeAllConnections(){
		try {
			serverCon.close();
			out.close();
			in.close();
			System.out.println("Connections closed!");
		} 
		catch (Exception e) {
			System.err.println("Could not close connection: "+e.getMessage());		
		}
	}
}
