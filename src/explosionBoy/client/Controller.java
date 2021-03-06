package explosionBoy.client;

import java.util.ArrayList;


public class Controller {

	int id, delta;
	Player player;
	private String moveDirection, lastDirection;
	private float speed;
	private  ArrayList<Bomb> bombArray, addBombArray;

	public Controller(Player player,  ArrayList<Bomb> bombArray, ArrayList<Bomb> addBombArray) {
		this.addBombArray = addBombArray;
		this.bombArray = bombArray;
		this.moveDirection = "";
		this.lastDirection = "";
		this.player = player;
		this.id = player.ID;
		System.out.println("PLayer ID: " + player.ID + " MY ID: " + id);
	}

	public void controll(Json json){
		speed = json.getSpeed();
		moveDirection = json.getDirection();
		player.json = json;

		if (!json.getDirection().equals(lastDirection) || lastDirection.equals("")) {
			moveDirection = json.getDirection();
			switch (json.getDirection()) {
			case "UP":
				player.setY(json.getyPos());
				break;

			case "DOWN":
				player.setY(json.getyPos());
				break;

			case "RIGHT":
				player.setX(json.getxPos());
				break;
			case "LEFT":
				player.setX(json.getxPos());
				break;
			case "BOMB":
				player.bombDropper(bombArray, addBombArray, json.getSpeed(), json.getbCountDown(), json.getPower());
				break;
			case "RESET":
				player.setX(json.getxPos());
				player.setY(json.getyPos());
				player.setOldx(json.getxPos());
				player.setOldy(json.getyPos());
				break;
			default:
				break;
			}
			lastDirection = json.getDirection();
		}
	}

	public void autoMove(){
		if (moveDirection.equals("UP")) player.move(0,-1,speed,delta);
		else if (moveDirection.equals("DOWN")) player.move(0,1,speed,delta);
		else if (moveDirection.equals("RIGHT")) player.move(1,0,speed,delta);
		else if (moveDirection.equals("LEFT")) player.move(-1,0,speed,delta);
		else if (moveDirection.equals("UPRIGHT")) player.move(1,-1,speed,delta);
		else if (moveDirection.equals("DOWNRIGHT")) player.move(1,1,speed,delta);
		else if (moveDirection.equals("UPLEFT")) player.move(-1,-1,speed,delta);
		else if (moveDirection.equals("DOWNLEFT")) player.move(-1,1,speed,delta);
		else player.move(0, 0, 0,delta);
	}
	public Player getPlayer(){
		return this.player;
	}

	public int getDelta() {
		return delta;
	}

	public void setDelta(int delta) {
		this.delta = delta;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
