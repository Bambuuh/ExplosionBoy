package explosionBoy.client;

import org.newdawn.slick.Animation;

public class Bomb {
	
	private boolean exploding = false;
	private boolean exploded = false;

	private long currentTime;
	private int range;
	private int ownerID;
	private float x;
	private float y;
	
	private AnimationHandler animationHandler;
	
	private Animation bombAnimation;
	private Animation explosionLeft;
	private Animation explosionRight;
	private Animation explosionUp;
	private Animation explosionDown;
	
	public Bomb(int ownerID, float x, float y, AnimationHandler animation) {
		animationHandler = animation;
		currentTime = System.currentTimeMillis();
		range =1;
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
		bombAnimation.draw(x, y);
		bombAnimation.update(delta);
		checkExplosion();
		
	}
	
	public void checkExplosion(){
		if (countDown() >= 3 && !exploding) {
			bombAnimation = setAnimation(32, 0, 160, false, false, false, false);
			explosionLeft = setAnimation(0, 0, 160, false, false, false, false);
			explosionRight = setAnimation(0, 0, 160, false, false, false, false);
			explosionDown = setAnimation(0, 0, 160, false, false, false, false);
			explosionUp = setAnimation(0, 0, 160, false, false, false, false);
			exploding = true;
		}
		if (bombAnimation.isStopped()) {
			exploded = true;
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
