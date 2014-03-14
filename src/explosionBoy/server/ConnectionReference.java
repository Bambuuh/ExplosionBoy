package explosionBoy.server;

import java.awt.Rectangle;
import java.net.InetAddress;

import explosionBoy.client.Delta;
import explosionBoy.client.Game;

public class ConnectionReference {

	private InetAddress ip = null;
	private int conId, port, gID, pID, maxBombs, currentBombs, bombPower, bombCountdown;
	private float xPos, yPos, speed, oldY, oldX, explosionSpeed, baseX, baseY;
	private String dir;
	private Delta delta;
	private Rectangle playerRect;
	private GameHolder gameHolder;
	private boolean isAwayFromBomb;
	private TCPConnection tcpConnection;
	
	public ConnectionReference() {
		this.playerRect = new Rectangle();
		delta = new Delta();
		delta.getDelta();
		this.xPos = 40;
		this.yPos = 30;
		this.speed = 0.1f;
		this.ip = null;
	}
	
	public ConnectionReference(int pID, GameHolder gameHolder) {
		this.gameHolder = gameHolder;
		this.playerRect = new Rectangle();
		this.explosionSpeed = 0.07f;
		this.bombCountdown = 3;
		this.bombPower = 8;
		this.maxBombs = 1;
		delta = new Delta();
		delta.getDelta();
		if (pID == 2) {
			this.xPos = Game.WIDTH-60;
			this.yPos = 35;
		}
		else if (pID == 1) {
			this.xPos = 40;
			this.yPos = 30;
		}
		else if (pID == 3){
			this.xPos = 40;
			this.yPos = Game.HEIGHT-65;
		}
		else if (pID == 4){
			this.xPos = Game.WIDTH-60;
			this.yPos = Game.HEIGHT-65;
		}
		this.baseX = xPos;
		this.baseY = yPos;
		this.pID = pID;
		this.speed = 0.1f;
		this.ip = null;
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
		int deltish = delta.getDelta();
		this.oldX = xPos;
		this.oldY = yPos;
		switch (dir) {
		case "UP":
			this.yPos -= speed*deltish;
			break;
		case "UPRIGHT":
			this.yPos -= speed*deltish;
			this.xPos += speed*deltish;
			break;
		case "UPLEFT":
			this.yPos -= speed*deltish;
			this.xPos -= speed*deltish;
			break;
		case "DOWN":
			this.yPos += speed*deltish;
			break;
		case "DOWNRIGHT":
			this.yPos += speed*deltish;
			this.xPos += speed*deltish;
			break;
		case "DOWNLEFT":
			this.yPos += speed*deltish;
			this.xPos -= speed*deltish;
			break;
		case "RIGHT":
			this.xPos += speed*deltish;
			break;
		case "LEFT":
			this.xPos -= speed*deltish;
			break;
		default:
			break;
		}
		playerRect.setBounds((int)xPos+1, (int)yPos+20, 14, 9);
		gameHolder.checkCollissions(this);
		this.dir = dir;
	}
	
	public Rectangle getPlayerRect() {
		return playerRect;
	}

	public void setPlayerRect(Rectangle playerRect) {
		this.playerRect = playerRect;
	}

	public float getOldY() {
		return oldY;
	}

	public void setOldY(float oldY) {
		this.oldY = oldY;
	}

	public float getOldX() {
		return oldX;
	}

	public void setOldX(float oldX) {
		this.oldX = oldX;
	}

	public int getMaxBombs() {
		return maxBombs;
	}

	public void setMaxBombs(int maxBombs) {
		this.maxBombs = maxBombs;
	}

	public int getCurrentBombs() {
		return currentBombs;
	}

	public void setCurrentBombs(int currentBombs) {
		this.currentBombs = currentBombs;
	}

	public float getExplosionSpeed() {
		return explosionSpeed;
	}

	public void setExplosionSpeed(float explosionSpeed) {
		this.explosionSpeed = explosionSpeed;
	}

	public int getBombCountdown() {
		return bombCountdown;
	}

	public void setBombCountdown(int bombCountdown) {
		this.bombCountdown = bombCountdown;
	}

	public int getBombPower() {
		return bombPower;
	}

	public void setBombPower(int bombPower) {
		this.bombPower = bombPower;
	}

	public boolean isAwayFromBomb() {
		return isAwayFromBomb;
	}

	public void setAwayFromBomb(boolean isAwayFromBomb) {
		this.isAwayFromBomb = isAwayFromBomb;
	}
	
	public void resetPosition(){
		this.xPos = baseX;
		this.yPos = baseY;
	}

	public TCPConnection getTcpConnection() {
		return tcpConnection;
	}

	public void setTcpConnection(TCPConnection tcpConnection) {
		this.tcpConnection = tcpConnection;
	}
	
}
