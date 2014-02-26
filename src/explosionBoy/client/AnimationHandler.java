package explosionBoy.client;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class AnimationHandler {
	
	private SpriteSheet snakeDownSheet;
	private Image bigSheetImage;
	private Animation snakeMoveDown;
	
	public AnimationHandler() {
		
			try {
				
				bigSheetImage = new Image("res/snakeboy/SnakeBoySprite.png");
				snakeDownSheet = new SpriteSheet(bigSheetImage.getSubImage(16, 0, 32, 30), 16, 30);
				snakeMoveDown = new Animation(snakeDownSheet, 500);
				
			} catch (SlickException e) {
				System.err.println("Could not load main sheet. " + e.getMessage());
			}
	}
	
	public Animation getImage(){
		return snakeMoveDown;
	}
}
