package explosionBoy.server;

import java.awt.Rectangle;

public class LevelObject {

	private int x, y;
	private Rectangle rectangle;
	private boolean box, buff;
	
	
	public LevelObject(int x, int y, boolean haveRectangle, boolean isBox) {
		this.setX(x);
		this.setY(y);
		if (haveRectangle) {
			rectangle = new Rectangle(x, y, 32, 32);
			if (isBox) {
				box = isBox;
			}
		}
	}
	


	public Rectangle getRectangle() {
		return rectangle;
	}


	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}


	public boolean isBox() {
		return box;
	}


	public void setBox(boolean box) {
		this.box = box;
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



	public boolean isBuff() {
		return buff;
	}



	public void setBuff(boolean buff) {
		this.buff = buff;
	}
	
}
