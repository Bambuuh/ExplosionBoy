package explosionBoy.client;


public class SnakeBoy extends Player {

	public SnakeBoy(int x, int y) {
		super(x, y);
	}

	public void update(){
		
	}
	
	public void setPlayerAnimation(AnimationHandler animation, int startPosX, int startPosY, int tileWidth){
		this.playerAnimation = animation.getSnakeAnimation(startPosX, startPosY, tileWidth);
	}
	
	public void update(AnimationHandler animation){
		
	}
	
}
