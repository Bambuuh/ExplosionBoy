package explosionBoy.server;

import java.net.InetAddress;
import java.net.UnknownHostException;

import explosionBoy.client.Delta;
import explosionBoy.client.Game;

public class ConnectionReference {

	private InetAddress ip = null;
	private int conId, port, gID, pID;
	private float xPos, yPos, speed;
	private String dir;
	private Delta delta;
	
	public ConnectionReference() {
		delta = new Delta();
		delta.getDelta();
		this.xPos = 40;
		this.yPos = 30;
		this.speed = 0.5f;
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println("Getting local host failed: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public ConnectionReference(int pID) {
		delta = new Delta();
		delta.getDelta();
		if (pID == 2) {
			this.xPos = Game.WIDTH-50;
			this.yPos = 30;
		}
		else {
			this.xPos = 40;
			this.yPos = 30;
		}
		this.pID = pID;
		this.speed = 1;
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println("Getting local host failed: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public InetAddress getIp() {
		return ip;
	}
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}
	public int getConId() {
		return conId;
	}
	public void setConId(int conId) {
		this.conId = conId;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getgID() {
		return gID;
	}
	public void setgID(int gID) {
		this.gID = gID;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public float getyPos() {
		return yPos;
	}
	public void setyPos(float yPos) {
		this.yPos = yPos;
	}
	public float getxPos() {
		return xPos;
	}
	public void setxPos(float xPos) {
		this.xPos = xPos;
	}
	public int getpID() {
		return pID;
	}
	public void setpID(int pID) {
		this.pID = pID;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		switch (dir) {
		case "UP":
			this.yPos -= speed*delta.getDelta();
			break;
		case "UPRIGHT":
			this.yPos -= speed*delta.getDelta();
			this.xPos += speed*delta.getDelta();
			break;
		case "UPLEFT":
			this.yPos -= speed*delta.getDelta();
			this.xPos -= speed*delta.getDelta();
			break;
		case "DOWN":
			this.yPos += speed*delta.getDelta();
			break;
		case "DOWNRIGHT":
			this.yPos += speed*delta.getDelta();
			this.xPos += speed*delta.getDelta();
			break;
		case "DOWNLEFT":
			this.yPos += speed*delta.getDelta();
			this.xPos -= speed*delta.getDelta();
			break;
		case "RIGHT":
			this.xPos += speed*delta.getDelta();
			break;
		case "LEFT":
			this.xPos -= speed*delta.getDelta();
			break;
		default:
			break;
		}
		this.dir = dir;
	}
	
}
