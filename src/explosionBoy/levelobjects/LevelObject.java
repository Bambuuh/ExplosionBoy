package explosionBoy.levelobjects;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class LevelObject {

	private Image image;
	private Rectangle rectangle;
	private float x,y;
	private boolean haveRectangle;
	
	public LevelObject(Image image, float xPos, float yPos, boolean haveRectangle) {
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
}
