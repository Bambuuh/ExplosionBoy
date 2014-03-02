package explosionBoy.client;

import org.newdawn.slick.Animation;

public class Explosion {
	
	private float x;
	private float y;
	
	private Animation animation;
	
	private boolean exploded = false;
	
	public Explosion(AnimationHandler handler, int direction, float x, float y){
		animation = handler.getExplosionAnimation();
		this.x = x;
		this.y = y;
	}
	
	public void explode(){
		if (animation.isStopped()){
			exploded = true;
		}
	}
	
	public void draw(){
		if (!exploded) {
			animation.draw(x, y);
		}
		if (animation.isStopped()){
			exploded = true;
		}
	}
	
	public boolean getExploded(){
		return exploded;
	}
}
