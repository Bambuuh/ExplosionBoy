package explosionBoy.client;


public class Controller {

	int id;
	Player player;
	
	public Controller(Player player) {
		this.player = player;
		this.id = player.ID;
		System.out.println("PLayer ID: " + player.ID + " MY ID: " + id);
	}
	
	public void controll(Json json){
		float speed = json.getSpeed();
		if (json.getDirection().equals("UP")) player.move(0,-1,speed);
		else if (json.getDirection().equals("DOWN")) player.move(0,1,speed);
		else if (json.getDirection().equals("RIGHT")) player.move(1,0,speed);
		else if (json.getDirection().equals("LEFT")) player.move(-1,0,speed);
		else if (json.getDirection().equals("UPRIGHT")) player.move(1,-1,speed);
		else if (json.getDirection().equals("DOWNRIGHT")) player.move(1,1,speed);
		else if (json.getDirection().equals("UPLEFT")) player.move(-1,-1,speed);
		else if (json.getDirection().equals("DOWNLEFT")) player.move(-1,1,speed);
		else player.move(0, 0,0);
		player.json = json;
	}
}
