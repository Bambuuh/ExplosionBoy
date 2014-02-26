package explosionBoy.client;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;






import org.newdawn.slick.Animation;

public abstract class Player {

	protected int x;
	protected int y;
	protected int speed;
	protected boolean UP = false;
	protected boolean DOWN = false;
	protected boolean LEFT = false;
	protected boolean RIGHT = false;
	protected Json json;
	private Animation currentAnimation;
	
	protected Animation playerAnimation;
	
	public Player(int x, int y) {
		this.currentAnimation = new Animation();
		this.json = new Json();
		this.x = x;
		this.y = y;
		
	}
	
	public void setPlayerAnimation(AnimationHandler animation, int startPosX, int startPosY, int tileWidth, boolean horizontal, boolean vertical){
		this.playerAnimation = animation.getSnakeAnimation(startPosX, startPosY, tileWidth, horizontal, vertical);
	}
	
	public void move(int x, int y, float speed){
		
		this.y += (y*speed);
		this.x += (x*speed);
		
	}
	
	public void setFacing(Json json,AnimationHandler animation){
		if (json.getDirection().equals("UP") && !UP) {
			setPlayerAnimation(animation, 48, 0, 48, false, false);
			UP = true;
			DOWN = false;
			LEFT = false;
			RIGHT = false;
		}
		else if (json.getDirection().equals("DOWN") && !DOWN) {
			setPlayerAnimation(animation, 0, 0, 48, false, false);
			DOWN = true;
			LEFT = false;
			RIGHT = false;
			UP = false;
		}
		else if (json.getDirection().equals("RIGHT") && !RIGHT) {
			setPlayerAnimation(animation, 96, 0, 48, false, false);
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
	
	public void update(AnimationHandler animation, int delta){
		setFacing(json, animation);
//		currentAnimation(animation);
		drawPlayer(delta);
	}
	
	public void drawPlayer(int delta){
		playerAnimation.draw(this.x, this.y);
		playerAnimation.update(delta);
		
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
