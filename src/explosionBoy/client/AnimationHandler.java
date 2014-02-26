package explosionBoy.client;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class AnimationHandler {
	
	private Image bigSheetImage;
	
	public AnimationHandler() {
		
			try {
				bigSheetImage = new Image("res/snakeboy/SnakeBoySprite.png");
			} catch (SlickException e) {
				System.err.println("Could not load main sheet. " + e.getMessage());
			}
	}
	
	public Animation getSnakeAnimation(int startPosX, int startPosY,int pictureW){
		
		Animation superAnimation;
		SpriteSheet superSheet;
		
		superSheet = new SpriteSheet(bigSheetImage.getSubImage(startPosX, startPosY, pictureW, 30), 16, 30);
		superAnimation = new Animation(superSheet, 500);
		return superAnimation;
	}
}
