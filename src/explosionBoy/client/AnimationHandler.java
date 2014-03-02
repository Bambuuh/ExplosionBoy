package explosionBoy.client;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class AnimationHandler {
	
	private Image bigSheetImage;
	private Image explosionSheet;
	private Image explosionSheet2;
	private Image bearSheet;
	
	public AnimationHandler() {
			try {
				bigSheetImage = new Image("res/snakeboy/SnakeBoySprite.png");
				explosionSheet = new Image("res/explosion/explosionSheet.png");
				explosionSheet2 = new Image("res/explosion/newExplosionSheet.png");
				bearSheet = new Image("res/bearboy/BearBoySprite.png");
				
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
	
	public Animation getBombAnimation(int startPosX, int startPosY,int pictureW, boolean horizontal, boolean vertical, boolean PingPong, boolean looping){
		
		SpriteSheet explosionSprite;
		Animation bombAnimation;
		
		explosionSprite = new SpriteSheet(explosionSheet.getSubImage(startPosX, startPosY, pictureW, 32).getFlippedCopy(horizontal, vertical), 32, 32);
		bombAnimation = new Animation(explosionSprite, 250);
		
		bombAnimation.setPingPong(PingPong);
		bombAnimation.setLooping(looping);
		
		return bombAnimation;
	}
	
	public Animation getExplosionAnimation(){
		
		SpriteSheet spriteSheet;
		Animation animation;
		
		spriteSheet = new SpriteSheet(explosionSheet2, 32, 32);
		animation = new Animation(spriteSheet, 80);
		
		animation.setPingPong(false);
		animation.setLooping(false);
		
		return animation;
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
}
