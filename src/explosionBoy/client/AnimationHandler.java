package explosionBoy.client;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class AnimationHandler {
	
	private Image bigSheetImage;
	
	private SpriteSheet snakeDownSheet;
	private SpriteSheet snakeUpSheet;
	private SpriteSheet snakeLeftSheet;
	private SpriteSheet snakeRightSheet;
	
	private Animation snakeMoveDown;
	private Animation snakeMoveUp;
	private Animation snakeMoveLeft;
	private Animation snakeMoveRight;
	
	public AnimationHandler() {
		
			try {
				
				bigSheetImage = new Image("res/snakeboy/SnakeBoySprite.png");
				
				snakeDownSheet = new SpriteSheet(bigSheetImage.getSubImage(16, 0, 32, 30), 16, 30);
				snakeMoveDown = new Animation(snakeDownSheet, 500);
				
				snakeUpSheet = new SpriteSheet(bigSheetImage.getSubImage(64, 0, 32, 30), 16, 30);
				snakeMoveUp = new Animation(snakeUpSheet, 500);
				
				snakeRightSheet = new SpriteSheet(bigSheetImage.getSubImage(112, 0, 32, 30), 16, 30);
				snakeMoveRight = new Animation(snakeRightSheet, 500);
				
				snakeLeftSheet = new SpriteSheet(bigSheetImage.getSubImage(112, 0, 32, 30).getFlippedCopy(true, false), 16, 30);
				snakeMoveLeft = new Animation(snakeLeftSheet, 400);
				
			} catch (SlickException e) {
				System.err.println("Could not load main sheet. " + e.getMessage());
			}
	}
	
	public Animation getImage(){
		return snakeMoveLeft;
	}
}
