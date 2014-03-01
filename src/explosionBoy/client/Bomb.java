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
	
	private Animation bombAnimation;
	
	public Bomb(int ownerID, float x, float y, AnimationHandler animation) {
		currentTime = System.currentTimeMillis();
		range =1;
		this.ownerID = ownerID;
		this.x = x;
		this.y = y;
		bombAnimation = animation.getBombAnimation(0, 160, 96, false, false, true, true);
	}
	
	public void setAnimation(AnimationHandler animation, int startX, int startY, int totalWidth, boolean horizontal, boolean vertical, boolean pingpong, boolean looping){
			bombAnimation = animation.getBombAnimation(startX, startY, totalWidth, horizontal, vertical, pingpong, looping);
	}
	
	public void update(int delta, AnimationHandler animation){
		bombAnimation.draw(x, y);
		bombAnimation.update(delta);
		checkExplosion(animation);
		
	}
	
	public void checkExplosion(AnimationHandler animation){
		if (countDown() >= 3 && !exploding) {
			setAnimation(animation, 0, 0, 160, false, false, false, false);
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
