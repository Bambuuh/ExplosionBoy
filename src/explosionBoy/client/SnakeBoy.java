package explosionBoy.client;


public class SnakeBoy extends Player {

	public SnakeBoy(int x, int y, AnimationHandler animationHandler, int ID, UnitCollission collision, LevelCreator level) {
		super(x, y, animationHandler, ID, collision, level);
	}

	public void setPlayerAnimation(AnimationHandler animation, int startPosX, int startPosY, int tileWidth, boolean horizontal, boolean vertical){
		this.playerAnimation = animation.getSnakeAnimation(startPosX, startPosY, tileWidth, horizontal, vertical);
	}
}
