package explosionBoy.levelobjects;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class LevelObject {

	private Image image;
	private Rectangle rectangle;
	private float x,y;
	
	public LevelObject(Image image, float xPos, float yPos) {
		this.x = xPos;
		this.y = yPos;
		this.setImage(image);
		this.rectangle = new Rectangle(xPos, yPos, image.getTextureWidth(), image.getTextureHeight());
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
}
