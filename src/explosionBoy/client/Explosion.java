package explosionBoy.client;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;

public class Explosion {
	
	private float x;
	private float y;
	private int randomAnim;
	
	private Rectangle rectangle;
	
	private Random rand = new Random();
	
	private Animation animation1;
	
	private boolean exploded = false;
	
	public Explosion(AnimationHandler handler, int direction, float x, float y){
		this.x = x-4;
		this.y = y-4;
		rectangle = new Rectangle(x, y, 30, 30);
		
		randomAnim = rand.nextInt(4)+1;
		animation1 = handler.getExplosionAnimation(randomAnim);
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
