package explosionBoy.client;


public class Controller {

	int id, delta;
	Player player;
	private String lastDirection;
	private float speed;

	public Controller(Player player) {
		this.lastDirection = "";
		this.player = player;
		this.id = player.ID;
		System.out.println("PLayer ID: " + player.ID + " MY ID: " + id);
	}

	public void controll(Json json){
		speed = json.getSpeed();
		lastDirection = json.getDirection();
		player.json = json;

		if (!json.getDirection().equals(lastDirection) || lastDirection.equals("")) {
			lastDirection = json.getDirection();
			switch (json.getDirection()) {
			case "UP":
				if (json.getyPos() < player.getY()) {
					player.setY(json.getyPos());
				}
				break;

			case "DOWN":
				if (json.getyPos() > player.getY()) {
					player.setY(json.getyPos());
				}
				break;

			case "RIGHT":
				if (json.getxPos() > player.getX()) {
					player.setX(json.getxPos());
				}
				break;

			case "LEFT":
				if (json.getxPos() < player.getX()) {
					player.setX(json.getxPos());
				}
				break;

			default:
				break;
			}
		}
	}
	
	public void autoMove(){
		if (lastDirection.equals("UP")) player.move(0,-1,speed,delta);
		else if (lastDirection.equals("DOWN")) player.move(0,1,speed,delta);
		else if (lastDirection.equals("RIGHT")) player.move(1,0,speed,delta);
		else if (lastDirection.equals("LEFT")) player.move(-1,0,speed,delta);
		else if (lastDirection.equals("UPRIGHT")) player.move(1,-1,speed,delta);
		else if (lastDirection.equals("DOWNRIGHT")) player.move(1,1,speed,delta);
		else if (lastDirection.equals("UPLEFT")) player.move(-1,-1,speed,delta);
		else if (lastDirection.equals("DOWNLEFT")) player.move(-1,1,speed,delta);
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
}
