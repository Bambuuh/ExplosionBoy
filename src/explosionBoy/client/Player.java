package explosionBoy.client;



import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;

public abstract class Player {

	protected ArrayList<Bomb> bombArray;
	
	protected int ID;
	protected float x, oldx;
	protected float y, oldy;
	protected int speed;
	protected int maxBomb;
	protected int currBomb;
	protected boolean UP = false;
	protected boolean DOWN = false;
	protected boolean LEFT = false;
	protected boolean RIGHT = false;
	protected Json json;
	private AnimationHandler animationHandler;
	private Rectangle rectangle;
	
	private Bomb bomb;
	
	protected Animation playerAnimation;
	
	public Player(float x, float y, AnimationHandler animation, int ID) {
		bombArray = new ArrayList<>();
		this.ID = ID;
		this.animationHandler = animation;
		this.json = new Json();
		this.x = x;
		this.y = y;
		setPlayerAnimation(animation, 0, 0, 48, false, false);
		this.rectangle = new Rectangle(x, y, playerAnimation.getCurrentFrame().getTextureWidth(), playerAnimation.getCurrentFrame().getTextureHeight());
	}
	
	public void setPlayerAnimation(AnimationHandler animation, int startPosX, int startPosY, int tileWidth, boolean horizontal, boolean vertical){
		this.playerAnimation = animation.getSnakeAnimation(startPosX, startPosY, tileWidth, horizontal, vertical);
	}
	
	public void move(float x, float y, float speed,int delta){
		this.oldx = this.x;
		this.oldy = this.y;
		this.y += (y*speed*delta);
		this.x += (x*speed*delta);
		
	}
	
	public void setFacing(Json json,AnimationHandler animation){
		if (json.getDirection().equals("UP") && !UP) {
			setPlayerAnimation(animation, 48, 0, 48, false, false);
			System.out.println(playerAnimation.getFrameCount());
			UP = true;
			DOWN = false;
			LEFT = false;
			RIGHT = false;
		}
		else if (json.getDirection().equals("DOWN") && !DOWN) {
			setPlayerAnimation(animation, 0, 0, 48, false, false);
			System.out.println(playerAnimation.getFrameCount());
			DOWN = true;
			LEFT = false;
			RIGHT = false;
			UP = false;
		}
		else if (json.getDirection().equals("RIGHT") && !RIGHT) {
			setPlayerAnimation(animation, 96, 0, 48, false, false);
			System.out.println(playerAnimation.getFrameCount());
			RIGHT = true;
			DOWN = false;
			LEFT = false;
			UP = false;
		}
		else if (json.getDirection().equals("LEFT") && !LEFT) {
			setPlayerAnimation(animation, 96, 0, 48, true, false);
			LEFT = true;
			DOWN = false;
			RIGHT = false;
			UP = false;
		}
		else if (json.getDirection().equals("STILL")){
			if (playerAnimation.getFrameCount()>1) {
				playerAnimation.setCurrentFrame(1);
				playerAnimation.stop();
				LEFT = false;
				DOWN = false;
				RIGHT = false;
				UP = false;
			}
		}
	}
	
	public void currentAnimation(AnimationHandler animation){
		if (UP) {
			setPlayerAnimation(animation, 48, 0, 48, false, false);
		}
		if (DOWN) {
			setPlayerAnimation(animation, 0, 0, 48, false, false);
		}
		if (RIGHT) {
			setPlayerAnimation(animation, 96, 0, 48, false, false);
		}
		if (LEFT) {
			setPlayerAnimation(animation, 96, 0, 48, true, false);
		}
	}
	
	public void update(int delta){
		boolean bombDown = false;
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)&& !bombDown) {
			bombArray.add(new Bomb(ID, x, y, animationHandler));
			bombDown = true;
		}
		
		for (Bomb bomb : bombArray) {
			bomb.update(delta);
		}
		
		setFacing(json, animationHandler);
//		currentAnimation(animation);
		drawPlayer(delta);
		this.rectangle.setBounds(x+1, y+playerAnimation.getHeight()-10, playerAnimation.getWidth()-2, 9);
	}
	
	public void drawPlayer(int delta){
		playerAnimation.draw(this.x, this.y);
		playerAnimation.update(delta);
		
	}
	
	
	public float getX() {
		return x;
	}

	public void setX(float oldx2) {
		this.x = oldx2;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public float getOldx() {
		return oldx;
	}

	public void setOldx(float oldx) {
		this.oldx = oldx;
	}

	public float getOldy() {
		return oldy;
	}

	public void setOldy(float oldy) {
		this.oldy = oldy;
	}
	
	public int getID(){
		return ID;
	}
}
