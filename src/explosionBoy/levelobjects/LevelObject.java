package explosionBoy.levelobjects;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class LevelObject {

	private Image image;
	
	public LevelObject(SpriteSheet sheet, int x, int y) {
		image = sheet.getSubImage(x, y);
	}
	
	
}
