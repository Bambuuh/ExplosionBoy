package explosionBoy.client;

import java.util.ArrayList;

public class VortexBoy extends Player {
	
	public VortexBoy(float x, float y, AnimationHandler animation, int ID, SFX sfx) {
		super(x, y, animation, ID, sfx);
		
		face = animation.getFaces();
		
		rightX = 96;
		leftX = 96;
		upX = 48;
		downX = 0;
		walkingY = 34;
		
	}

	@Override
	public void bombDropper(ArrayList<Bomb> bombArray, ArrayList<Bomb> addBombArray, float explosionSpeed, float countdown, int power) {
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
			addBombArray.add(new SwirlingBomb(animationHandler, bombX, bombY, explosionSpeed, countdown, power));
			sfx.getDropBomb().playAsSoundEffect(1f, 1f, false);
		}
		bombAvailable = true;
	}
	
	
}
