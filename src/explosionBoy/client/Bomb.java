package explosionBoy.client;

import java.util.ArrayList;

import org.newdawn.slick.Animation;

public class Bomb {
	
	
	protected ArrayList<Explosion> explosionArray;
	protected ArrayList<Explosion> explosionToRemove;
	
	protected Animation bombAnimation;
	protected AnimationHandler animationHandler;
	
	protected boolean exploding;
	protected boolean  exploded;
	
	protected long currentTime;
	protected long currentTime2;
	
	protected float x;
	protected float y;
	private float explosionSpeed, bombCountdown;
	
	protected int ID;
	protected int power;
	protected int powerCounter;
	
	public Bomb(AnimationHandler animHandler, float x, float y, float explosionSpeed, float bombCountdown, int power){
		explosionArray = new ArrayList<Explosion>();
		explosionToRemove = new ArrayList<Explosion>();
		animationHandler = animHandler;
		this.explosionSpeed = explosionSpeed;
		this.bombCountdown = bombCountdown;
		bombAnimation = animationHandler.getBombAnimation(0, 160, 96, false, false, true, true);
		currentTime = System.currentTimeMillis();
		currentTime2 = System.currentTimeMillis();
		powerCounter = 1;
		exploding = false;
		exploded = false;
		
		this.x = x;
		this.y = y;
		
		this.power = power;
	}
	
	public void update(int delta){
		animateBomb();
		cleanExplosion();
	}
	
	public void animateBomb(){
		if (countDown() >= bombCountdown && !exploding) {
			exploding = true;
			explosionArray.add(new Explosion(animationHandler, 1, x, y));
		}
		
		if (exploding) {
			
			if (countDown2() >= explosionSpeed && powerCounter <= power) {
				currentTime2 = System.currentTimeMillis();
				explosionArray.add(new Explosion(animationHandler, 1, x + powerCounter * 32, y));
				explosionArray.add(new Explosion(animationHandler, 1, x - powerCounter * 32, y));
				explosionArray.add(new Explosion(animationHandler, 1, x, y + powerCounter * 32));
				explosionArray.add(new Explosion(animationHandler, 1, x, y - powerCounter * 32));
				powerCounter++;
			}
		}
		
		for (Explosion explosion : explosionArray) {
			explosion.draw();
		}
		if (!exploding) {
			bombAnimation.draw(x, y);
		}
		if (exploding && explosionArray.size() == 0) {
			exploded = true;
		}
	}
	
	public void cleanExplosion(){
		for (Explosion explosion : explosionArray) {
			if (explosion.getExploded()) {
				explosionToRemove.add(explosion);
			}
		}
		for (Explosion explosion : explosionToRemove) {
			explosionArray.remove(explosion);
		}
	}
	
	public boolean getExploded(){
		return exploded;
	}
	
	public double countDown(){
		long tEnd = System.currentTimeMillis();
		long tDelta = tEnd - currentTime;
		double elapsedSeconds = tDelta / 1000.0;
		return elapsedSeconds;
	}
	
	public double countDown2(){
		long tEnd = System.currentTimeMillis();
		long tDelta = tEnd - currentTime2;
		double elapsedSeconds = tDelta / 1000.0;
		return elapsedSeconds;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
}
