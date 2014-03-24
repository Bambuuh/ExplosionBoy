package explosionBoy.client;

import java.util.ArrayList;

public class BorderDisplay {

	private ArrayList<Controller> playerArray;
	
	public BorderDisplay(ArrayList<Controller> controllerArray) {
		playerArray = controllerArray;
		
	}
	
	public void update(){
		
	}
	
	public void render(){
		displayStats();
	}
	
	public void displayStats(){
		
		for (Controller player : playerArray) {
			player.getPlayer().getFace().draw(32, Game.BOARDHEIGHT+32);
		}
		
	}
	
	
}
