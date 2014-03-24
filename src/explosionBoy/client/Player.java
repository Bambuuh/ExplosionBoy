package explosionBoy.client;



import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public abstract class Player {

	float bombX=0;
	float bombY=0;

	protected float x, oldx;
	protected float y, oldy;
	
	protected Image face;
	
	protected int ID;
	protected int speed;
	protected int maxBomb;
	protected int currBomb;
	
	protected int rightX;
	protected int leftX;
	protected int upX;
	protected int downX;
	protected int walkingY;
	
	protected boolean UP = false;
	protected boolean DOWN = false;
	protected boolean LEFT = false;
	protected boolean RIGHT = false;
	protected boolean bombAvailable = true;
	
	protected Json json;
	protected AnimationHandler animationHandler;
	protected Rectangle rectangle;
	protected SFX sfx;

	protected Animation playerAnimation;

	public Player(float x, float y, AnimationHandler animation, int ID, SFX sfx) {
		this.ID = ID;
		this.animationHandler = animation;
		this.json = new Json();
		this.x = x;
		this.y = y;
		setPlayerWalkingAnimation(animation, 0, 0, 48, false, false);
		this.rectangle = new Rectangle(x, y, playerAnimation.getCurrentFrame().getTextureWidth(), playerAnimation.getCurrentFrame().getTextureHeight());
		this.sfx = sfx;
	}

	public void setPlayerWalkingAnimation(AnimationHandler animation, int startPosX, int startPosY, int tileWidth, boolean horizontal, boolean vertical){
		this.playerAnimation = animation.getWalkingAnimation(startPosX, startPosY, tileWidth, horizontal, vertical);
	}

	//moves the character client-side
	public void move(float x, float y, float speed, int delta){
		this.oldx = this.x;
		this.oldy = this.y;
		this.y += (y*speed*delta);
		this.x += (x*speed*delta);
	}
	// sets the facing depending on direction sent from Json
	//the x variable is set in the child class
	public void setFacing(Json json){
		if (json.getDirection().equals("UP") && !UP) {
			setPlayerWalkingAnimation(animationHandler, upX, walkingY, 48, false, false);
			UP = true;
			DOWN = false;
			LEFT = false;
			RIGHT = false;
		}
		else if (json.getDirection().equals("DOWN") && !DOWN) {
			setPlayerWalkingAnimation(animationHandler, downX, walkingY, 48, false, false);
			DOWN = true;
			LEFT = false;
			RIGHT = false;
			UP = false;
		}
		else if (json.getDirection().equals("RIGHT") && !RIGHT) {
			setPlayerWalkingAnimation(animationHandler, rightX, walkingY, 48, false, false);
			RIGHT = true;
			DOWN = false;
			LEFT = false;
			UP = false;
		}
		else if (json.getDirection().equals("LEFT") && !LEFT) {
			setPlayerWalkingAnimation(animationHandler, leftX, walkingY, 48, true, false);
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

	public void currentAnimation(){
		if (UP) {
			setPlayerWalkingAnimation(animationHandler, upX, walkingY, 48, false, false);
		}
		if (DOWN) {
			setPlayerWalkingAnimation(animationHandler, downX, walkingY, 48, false, false);
		}
		if (RIGHT) {
			setPlayerWalkingAnimation(animationHandler, rightX, walkingY, 48, false, false);
		}
		if (LEFT) {
			setPlayerWalkingAnimation(animationHandler, leftX, walkingY, 48, true, false);
		}
	}

	public void update(int delta){
		setFacing(json);
		setRectangle();
	}
	
	public void render(int delta){
		playerAnimation.draw(this.x, this.y);
		playerAnimation.update(delta);
	}

	public void bombDropper(ArrayList<Bomb> bombArray, ArrayList<Bomb> addBombArray, float explosionSpeed, float countdown, int power){

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
		for (int i = bombArray.size()-1; i >= 0; i--) {
			if (bombArray.get(i).getX() == bombX && bombArray.get(i).getY() == bombY) {
				bombAvailable = false;
				break;
			}
		}
		if (bombAvailable) {
			sfx.getDropBomb().playAsSoundEffect(1f, 0.2f, false);
			addBombArray.add(new Bomb(animationHandler, bombX, bombY, explosionSpeed, countdown, power));
		}
		bombAvailable = true;
	}

	public void setRectangle(){
		this.rectangle.setBounds(x+1, y+playerAnimation.getHeight()-10, playerAnimation.getWidth()-4, 8);
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
	
	public Image getFace(){
		return face;
	}
}
