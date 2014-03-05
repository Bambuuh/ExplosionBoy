package explosionBoy.server;

import java.awt.Rectangle;

public class ServerExplosion {

	private Rectangle rect;
	private int x, y;
	private boolean remove;
	private float explosionTime;
	private long currentTime, lastTime;
	
	public ServerExplosion() {
		
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
	public boolean isRemove() {
		return remove;
	}
	public void setRemove(boolean remove) {
		this.remove = remove;
	}
	public float getExplosionTime() {
		return explosionTime;
	}
	public void setExplosionTime(float explosionTime) {
		this.explosionTime = explosionTime;
	}
	public long getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}
	public long getLastTime() {
		return lastTime;
	}
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}
	
}
