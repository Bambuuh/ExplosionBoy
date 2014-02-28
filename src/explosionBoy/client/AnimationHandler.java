package explosionBoy.client;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class AnimationHandler {
	
	private Image bigSheetImage;
	private Image explosionSheet;
	
	public AnimationHandler() {
		
			try {
				bigSheetImage = new Image("res/snakeboy/SnakeBoySprite.png");
				explosionSheet = new Image("res/explosion/explosionSheet.png");
			} catch (SlickException e) {
				System.err.println("Could not load main sheet. " + e.getMessage());
			}
	}
	
	public Animation getSnakeAnimation(int startPosX, int startPosY,int pictureW, boolean horizontal, boolean vertical){
		
		SpriteSheet superSheet;
		Animation superAnimation;
		
		superSheet = new SpriteSheet(bigSheetImage.getSubImage(startPosX, startPosY, pictureW, 30).getFlippedCopy(horizontal, vertical), 16, 30);
		superAnimation = new Animation(superSheet, 250);
		
		superAnimation.setPingPong(true);
		
		return superAnimation;
	}
	
	public Animation getBombAnimation(int startPosX, int startPosY,int pictureW, boolean horizontal, boolean vertical, boolean PingPong){
		
		SpriteSheet explosionSprite;
		Animation bombAnimation;
		
		explosionSprite = new SpriteSheet(explosionSheet.getSubImage(startPosX, startPosY, pictureW, 32).getFlippedCopy(horizontal, vertical), 32, 32);
		bombAnimation = new Animation(explosionSprite, 250);
		
		bombAnimation.setPingPong(PingPong);
		
		return bombAnimation;
	}
}
