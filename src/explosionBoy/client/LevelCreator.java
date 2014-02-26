package explosionBoy.client;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import explosionBoy.levelobjects.LevelObject;

public class LevelCreator {
	
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
	
	public void createLevel(){
		
		int width = 32;
		int height = 32;
		
		int[][] level = new int[][]{
		{1,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,1},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}
		};
		
		for (int x = 0; x < 25; x++) {
			for (int y = 0; y < 17; y++) {
				switch (level[x][y]) {
				case 0:
					tiles.getSubImage(0, 0, 32, 32).draw(0*x,0*y);
					break;
				case 1:
					tiles.getSubImage(32, 0, 32, 32).draw(0*x,0*y);
					break;
				case 5:
					tiles.getSubImage(64, 0, 32, 32).draw(0*x,0*y);
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
