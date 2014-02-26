package explosionBoy.client;

public class Json {
	
	private int conID;
	private float xPos, yPos, speed;
	private String direction = "";
	
	
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
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public int getConID() {
		return conID;
	}
	public void setConID(int conID) {
		this.conID = conID;
	}

	
}
