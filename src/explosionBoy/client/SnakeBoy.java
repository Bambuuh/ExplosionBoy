package explosionBoy.client;


public class SnakeBoy extends Player {

	public SnakeBoy(int x, int y, AnimationHandler animationHandler, int ID, SFX sfx) {
		super(x, y, animationHandler, ID, sfx);
		rightX = 96;
		leftX = 96;
		upX = 48;
		downX = 0;
		walkingY = 0;
		
	}


}
