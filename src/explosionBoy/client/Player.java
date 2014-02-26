package explosionBoy.client;



import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;

public abstract class Player {

	protected int x, oldx;
	protected int y, oldy;
	protected int speed;
	protected boolean UP = false;
	protected boolean DOWN = false;
	protected boolean LEFT = false;
	protected boolean RIGHT = false;
	protected Json json;
	private AnimationHandler animationHandler;
	private Rectangle rectangle;
	
	protected Animation playerAnimation;
	
	public Player(int x, int y, AnimationHandler animation) {
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
	
	public void move(int x, int y, float speed){
		this.oldx = this.x;
		this.oldy = this.y;
		this.y += (y*speed);
		this.x += (x*speed);
		
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
			System.out.println(playerAnimation.getFrameCount());
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
		setFacing(json, animationHandler);
//		currentAnimation(animation);
		drawPlayer(delta);
		this.rectangle.setBounds(x, y+playerAnimation.getHeight()-5, playerAnimation.getWidth(), playerAnimation.getHeight()/5);
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

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public int getOldx() {
		return oldx;
	}

	public void setOldx(int oldx) {
		this.oldx = oldx;
	}

	public int getOldy() {
		return oldy;
	}

	public void setOldy(int oldy) {
		this.oldy = oldy;
	}
}
