package explosionBoy.client;

import java.util.Random;

import org.newdawn.slick.Animation;

public class Explosion {
	
	private float x;
	private float y;
	private int randomAnim;
	
	private Random rand = new Random();
	
	private Animation animation1;
	private Animation animation2;
	private Animation animation3;
	private Animation animation4;
	
	private boolean exploded = false;
	
	public Explosion(AnimationHandler handler, int direction, float x, float y){
		randomAnim = rand.nextInt(4)+1;
		
		animation1 = handler.getExplosionAnimation(randomAnim);
		this.x = x;
		this.y = y;
	}
	
	public void explode(){
		if (animation1.isStopped()){
			exploded = true;
		}
	}
	
	public void draw(){
		if (!exploded) {
			animation1.draw(x, y);
		}
		if (animation1.isStopped()){
			exploded = true;
		}
	}
	
	public boolean getExploded(){
		return exploded;
	}
}
