package explosionBoy.client;

import org.lwjgl.input.Keyboard;

public class Controller {

	int id;
	Player player;
	
	public Controller(Player player) {
		this.player = player;
	}
	
	public void controll(Json json){
		if (json.getDirection()==Direction.UP) player.move(0,1);
		else if (json.getDirection()==Direction.DOWN) player.move(0,-1);
		else if (json.getDirection()==Direction.RIGHT) player.move(1,0);
		else if (json.getDirection()==Direction.LEFT) player.move(-1,0);
		else player.move(0, 0);
		
	}
}
