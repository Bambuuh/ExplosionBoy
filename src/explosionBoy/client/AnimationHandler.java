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
	
	public Animation getSnakeAnimation(int startPosX, int startPosY,int pictureW, boolean horizontal, boolean vertical){
		
		Animation superAnimation;
		SpriteSheet superSheet;
		
		superSheet = new SpriteSheet(bigSheetImage.getSubImage(startPosX, startPosY, pictureW, 30).getFlippedCopy(horizontal, vertical), 16, 30);
		superAnimation = new Animation(superSheet, 250);
		
		superAnimation.setPingPong(true);
		
		return superAnimation;
	}
}
