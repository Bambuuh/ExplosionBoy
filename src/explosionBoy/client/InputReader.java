package explosionBoy.client;


import org.lwjgl.input.Keyboard;

import com.google.gson.Gson;

public class InputReader {
	
	private Json json;
	private ServerConnection connection;
	
	public InputReader(ServerConnection connection) {
		this.connection = connection;
		json = new Json();
		
	}
	
	public void readInput(){
		if (Keyboard.isKeyDown(Keyboard.KEY_UP) && (Keyboard.isKeyDown(Keyboard.KEY_LEFT))) json.setDirection("UPLEFT");
		else if (Keyboard.isKeyDown(Keyboard.KEY_UP) && (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))) json.setDirection("UPRIGHT");
		else if (Keyboard.isKeyDown(Keyboard.KEY_UP)) json.setDirection("UP");
		
		else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN) && Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) json.setDirection("DOWNRIGHT");
		else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN) && Keyboard.isKeyDown(Keyboard.KEY_LEFT)) json.setDirection("DOWNLEFT");
		else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) json.setDirection("DOWN");
		else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) json.setDirection("LEFT");
		else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) json.setDirection("RIGHT");
		else json.setDirection("STILL");
		json.setpID(2);
		Gson g = new Gson();
		g.toJson(json, Json.class);
		connection.send(json);
	}
}
