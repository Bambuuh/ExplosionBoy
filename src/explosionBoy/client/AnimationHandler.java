package explosionBoy.client;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class AnimationHandler {
	
	private Image bigSheetImage;
	private Image explosionSheet;
	private Image testExplosion;
	private Image powerupTileSet;
	private Image bombSheet;
	private Image tileSet;
	private Image faces;
	private Image alphabet;
	
	private SpriteSheet tiles;
	private SpriteSheet powerups;
	
	public AnimationHandler() {
			try {
				bigSheetImage = new Image("res/classBoy/BigSheetBoy.png");
				explosionSheet = new Image("res/explosion/explosionSheet.png");
				testExplosion = new Image("res/explosion/bigExplosion.png");
				bombSheet = new Image("res/explosion/bombSheet.png");
				faces = new Image("res/faces/vortexFace.png");
				alphabet = new Image("res/alphabet/redAlphabetOffset.png");
				
				powerupTileSet = new Image("res/tileset/retardPowerTiles.png");
				powerups = new SpriteSheet(powerupTileSet, 32, 32);
				
				tileSet = new Image("res/tileset/levelTileSet.png");
				tiles = new SpriteSheet(tileSet, 32, 32);
				
			} catch (SlickException e) {
				System.err.println("Could not load main sheet. " + e.getMessage());
			}
	}
	
	public Animation getWalkingAnimation(int startPosX, int startPosY,int pictureW, boolean horizontal, boolean vertical){
		
		SpriteSheet superSheet;
		Animation superAnimation;
		
		superSheet = new SpriteSheet(bigSheetImage.getSubImage(startPosX, startPosY, pictureW, 30).getFlippedCopy(horizontal, vertical), 16, 30);
		superAnimation = new Animation(superSheet, 250);
		
		superAnimation.setPingPong(true);
		
		return superAnimation;
	}
	
	public Animation getBombAnimation(int startPosX, int startPosY,int pictureW, boolean horizontal, boolean vertical, boolean PingPong, boolean looping){
		
		SpriteSheet explosionSprite;
		Animation bombAnimation;
		
		explosionSprite = new SpriteSheet(bombSheet.getSubImage(startPosX, startPosY, pictureW, 32).getFlippedCopy(horizontal, vertical), 32, 32);
		bombAnimation = new Animation(explosionSprite, 250);
		
		bombAnimation.setPingPong(PingPong);
		bombAnimation.setLooping(looping);
		
		return bombAnimation;
	}
	
	public Animation getExplosionAnimation(int choice){
		
		SpriteSheet spriteSheet1;
		SpriteSheet spriteSheet2;
		SpriteSheet spriteSheet3;
		SpriteSheet spriteSheet4;
		
		Animation animation1;
		Animation animation2;
		Animation animation3;
		Animation animation4;
		
		spriteSheet1 = new SpriteSheet(testExplosion.getSubImage(0, 0, 320, 40 ), 40, 40);
		spriteSheet2 = new SpriteSheet(testExplosion.getSubImage(0, 0, 320, 40 ).getFlippedCopy(true, false), 40, 40);
		spriteSheet3 = new SpriteSheet(testExplosion.getSubImage(0, 40, 320, 40 ), 40, 40);
		spriteSheet4 = new SpriteSheet(testExplosion.getSubImage(0, 40, 320, 40 ).getFlippedCopy(false, true), 40, 40);

		animation1 = new Animation(spriteSheet1, 50);
		animation2 = new Animation(spriteSheet2, 50);
		animation3 = new Animation(spriteSheet3, 50);
		animation4 = new Animation(spriteSheet4, 50);
		
		animation1.setPingPong(false);
		animation1.setLooping(false);
		
		animation2.setPingPong(false);
		animation2.setLooping(false);

		animation3.setPingPong(false);
		animation3.setLooping(false);
		
		animation4.setPingPong(false);
		animation4.setLooping(false);

		switch (choice) {
		case 1:
			return animation1;
		case 2:
			return animation2;
		case 3:
			return animation3;
		case 4:
			return animation4;
		default:
			break;
		}
		
		return null;
	}
	
	public Animation getAnimation(Image image, int startPosX, int startPosY, int totalWidth, int totalHeight, boolean flipHorizontal, boolean flipVertical, int tileWidth, int tileHeight, boolean pingpong, boolean looping){
		
		SpriteSheet spriteSheet;
		Animation animation;
		
		spriteSheet = new SpriteSheet(image.getSubImage(startPosX, startPosY, totalWidth, totalHeight).getFlippedCopy(flipHorizontal, flipVertical), tileWidth, tileHeight);
		animation = new Animation(spriteSheet, 250);
		
		animation.setPingPong(pingpong);
		animation.setLooping(looping);
		
		return animation;
	}

	public Image getTileSet() {
		return tileSet;
	}

	public void setTileSet(Image tileSet) {
		this.tileSet = tileSet;
	}

	public SpriteSheet getTiles() {
		return tiles;
	}

	public void setTiles(SpriteSheet tiles) {
		this.tiles = tiles;
	}
	
	public SpriteSheet getPowerups(){
		return powerups;
	}
	
	public Image getFaces(){
		return faces;
	}
	
	public Image getAlphabet(){
		return alphabet;
	}
}
