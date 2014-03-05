package explosionBoy.client;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;

import explosionBoy.levelobjects.LevelObject;

public class Bomb {
	
	
	protected ArrayList<Explosion> explosionArray;
	protected ArrayList<Explosion> explosionToRemove;
	
	protected Animation bombAnimation;
	protected AnimationHandler animationHandler;
	
	protected LevelCreator level;
	protected UnitCollission collision;
	
	private Rectangle bombTangle;
	
	protected boolean exploding;
	protected boolean exploded;
	protected boolean explodeUp;
	protected boolean explodeDown;
	protected boolean explodeLeft;
	protected boolean explodeRight;
	
	protected long currentTime;
	protected long currentTime2;
	
	
	protected float x;
	protected float y;
	private float explosionSpeed, bombCountdown;
	
	protected int ID;
	protected int power;
	protected int powerCounter;
	private int explosionSize = 30;
	private int tileSize = 32;
	
	public Bomb(UnitCollission collision, LevelCreator level, AnimationHandler animHandler, float x, float y, float explosionSpeed, float bombCountdown, int power){
		this.collision = collision;
		this.level = level;
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
		explodeUp = true;
		explodeDown = true;
		explodeLeft = true;
		explodeRight = true;
		
		this.x = x;
		this.y = y;
		
		bombTangle = new Rectangle(x, y, 32, 32);
		
		this.power = power;
	}
	
	public void update(int delta, LevelCreator levelCreator){
		animateBomb(levelCreator);
		cleanExplosion();
	}
	
	public void animateBomb(LevelCreator levelCreator){
		Rectangle rectangle = new Rectangle(x, y, explosionSize, explosionSize);
		if (countDown() >= bombCountdown && !exploding) {
			exploding = true;
			explosionArray.add(new Explosion(animationHandler, 1, x, y, rectangle));
		}
		
		if (exploding && countDown2() >= explosionSpeed && powerCounter <= power) {
			currentTime2 = System.currentTimeMillis();
			createExplosion(levelCreator, rectangle);
			powerCounter++;
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
	
	public void createExplosion(LevelCreator levelCreator, Rectangle rectangle){

		if (explodeRight){
			rectangle.setBounds(x + (powerCounter * tileSize) + 1, y+1, explosionSize, explosionSize);
			for (LevelObject object : levelCreator.getLvlObjects()) {
				if (object.isHaveRectangle()) {
					if (UnitCollission.isColliding(rectangle, object.getRectangle())) {
						explodeRight = false;
						break;
					}
				}
			}
			if (explodeRight) {
				explosionArray.add(new Explosion(animationHandler, 1, x + powerCounter * tileSize, y, rectangle));
			}
		}
		
		if (explodeLeft){
			rectangle.setBounds(x - (powerCounter * tileSize) + 1, y+1, explosionSize, explosionSize);
			for (LevelObject object : levelCreator.getLvlObjects()) {
				if (object.isHaveRectangle()) {
					if (UnitCollission.isColliding(rectangle, object.getRectangle())) {
						explodeLeft = false;
						break;
					}
				}
			}
			if (explodeLeft) {
				explosionArray.add(new Explosion(animationHandler, 1, x - powerCounter * tileSize, y, rectangle));
			}
		}
		if (explodeDown){
			rectangle.setBounds(x+ 1, y+(powerCounter * tileSize)+1, explosionSize, explosionSize);
			for (LevelObject object : levelCreator.getLvlObjects()) {
				if (object.isHaveRectangle()) {
					if (UnitCollission.isColliding(rectangle, object.getRectangle())) {
						explodeDown = false;
						break;
					}
				}
			}
			if (explodeDown) {
				explosionArray.add(new Explosion(animationHandler, 1, x, y + powerCounter * tileSize, rectangle));
			}
		}
		if (explodeUp){
			rectangle.setBounds(x+ 1, y - (powerCounter * tileSize)+1, explosionSize, explosionSize);
			for (LevelObject object : levelCreator.getLvlObjects()) {
				if (object.isHaveRectangle()) {
					if (UnitCollission.isColliding(rectangle, object.getRectangle())) {
						explodeUp = false;
						break;
					}
				}
			}
			if (explodeUp) {
				explosionArray.add(new Explosion(animationHandler, 1, x, y - powerCounter * tileSize, rectangle));
			}
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
		explosionToRemove.clear();
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
	
	public float getBombCountDown(){
		return bombCountdown;
	}
	
	public void setBombCountDown(float newTime){
		bombCountdown = newTime;
	}
	
	public ArrayList<Explosion> getExplosionArray(){
		return explosionArray;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public Rectangle getRectangle(){
		return bombTangle;
	}
	
}
