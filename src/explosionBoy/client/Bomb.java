package explosionBoy.client;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;

import explosionBoy.levelobjects.FirePower;
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
	
	protected boolean oneLastRight;
	protected boolean oneLastLeft;
	protected boolean oneLastUp;
	protected boolean oneLastDown;
	
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
		
		oneLastRight = false;
		oneLastLeft = false;
		oneLastUp = false;
		oneLastDown = false;
		
		this.x = x;
		this.y = y;
		
		bombTangle = new Rectangle(x + 1, y + 1, 30, 30);
		
		this.power = power;
	}
	
	public void update(int delta, LevelCreator levelCreator){
		animateBomb(levelCreator);
		cleanExplosion();
	}
	
	public void animateBomb(LevelCreator levelCreator){
		Rectangle rectangle = new Rectangle(x, y, explosionSize, explosionSize);
		//creates one explosions where the bomb was to start the animations
		if (countDown() >= bombCountdown && !exploding) {
			exploding = true;
			explosionArray.add(new Explosion(animationHandler, x, y));
		}
		
		//creates a new explosions in all allowed directions if enough time has passed
		if (exploding && countDown2() >= explosionSpeed && powerCounter <= power) {
			currentTime2 = System.currentTimeMillis();
			createExplosion(levelCreator, rectangle);
			powerCounter++;
		}
		
		//paints all the explosions
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
		
		//checks if the right explosion hits anything interesting.
		if (explodeRight){
			rectangle.setBounds(x + (powerCounter * tileSize) + 1, y+1, explosionSize, explosionSize);
			for (LevelObject object : levelCreator.getLvlObjects()) {
				if (object.isHaveRectangle()) {
					if (UnitCollission.isColliding(rectangle, object.getRectangle())) {
						explodeRight = false;
						//checks if its is a box, and if it is, changes to ground and remove rectangle
						if (object.isBox()) {
							levelCreator.getLvlObjects().add(new FirePower(animationHandler.getPowerups().getSubImage(0, 0, 32, 32), object.getX(), object.getY(), true, false, false));
							levelCreator.getRemoveList().add(object);
//							object.setImage(animationHandler.getTiles().getSubImage(96, 0, 32, 32));
//							object.setHaveRectangle(false);
							oneLastRight = true;
						}
						break;
					}
				}
			}
			if (explodeRight) {
				explosionArray.add(new Explosion(animationHandler, x + powerCounter * tileSize, y));
			}
			if (oneLastRight) {
				explosionArray.add(new Explosion(animationHandler, x + powerCounter * tileSize, y));
				oneLastRight = false;
			}
		}
		//checks if the left explosion hits anything interesting.
		if (explodeLeft){
			rectangle.setBounds(x - (powerCounter * tileSize) + 1, y+1, explosionSize, explosionSize);
			for (LevelObject object : levelCreator.getLvlObjects()) {
				if (object.isHaveRectangle()) {
					if (UnitCollission.isColliding(rectangle, object.getRectangle())) {
						explodeLeft = false;
						//checks if its is a box, and if it is, changes to ground and remove rectangle
						if (object.isBox()) {
							object.setImage(animationHandler.getTiles().getSubImage(96, 0, 32, 32));
							object.setHaveRectangle(false);
							oneLastLeft = true;
						}
						break;
					}
				}
			}
			if (explodeLeft) {
				explosionArray.add(new Explosion(animationHandler, x - powerCounter * tileSize, y));
			}
			if (oneLastLeft) {
				explosionArray.add(new Explosion(animationHandler, x - powerCounter * tileSize, y));
				oneLastLeft = false;
			}
		}
		//checks if the downwards explosion hits anything interesting.
		if (explodeDown){
			rectangle.setBounds(x+ 1, y+(powerCounter * tileSize)+1, explosionSize, explosionSize);
			for (LevelObject object : levelCreator.getLvlObjects()) {
				if (object.isHaveRectangle()) {
					if (UnitCollission.isColliding(rectangle, object.getRectangle())) {
						explodeDown = false;
						//checks if its is a box, and if it is, changes to ground and remove rectangle
						if (object.isBox()) {
							object.setImage(animationHandler.getTiles().getSubImage(96, 0, 32, 32));
							object.setHaveRectangle(false);
							oneLastDown = true;
						}
						break;
					}
				}
			}
			if (explodeDown) {
				explosionArray.add(new Explosion(animationHandler, x, y + powerCounter * tileSize));
			}
			if (oneLastDown) {
				explosionArray.add(new Explosion(animationHandler, x, y + powerCounter * tileSize));
				oneLastDown = false;
			}
		}
		//checks if the upwards explosion hits anything interesting.
		if (explodeUp){
			rectangle.setBounds(x+ 1, y - (powerCounter * tileSize)+1, explosionSize, explosionSize);
			for (LevelObject object : levelCreator.getLvlObjects()) {
				if (object.isHaveRectangle()) {
					if (UnitCollission.isColliding(rectangle, object.getRectangle())) {
						explodeUp = false;
						//checks if its is a box, and if it is, changes to ground and remove rectangle
						if (object.isBox()) {
							object.setImage(animationHandler.getTiles().getSubImage(96, 0, 32, 32));
							object.setHaveRectangle(false);
							oneLastUp = true;
						}
						break;
					}
				}
			}
			if (explodeUp) {
				explosionArray.add(new Explosion(animationHandler, x, y - powerCounter * tileSize));
			}
			if (oneLastUp) {
				explosionArray.add(new Explosion(animationHandler, x, y- powerCounter * tileSize));
				oneLastUp = false;
			}
		}
	}
	
	//cleans up finshed explosions and removes them
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
	
	//countdown for the bomb
	public double countDown(){
		long tEnd = System.currentTimeMillis();
		long tDelta = tEnd - currentTime;
		double elapsedSeconds = tDelta / 1000.0;
		return elapsedSeconds;
	}
	
	//countdown for new explosions
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
		return this.bombTangle;
	}
	
}
