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
	
	private LevelObject[] lvlObjects;
	
	private Image tileSet;
	private SpriteSheet tiles;
	
	private LevelObject corner;
	
	public LevelCreator() {
		lvlObjects = new LevelObject[475];
		try {
			tileSet = new Image("res/tileset/levelTileSet.png");
			tiles = new SpriteSheet(tileSet, 32, 32);
		} catch (SlickException e) {
			System.err.println("Could not load level tileset. " + e.getMessage());
		}
		generateLevel();
	}
	
	
	private void generateLevel(){
		
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
		
		int index = 0;
		for (int row = 0; row < 25; row++) {
			for (int col = 0; col < 19; col++) {
				
				
				
				Image image = null;
				switch (level[row][col]) {
				case 0:
					int n = rand.nextInt(3);
					
					if (n == 0) {
						image = tiles.getSubImage(96, 0, 32, 32);
					}
					else {
						image = tiles.getSubImage(128, 0, 32, 32);
					}
					break;
				case 1:
					image = tiles.getSubImage(0, 0, 32, 32);
					break;
				case 2:
					image = tiles.getSubImage(0, 0, 32, 32).getFlippedCopy(true, false);
					break;
				case 3:
					image = tiles.getSubImage(0, 0, 32, 32).getFlippedCopy(true, true);
					break;
				case 4:
					image = tiles.getSubImage(0, 0, 32, 32).getFlippedCopy(false, true);
					break;
				case 5:
					image = tiles.getSubImage(64, 0, 32, 32).getFlippedCopy(true, false);
					break;
				case 6:
					image = tiles.getSubImage(32, 0, 32, 32);
					break;
				case 7:
					image = tiles.getSubImage(64, 0, 32, 32);
					break;
				case 8:
					image = tiles.getSubImage(32, 0, 32, 32).getFlippedCopy(false, true);
					break;
				case 9:
					image = tiles.getSubImage(160, 0, 32, 32);
					break;

				default:
					break;
				}
				int x = 32*row;
				int y = 32*col;
				System.out.println(x+" "+y);
				lvlObjects[index] = new LevelObject(image, x, y);
				index++;
			}
		}
	}
	
	public void printLevel(){
		for (LevelObject lvl : lvlObjects) {
			lvl.draw();
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
