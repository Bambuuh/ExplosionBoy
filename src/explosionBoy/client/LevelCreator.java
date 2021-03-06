package explosionBoy.client;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import explosionBoy.levelobjects.LevelObject;
import explosionBoy.levelobjects.PowerUp;

public class LevelCreator {
	
	private AnimationHandler animHandler;
	
	private ArrayList<LevelObject> lvlObjects;
	private ArrayList<LevelObject> objectsToAdd;
	private ArrayList<LevelObject> objectsToRemove;


//	private Image tileSet;
	private SpriteSheet tiles;
	
	public LevelCreator(AnimationHandler handler) {
		this.animHandler = handler;
		this.setObjectsToAdd(new ArrayList<LevelObject>());
		lvlObjects = new ArrayList<LevelObject>();
		objectsToRemove = new ArrayList<LevelObject>();
		
//		tileSet = animHandler.getTileSet();
		tiles = animHandler.getTiles();
		generateLevel();
	}
	
	
	private void generateLevel(){
		
		int[][] level = new int[][]{
				{1,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4},
				{6,10,10,0,0,0,0,0,0,0,0,0,0,0,0,0,10,10,8},
				{6,10,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,10,8},
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
				{6,10,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,10,8},
				{6,10,10,0,0,0,0,0,0,0,0,0,0,0,0,0,10,10,8},
				{2,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,3},
		};
		
		
		
		for (int row = 0; row < 25; row++) {
			for (int col = 0; col < 19; col++) {
				boolean haveRectangle = true;
				
				
				Image image = null;
				
				boolean box = false;
				boolean unbreakable = false;
				
				//created the lvl and sets a boolean to determine if its unbreakable, ground or a box.
				switch (level[row][col]) {
				
				case 0:
						image = tiles.getSubImage(96, 0, 32, 32);
						haveRectangle = false;
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
					unbreakable = true;
					break;
				case 10:
					image = tiles.getSubImage(96, 0, 32, 32);
					haveRectangle = false;
					break;

				default:
					break;
				}
				int x = 32*row;
				int y = 32*col;
				lvlObjects.add(new LevelObject(image, x, y, haveRectangle, box, unbreakable));
			}
		}
	}
	
	public void printLevel(){
		for (LevelObject lvl : lvlObjects) {
			lvl.draw();
		}
		if (!objectsToAdd.isEmpty()) {
			lvlObjects.addAll(objectsToAdd);
			objectsToAdd.clear();
		}
	}
	
	public void createPowerUp(Json json){
		Image img=null;
		switch (json.getDirection()) {
		case "BOMBPOWER":
			img = animHandler.getPowerups().getSubImage(32, 0, 32, 32);
			objectsToAdd.add(new PowerUp(img, json.getxPos(), json.getyPos(), true, false, false));
			break;
		case "FIREPOWER":
			img = animHandler.getPowerups().getSubImage(0, 0, 32, 32);
			objectsToAdd.add(new PowerUp(img, json.getxPos(), json.getyPos(), true, false, false));
			break;
		default:
			break;
		}
	}
	
	public ArrayList<LevelObject> getLevelArray(){
		return lvlObjects;
	}
	
	public void levelTest(){
		//corners
		tiles.getSubImage(0, 0, 32, 32).draw(0,0);
		tiles.getSubImage(0, 0, 32, 32).draw(0,0);
		tiles.getSubImage(0, 0, 32, 32).draw(0,0);
		tiles.getSubImage(0, 0, 32, 32).draw(0,0);
	}
	
	public ArrayList<LevelObject> getLvlObjects() {
		return lvlObjects;
	}


	public void setLvlObjects(ArrayList<LevelObject> lvlObjects) {
		this.lvlObjects = lvlObjects;
	}


	public ArrayList<LevelObject> getObjectsToAdd() {
		return objectsToAdd;
	}


	public void setObjectsToAdd(ArrayList<LevelObject> objectsToAdd) {
		this.objectsToAdd = objectsToAdd;
	}
	
	public ArrayList<LevelObject> getRemoveList(){
		return objectsToRemove;
	}
}
