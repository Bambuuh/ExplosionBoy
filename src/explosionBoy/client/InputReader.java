package explosionBoy.client;

import org.lwjgl.input.Keyboard;

public class InputReader {
	
	private Json json;
	private ServerConnection connection;
	
	public InputReader(ServerConnection connection) {
		this.connection = connection;
		json = new Json();
		
	}
	
	public void readInput(){
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) json.setDirection(Direction.UP);
		else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) json.setDirection(Direction.DOWN);
		else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) json.setDirection(Direction.LEFT);
		else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) json.setDirection(Direction.RIGHT);
		else json.setDirection(Direction.STILL);
		
		connection.send(json);
	}
}
