package explosionBoy.client;

import org.lwjgl.Sys;
import org.newdawn.slick.Animation;

public class Bomb {

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
		bombAnimation = animation.getBombAnimation(0, 128, 96, false, false, true);
	}
	
	public void setAnimation(AnimationHandler animation){
			bombAnimation = animation.getBombAnimation(128, 0, 32, false, false, false);
	}
	
	public void update(int delta, AnimationHandler animation){
		bombAnimation.draw(x, y);
		bombAnimation.update(delta);
		checkExplosion(animation);
		
	}
	
	public void checkExplosion(AnimationHandler animation){
		if (countDown() >= 3) {
			setAnimation(animation);
		}
	}
	
	public double countDown(){
		long tEnd = System.currentTimeMillis();
		long tDelta = tEnd - currentTime;
		double elapsedSeconds = tDelta / 1000.0;
		return elapsedSeconds;
		
	}
	
	
	
}
