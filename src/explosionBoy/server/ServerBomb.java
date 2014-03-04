package explosionBoy.server;

import java.awt.Rectangle;

public class ServerBomb {
	
	private Rectangle rect;
	private int x, y, playerID;
	
	public ServerBomb(int playerX, int playerY, int playerID) {
		x = calcPosX(playerX);
		y = calcPosY(playerY);
		this.playerID = playerID;
		rect = new Rectangle(x, y, 32, 32);
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
	
}
