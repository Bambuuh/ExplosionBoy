package explosionBoy.server;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnectionReference {

	private InetAddress ip = null;
	private int conId, port, gID, pID;
	private float xPos, yPos, speed;
	private String dir;
	
	public ConnectionReference() {
		this.xPos = 40;
		this.yPos = 30;
		this.speed = 1;
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println("Getting local host failed: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public ConnectionReference(int pID) {
		this.pID = pID;
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
			this.yPos -= speed;
			break;
		case "UPRIGHT":
			this.yPos -= speed;
			this.xPos += speed;
			break;
		case "UPLEFT":
			this.yPos -= speed;
			this.xPos -= speed;
		case "DOWN":
			this.yPos += speed;
			break;
		case "DOWNRIGHT":
			this.yPos += speed;
			this.xPos += speed;
			break;
		case "DOWNLEFT":
			this.yPos += speed;
			this.xPos -= speed;
			break;
		case "RIGHT":
			this.xPos += speed;
			break;
		case "LEFT":
			this.xPos -= speed;
			break;
		default:
			break;
		}
		this.dir = dir;
	}
	
}
