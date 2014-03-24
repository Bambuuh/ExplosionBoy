package explosionBoy.client;

import java.util.ArrayList;

import explosionBoy.textHandler.TextHandler;

public class BorderDisplay {
	
	private int playerSpace = 120;
	private int lifeSpace = 50;
	
	
	private int p1Start = 30;
	private int p2Start = p1Start + playerSpace;
	private int p3Start = p2Start + playerSpace;
	private int p4Start = p3Start + playerSpace;

	private ArrayList<Controller> playerArray;
	private TextHandler text;
	
	public BorderDisplay(ArrayList<Controller> controllerArray, AnimationHandler animation) {
		playerArray = controllerArray;
		text = new TextHandler(animation);
		
	}
	
	public void update(){
		
	}
	
	public void render(){
		displayStats();
	}
	
	public void displayStats(){
		
		for (Controller player : playerArray) {
			if (player.getPlayer().getID() == 1) {
				text.drawText("p1", p1Start, Game.BOARDHEIGHT+27);
				
				for (int i = 0; i < player.getPlayer().getHealth(); i++) {
					player.getPlayer().getFace().draw((p1Start+lifeSpace)+i*(player.getPlayer().getFace().getWidth()+5), Game.BOARDHEIGHT+32);
				}
			}
			if (player.getPlayer().getID() == 2) {
				text.drawText("p2", p1Start, Game.BOARDHEIGHT+27);
				
				for (int i = 0; i < player.getPlayer().getHealth(); i++) {
					player.getPlayer().getFace().draw((p2Start+lifeSpace)+i*(player.getPlayer().getFace().getWidth()+5), Game.BOARDHEIGHT+32);
				}
			}
			if (player.getPlayer().getID() == 3) {
				text.drawText("p3", p1Start, Game.BOARDHEIGHT+27);
				
				for (int i = 0; i < player.getPlayer().getHealth(); i++) {
					player.getPlayer().getFace().draw((p3Start+lifeSpace)+i*(player.getPlayer().getFace().getWidth()+5), Game.BOARDHEIGHT+32);
				}
			}
			if (player.getPlayer().getID() == 4) {
				text.drawText("p4", p1Start, Game.BOARDHEIGHT+27);
				
				for (int i = 0; i < player.getPlayer().getHealth(); i++) {
					player.getPlayer().getFace().draw((p4Start+lifeSpace)+i*(player.getPlayer().getFace().getWidth()+5), Game.BOARDHEIGHT+32);
				}
			}
			
		}
	}
}
