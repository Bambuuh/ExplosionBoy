package explosionBoy.client;



import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;

public abstract class Player {

	float bombX=0;
	float bombY=0;
	
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
	
	private boolean bombDown = false;
	
	protected Animation playerAnimation;
	
	public Player(float x, float y, AnimationHandler animation, int ID) {
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
	
	public void move(float x, float y, float speed, int delta){
		this.oldx = this.x;
		this.oldy = this.y;
		this.y += (y*speed*delta);
		this.x += (x*speed*delta);
	}
	
	public void setFacing(Json json){
		if (json.getDirection().equals("UP") && !UP) {
			setPlayerAnimation(animationHandler, 48, 0, 48, false, false);
			UP = true;
			DOWN = false;
			LEFT = false;
			RIGHT = false;
		}
		else if (json.getDirection().equals("DOWN") && !DOWN) {
			setPlayerAnimation(animationHandler, 0, 0, 48, false, false);
			DOWN = true;
			LEFT = false;
			RIGHT = false;
			UP = false;
		}
		else if (json.getDirection().equals("RIGHT") && !RIGHT) {
			setPlayerAnimation(animationHandler, 96, 0, 48, false, false);
			RIGHT = true;
			DOWN = false;
			LEFT = false;
			UP = false;
		}
		else if (json.getDirection().equals("LEFT") && !LEFT) {
			setPlayerAnimation(animationHandler, 96, 0, 48, true, false);
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
	
	public void update(int delta, ArrayList<Bomb> bombArray){
		setRectangle();
		bombDropper(bombArray);
		setFacing(json);
		drawPlayer(delta);
	}
	
	public void bombDropper(ArrayList<Bomb> bombArray){
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !bombDown) {
			for (int i = (int)x+5; i > 0; i--) {
				if (i%32 == 1) {
					bombX = i-1;
					break;
				}
			}
			for (int i = (int) ((int)y+25); i > 0; i--) {
				if (i%32 == 1) {
					bombY = i-1;
					break;
				}
			}
			bombArray.add(new Bomb(ID, bombX, bombY, animationHandler));
			bombDown = true;
		}
		if (!Keyboard.isKeyDown(Keyboard.KEY_SPACE) && bombDown) {
			bombDown = false;
		}
	}
	
	public void drawPlayer(int delta){
		playerAnimation.draw(this.x, this.y);
		playerAnimation.update(delta);
	}
	
	public void setRectangle(){
		this.rectangle.setBounds(x+1, y+playerAnimation.getHeight()-10, playerAnimation.getWidth()-2, 9);
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
