package explosionBoy.superbombs;

import explosionBoy.client.AnimationHandler;
import explosionBoy.client.Bomb;
import explosionBoy.client.Explosion;

public class SnakeBomb extends Bomb{

	public SnakeBomb(AnimationHandler animHandler, float x, float y, int ID) {
		super(animHandler, x, y, ID);
	}

	@Override
	public void animateBomb() {
		if (countDown() >=3 && !exploding) {
			exploding = true;
			explosionArray.add(new Explosion(animationHandler, 1, x, y));
		}
		
		if (exploding) {
			
			if (countDown2() >= 0.15 && powerCounter <= power) {
				currentTime2 = System.currentTimeMillis();
				explosionArray.add(new Explosion(animationHandler, 1, x + powerCounter * 32, y));
				explosionArray.add(new Explosion(animationHandler, 1, x - powerCounter * 32, y));
				explosionArray.add(new Explosion(animationHandler, 1, x, y + powerCounter * 32));
				explosionArray.add(new Explosion(animationHandler, 1, x, y - powerCounter * 32));
				powerCounter++;
			}
		}
		
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
	
	
	
}
