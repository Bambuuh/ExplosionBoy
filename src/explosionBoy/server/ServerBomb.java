package explosionBoy.server;

import java.awt.Rectangle;
import java.util.ArrayList;

public class ServerBomb {

	private Rectangle rect;
	private int x, y, playerID, countDown, explosionPower, powerCounter;
	private long dropTime, explosionTime;
	private boolean remove, isColliding, exploding, eLeft, eRight, eUp, eDown;
	private float explosionSpeed;
	private ArrayList<ServerExplosion> explArray;
	private ArrayList<ServerExplosion> removeExplosionArray;

	public ServerBomb(int playerX, int playerY, int playerID, int countDown, float explosionSpeed, int explosionPower) {
		this.explArray = new ArrayList<>();
		removeExplosionArray = new ArrayList<ServerExplosion>();
		x = calcPosX(playerX);
		y = calcPosY(playerY);
		this.eLeft = true;
		this.eRight = true;
		this.eUp = true;
		this.eDown = true;
		this.playerID = playerID;
		this.countDown = countDown;
		this.exploding = false;
		this.setRemove(false);
		this.isColliding = true;
		this.powerCounter = 1;
		this.setExplosionSpeed(explosionSpeed);
		this.explosionPower = explosionPower;
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
		for (ServerExplosion explosion : explArray) {
			explosion.update();
			if (explosion.isRemove()) {
				removeExplosionArray.add(explosion);
			}
		}
		for (ServerExplosion explosion : removeExplosionArray) {
			explArray.remove(explosion);
		}
		removeExplosionArray.clear();
		if (System.currentTimeMillis()>=dropTime+(countDown*1000) && !exploding) {
			exploding = true;
			explosionTime = System.currentTimeMillis()+150;
		}
		if (exploding && powerCounter <= explosionPower && System.currentTimeMillis()>=explosionTime){
			if (eRight) {
				//RIGHT
				explArray.add(new ServerExplosion(x+(powerCounter*32), y, "RIGHT"));
			}
			if (eLeft) {
				//LEFT
				explArray.add(new ServerExplosion(x-(powerCounter*32), y,"LEFT"));
			}
			if (eUp) {
				//UP
				explArray.add(new ServerExplosion(x, y-(powerCounter*32),"UP"));
			}
			if (eDown) {
				//DOWN
				explArray.add(new ServerExplosion(x,  y+(powerCounter*32),"DOWN"));
			}
			powerCounter++;
			explosionTime =  (long) (System.currentTimeMillis()+150);
			System.out.println(explosionTime - System.currentTimeMillis());
			System.out.println((explosionSpeed));

		}
		else if(exploding && explArray.isEmpty()){
			return true;
		}
		return false;
	}

	public void cancelDirection(String direction){
		switch (direction) {
		case "UP":
			eUp = false;
			break;
		case "DOWN":
			eDown = false;
			break;
		case "LEFT":
			eLeft = false;
			break;
		case "RIGHT":
			eRight = false;
			break;
		default:
			break;
		}
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

	public float getExplosionSpeed() {
		return explosionSpeed;
	}

	public void setExplosionSpeed(float explosionSpeed) {
		this.explosionSpeed = explosionSpeed;
	}

	public int getExplosionPower() {
		return explosionPower;
	}

	public void setExplosionPower(int explosionPower) {
		this.explosionPower = explosionPower;
	}

	public ArrayList<ServerExplosion> getExplArray() {
		return explArray;
	}

	public void setExplArray(ArrayList<ServerExplosion> explArray) {
		this.explArray = explArray;
	}

}
