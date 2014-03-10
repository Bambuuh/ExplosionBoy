package explosionBoy.client;

import java.util.ArrayList;

public class Json {
	
	private int power, gID, pID;
	private float xPos, yPos, speed, bCountDown;
	private String direction = "";
	private ArrayList<Json> jArr = new ArrayList<>();
	
	
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
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public int getgID() {
		return gID;
	}
	public void setgID(int gID) {
		this.gID = gID;
	}
	public int getpID() {
		return pID;
	}
	public void setpID(int pID) {
		this.pID = pID;
	}
	public float getbCountDown() {
		return bCountDown;
	}
	public void setbCountDown(float bCountDown) {
		this.bCountDown = bCountDown;
	}
	public ArrayList<Json> getjArr() {
		return jArr;
	}
	public void setjArr(ArrayList<Json> jArr) {
		this.jArr = jArr;
	}

	
}
