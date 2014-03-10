package explosionBoy.levelobjects;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class LevelObject {

	private Image image;
	private Rectangle rectangle;
	private float x,y;
	private boolean haveRectangle;
	private boolean box;
	private boolean unbreakable;
	
	public LevelObject(Image image, float xPos, float yPos, boolean haveRectangle, boolean box, boolean unbreakable) {
		this.box = box;
		this.unbreakable = unbreakable;
		this.setHaveRectangle(haveRectangle);
		this.x = xPos;
		this.y = yPos;
		this.setImage(image);
		if (haveRectangle) {
			this.setRectangle(new Rectangle(xPos, yPos, image.getWidth(), image.getHeight()));
		}
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public void draw(){
		this.image.draw(x,y);
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public boolean isHaveRectangle() {
		return haveRectangle;
	}

	public void setHaveRectangle(boolean haveRectangle) {
		this.haveRectangle = haveRectangle;
	}

	public boolean isBox() {
		return box;
	}

	public void setBox(boolean box) {
		this.box = box;
	}

	public boolean isUnbreakable() {
		return unbreakable;
	}

	public void setUnbreakable(boolean unbreakable) {
		this.unbreakable = unbreakable;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
		
}
