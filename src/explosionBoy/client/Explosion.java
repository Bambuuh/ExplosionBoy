package explosionBoy.client;

import java.util.ArrayList;

import org.newdawn.slick.Animation;

public class Explosion {
	
	private float x;
	private float y;
	private int direction;
	private int orderNumber;
	
	private Animation animation;
	
	public Explosion(AnimationHandler handler, int direction, int orderNumber, float x, float y, ArrayList<Explosion> explosionArray){
		animation = handler.getExplosionAnimation();
		this.direction = direction;
		this.orderNumber = orderNumber;
		explosionArray.add(this);
	}
	
	public void explode(){
		if (direction == 1) {
			animation.draw(x - orderNumber * 32 , y);
		}
		if (direction == 2) {
			animation.draw(x + orderNumber * 32, y);
		}
		if (direction == 3) {
			animation.draw(x, y - orderNumber * 32);
		}
		if (direction == 4) {
			animation.draw(x, y + orderNumber * 32);
		}
	}
	
}
