package explosionBoy.server;

import java.awt.Rectangle;

public class ServerExplosion {

	private Rectangle rect;
	private int x, y;
	private boolean remove;
	private float explosionTime;
	private long startTime, lastTime;
	private String direction;
	
	public ServerExplosion(int x, int y, String direction) {
		startTime = System.currentTimeMillis();
		explosionTime = 400;
		rect = new  Rectangle(x+1, y+1, 30, 30);
		this.direction = direction;
//		System.out.println("DIR: "+direction);
//		System.out.println("X: "+x);
//		System.out.println("Y: "+y);
	}
	
	public void update(){
		if (countDown()<explosionTime) {
			remove = true;
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
		return startTime;
	}
	public void setCurrentTime(long currentTime) {
		this.startTime = currentTime;
	}
	public long getLastTime() {
		return lastTime;
	}
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public double countDown(){
		long tEnd = System.currentTimeMillis();
		long tDelta = tEnd - startTime;
		double elapsedSeconds = tDelta;
		return elapsedSeconds;
	}
}
