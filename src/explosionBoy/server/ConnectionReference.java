package explosionBoy.server;

import java.net.InetAddress;

public class ConnectionReference {

	private InetAddress ip;
	private int conId, port, gID;
	private float xPos, yPos, speed;
	
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
	
}
