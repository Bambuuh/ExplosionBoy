package explosionBoy.client;

import org.newdawn.slick.Animation;

public class Bomb {
	
	private boolean exploding = false;
	private boolean exploded = false;

	private long currentTime;
	private int power;
	private int ownerID;
	private float x;
	private float y;
	
	
	private AnimationHandler animationHandler;
	
	private Animation bombAnimation;
	private Animation explosionLeft;
	private Animation explosionRight;
	private Animation explosionUp;
	private Animation explosionDown;
	private Animation explosionTopLeft;
	private Animation explosionTopRight;
	private Animation explosionTopUp;
	private Animation explosionTopDown;
	
	public Bomb(int ownerID, float x, float y, AnimationHandler animation) {
		animationHandler = animation;
		currentTime = System.currentTimeMillis();
		power =2;
		this.ownerID = ownerID;
		this.x = x;
		this.y = y;
		bombAnimation = animation.getBombAnimation(0, 160, 96, false, false, true, true);
	}
	
	public Animation setAnimation(int startX, int startY, int totalWidth, boolean horizontal, boolean vertical, boolean pingpong, boolean looping){
			Animation animation;
			animation = animationHandler.getBombAnimation(startX, startY, totalWidth, horizontal, vertical, pingpong, looping);
			return animation;
	}
	
	public void update(int delta){
		animateBomb(delta, power);
		checkExplosion();
		
	}
	
	public void checkExplosion(){
		if (countDown() >= 3 && !exploding) {
			exploding = true;
			bombAnimation = setAnimation(32, 0, 128, false, false, false, false);
			
			explosionLeft = setAnimation(0, 64, 128, false, false, false, false);
			explosionTopLeft = setAnimation(0, 128, 128, false, false, false, false);
			
			explosionRight = setAnimation(0, 64, 128, false, false, false, false);
			explosionTopRight = setAnimation(0, 128, 128, false, false, false, false);
			
			explosionDown = setAnimation(0, 32, 128, false, false, false, false);
			explosionTopDown = setAnimation(0, 96, 128, false, true, false, false);
			
			explosionUp = setAnimation(0, 32, 128, false, false, false, false);
			explosionTopUp = setAnimation(0, 96, 128, false, false, false, false);
		}
		if (bombAnimation.isStopped()) {
			exploded = true;
		}
	}
	
	public void animateBomb(int delta, int power){
		
		if (!exploding) {
			bombAnimation.draw(x, y);
			bombAnimation.update(delta);
		}
		if (exploding) {
			for (int i = 1; i <= power; i++) {
				int explosionPower = i * 32;
				
				bombAnimation.draw(x, y);
				bombAnimation.update(delta);
				
				explosionLeft.draw(x-explosionPower, y);
				explosionLeft.update(delta);
				explosionTopLeft.draw(x-explosionPower-32, y);
				explosionTopLeft.update(delta);
				
				explosionRight.draw(x+explosionPower, y);
				explosionRight.update(delta);
				explosionTopRight.draw(x+explosionPower+32, y);
				explosionTopRight.update(delta);
				
				explosionUp.draw(x, y-explosionPower);
				explosionUp.update(delta);
				explosionTopUp.draw(x, y-explosionPower-32);
				explosionTopUp.update(delta);
				
				explosionDown.draw(x, y+explosionPower);
				explosionDown.update(delta);
				explosionTopDown.draw(x, y+explosionPower+32);
				explosionTopDown.update(delta);
			}
		}
	}
	
	public double countDown(){
		long tEnd = System.currentTimeMillis();
		long tDelta = tEnd - currentTime;
		double elapsedSeconds = tDelta / 1000.0;
		return elapsedSeconds;
		
	}
	
	public boolean getExploded(){
		return exploded;
	}
	
}
