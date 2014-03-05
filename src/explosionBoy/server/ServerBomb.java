package explosionBoy.server;

import java.awt.Rectangle;

public class ServerBomb {
	
	private Rectangle rect;
	private int x, y, playerID, countDown;
	private long dropTime;
	private boolean remove, isColliding;
	
	public ServerBomb(int playerX, int playerY, int playerID, int countDown) {
		x = calcPosX(playerX);
		y = calcPosY(playerY);
		this.playerID = playerID;
		this.countDown = countDown;
		this.setRemove(false);
		this.isColliding = true;
		rect = new Rectangle(x, y, 32, 32);
		dropTime = System.currentTimeMillis();
	}

	private int calcPosY(int playerY) {
		for (int i = (int) ((int)playerY+25); i > 0; i--) {
			if (i%32 == 1) {
				return i-1;
			}
		}
		return 0;
	}

	private int calcPosX(int playerX) {
		for (int i = (int)playerX+5; i > 0; i--) {
			if (i%32 == 1) {
				return i-1;
			}
		}
		return 0;
	}
	
	public boolean checkIfRemove(){
		if (System.currentTimeMillis()>=dropTime+(countDown*1000)) {
			System.out.println("REMOVING BOMB!");
			setRemove(true);
			return true;
		}
		return false;
	}
	
	public Rectangle getRect() {
		return rect;
	}
	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public int getCountDown() {
		return countDown;
	}

	public void setCountDown(int countDown) {
		this.countDown = countDown;
	}

	public long getDropTime() {
		return dropTime;
	}

	public void setDropTime(long dropTime) {
		this.dropTime = dropTime;
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public boolean isColliding() {
		return isColliding;
	}

	public void setColliding(boolean isColliding) {
		this.isColliding = isColliding;
	}
	
}
