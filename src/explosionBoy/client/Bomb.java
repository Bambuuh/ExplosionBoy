package explosionBoy.client;

import org.newdawn.slick.Animation;

public class Bomb {

	private float countDown;
	private int range;
	private int ownerID;
	private float x;
	private float y;
	
	private Animation bombAnimation;
	
	public Bomb(int ownerID, float x, float y, AnimationHandler animation) {
		countDown = 3f;
		range =1;
		this.ownerID = ownerID;
		this.x = x;
		this.y = y;
		bombAnimation = animation.getBombAnimation(0, 128, 96, false, false, true);
	}
	
//	public void setAnimation(AnimationHandler animation){
//		
//		if (countDown == 0) {
//			animation.getBombAnimation(startPosX, startPosY, pictureW, horizontal, vertical, PingPong)
//		}
//	}
	
	public void update(int delta){
		bombAnimation.draw(x, y);
		bombAnimation.update(delta);
	}
	
	
	
}
