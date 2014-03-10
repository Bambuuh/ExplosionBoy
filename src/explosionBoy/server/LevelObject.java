package explosionBoy.server;

import java.awt.Rectangle;

public class LevelObject {

	int x, y;
	private Rectangle rectangle;
	private boolean box;
	
	
	public LevelObject(int x, int y, boolean haveRectangle, boolean isBox) {
		this.x = x;
		this.y = y;
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
	
}
