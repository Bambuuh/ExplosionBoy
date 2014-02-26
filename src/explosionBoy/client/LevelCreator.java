package explosionBoy.client;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class LevelCreator {
	
	private Image tileSet;
	private SpriteSheet tiles;
	
	public LevelCreator() {
		try {
			tileSet = new Image("res/tileset/levelTileSet.png");
			tiles = new SpriteSheet(tileSet, 32, 32);
		} catch (SlickException e) {
			System.err.println("Could not load level tileset. " + e.getMessage());
		}
	}
	
	public void createLevel(){
		int last = 0;
		
		tiles.getSubImage(0, 0, 32, 32).draw(0,0);
		for (int i = 1; i < 25; i++) {
			tiles.getSubImage(32, 0, 32, 32).draw(32*i, 0);
			last = i;
		}
		tiles.getSubImage(0, 0, 32, 32).getFlippedCopy(true, false).draw(32*last,0);
	}

}
