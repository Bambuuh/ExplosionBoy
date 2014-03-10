package explosionBoy.client;


import org.lwjgl.input.Keyboard;

import com.google.gson.Gson;

public class InputReader {
	
	private Json json;
	private ServerConnection connection;
	private boolean bombDown;
	
	public InputReader(ServerConnection connection) {
		this.connection = connection;
		json = new Json();
		
	}
	
	public void readInput(int playerID){
//		if (Keyboard.isKeyDown(Keyboard.KEY_UP) && (Keyboard.isKeyDown(Keyboard.KEY_LEFT))) json.setDirection("UPLEFT");
//		else if (Keyboard.isKeyDown(Keyboard.KEY_UP) && (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))) json.setDirection("UPRIGHT");
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) json.setDirection("UP");
		
//		else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN) && Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) json.setDirection("DOWNRIGHT");
//		else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN) && Keyboard.isKeyDown(Keyboard.KEY_LEFT)) json.setDirection("DOWNLEFT");
		else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) json.setDirection("DOWN");
		else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) json.setDirection("LEFT");
		else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) json.setDirection("RIGHT");
		else json.setDirection("STILL");
		json.setpID(playerID);
		Gson g = new Gson();
		g.toJson(json, Json.class);
		connection.send(json);
		//TODO: Solve this in a better way later!
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !bombDown){
			json.setDirection("BOMB");
			connection.send(json);
			bombDown = true;
		}
		else if (!Keyboard.isKeyDown(Keyboard.KEY_SPACE) && bombDown) {
			bombDown = false;
		}
	}
}
