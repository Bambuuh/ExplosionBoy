package explosionBoy.client;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

import explosionBoy.levelobjects.LevelObject;

public class LevelCreator {
	
	private Random rand = new Random();
	
	private ArrayList<LevelObject> objectList;
	
	private Image tileSet;
	private SpriteSheet tiles;
	
	private LevelObject corner;
	
	public LevelCreator() {
		try {
			tileSet = new Image("res/tileset/levelTileSet.png");
			tiles = new SpriteSheet(tileSet, 32, 32);
		} catch (SlickException e) {
			System.err.println("Could not load level tileset. " + e.getMessage());
		}
	}
	
	public void generateLevel(){
		
	}
	
	public void drawLevel(){
		
		int[][] level = new int[][]{
				{1,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{2,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,3},
		};
		
		for (int row = 0; row < 25; row++) {
			for (int col = 0; col < 19; col++) {
				switch (level[row][col]) {
				case 0:
						tiles.getSubImage(96, 0, 32, 32).draw(32*row,32*col);
					break;
				case 1:
					tiles.getSubImage(0, 0, 32, 32).draw(32*row,32*col);
					break;
				case 2:
					tiles.getSubImage(0, 0, 32, 32).getFlippedCopy(true, false).draw(32*row,32*col);
					break;
				case 3:
					tiles.getSubImage(0, 0, 32, 32).getFlippedCopy(true, true).draw(32*row,32*col);
					break;
				case 4:
					tiles.getSubImage(0, 0, 32, 32).getFlippedCopy(false, true).draw(32*row,32*col);
					break;
				case 5:
					tiles.getSubImage(64, 0, 32, 32).getFlippedCopy(true, false).draw(32*row,32*col);
					break;
				case 6:
					tiles.getSubImage(32, 0, 32, 32).draw(32*row,32*col);
					break;
				case 7:
					tiles.getSubImage(64, 0, 32, 32).draw(32*row,32*col);
					break;
				case 8:
					tiles.getSubImage(32, 0, 32, 32).getFlippedCopy(false, true).draw(32*row,32*col);
					break;
				case 9:
					tiles.getSubImage(160, 0, 32, 32).draw(32*row,32*col);
					break;

				default:
					break;
				}
			}
		}
	}
	
	private void createLevelObjects(){
		
		objectList = new ArrayList<LevelObject>();
		for (int i = 0; i < 408; i++) {
			
		}
		
	}
	
	public void levelTest(){
		
		
		
		//corners
		tiles.getSubImage(0, 0, 32, 32).draw(0,0);
		tiles.getSubImage(0, 0, 32, 32).draw(0,0);
		tiles.getSubImage(0, 0, 32, 32).draw(0,0);
		tiles.getSubImage(0, 0, 32, 32).draw(0,0);
		
				
				
				
		
		
	}

}
