package explosionBoy.server;

public class Json {
	
	private int connectionID;
	private float xPos, yPos, speed;
	private Direction direction;
	
	
	public float getxPos() {
		return xPos;
	}
	public void setxPos(float xPos) {
		this.xPos = xPos;
	}
	public float getyPos() {
		return yPos;
	}
	public void setyPos(float yPos) {
		this.yPos = yPos;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public int getConnectionID() {
		return connectionID;
	}
	public void setConnectionID(int accountID) {
		this.connectionID = accountID;
	}
	

	
}
