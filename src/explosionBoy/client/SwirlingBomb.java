package explosionBoy.client;

import org.newdawn.slick.geom.Rectangle;

import explosionBoy.levelobjects.LevelObject;

public class SwirlingBomb extends Bomb {
	
	private boolean change;
	private boolean swirlCounter;

	public SwirlingBomb(AnimationHandler animHandler, float x, float y,
			float explosionSpeed, float bombCountdown, int power) {
		super(animHandler, x, y, explosionSpeed, bombCountdown, power);
		bombAnimation = animHandler.getBombAnimation(0, 32, 96, false, false, true, true);
	}

	@Override
	public void createExplosion(LevelCreator levelCreator, Rectangle rectangle) {
		
		//checks if the right explosion hits anything interesting.
		if (explodeRight){
			rectangle.setBounds(x + (powerCounter * tileSize) + 1, y+1, explosionSize, explosionSize);
			for (LevelObject object : levelCreator.getLvlObjects()) {
				if (object.isHaveRectangle()) {
					if (UnitCollission.isColliding(rectangle, object.getRectangle())) {
						explodeRight = false;
						//checks if its is a box, and if it is, changes to ground and remove rectangle
						if (object.isBox()) {
							String power = generatePowerup();
							if (power == "FIRE") {
								levelCreator.getRemoveList().add(object);
							}
							else if (power == "BOMB") {
								levelCreator.getRemoveList().add(object);
							}else{
								object.setHaveRectangle(false);
								object.setImage(animationHandler.getTiles().getSubImage(96, 0, 32, 32));
							}
							oneLastRight = true;
						}
						break;
					}
				}
			}
			if (explodeRight) {
				explosionArray.add(new Explosion(animationHandler, x + powerCounter * tileSize, y));
			}
			if (oneLastRight) {
				explosionArray.add(new Explosion(animationHandler, x + powerCounter * tileSize, y));
				oneLastRight = false;
			}
		}
		//checks if the left explosion hits anything interesting.
		if (explodeLeft){
			rectangle.setBounds(x - (powerCounter * tileSize) + 1, y+1, explosionSize, explosionSize);
			for (LevelObject object : levelCreator.getLvlObjects()) {
				if (object.isHaveRectangle()) {
					if (UnitCollission.isColliding(rectangle, object.getRectangle())) {
						explodeLeft = false;
						//checks if its is a box, and if it is, changes to ground and remove rectangle
						if (object.isBox()) {
							String power = generatePowerup();
							if (power == "FIRE") {
								levelCreator.getRemoveList().add(object);
							}
							else if (power == "BOMB") {
								levelCreator.getRemoveList().add(object);
							}else{
								object.setHaveRectangle(false);
								object.setImage(animationHandler.getTiles().getSubImage(96, 0, 32, 32));
							}
							oneLastLeft = true;
						}
						break;
					}
				}
			}
			if (explodeLeft) {
				explosionArray.add(new Explosion(animationHandler, x - powerCounter * tileSize, y));
			}
			if (oneLastLeft) {
				explosionArray.add(new Explosion(animationHandler, x - powerCounter * tileSize, y));
				oneLastLeft = false;
			}
		}
		//checks if the downwards explosion hits anything interesting.
		if (explodeDown){
			rectangle.setBounds(x+ 1, y+(powerCounter * tileSize)+1, explosionSize, explosionSize);
			for (LevelObject object : levelCreator.getLvlObjects()) {
				if (object.isHaveRectangle()) {
					if (UnitCollission.isColliding(rectangle, object.getRectangle())) {
						explodeDown = false;
						//checks if its is a box, and if it is, changes to ground and remove rectangle
						if (object.isBox()) {
							String power = generatePowerup();
							if (power == "FIRE") {
								levelCreator.getRemoveList().add(object);
							}
							else if (power == "BOMB") {
								levelCreator.getRemoveList().add(object);
							}else{
								object.setHaveRectangle(false);
								object.setImage(animationHandler.getTiles().getSubImage(96, 0, 32, 32));
							}
							oneLastDown = true;
						}
						break;
					}
				}
			}
			if (explodeDown) {
				explosionArray.add(new Explosion(animationHandler, x, y + powerCounter * tileSize));
			}
			if (oneLastDown) {
				explosionArray.add(new Explosion(animationHandler, x, y + powerCounter * tileSize));
				oneLastDown = false;
			}
		}
		//checks if the upwards explosion hits anything interesting.
		if (explodeUp){
			rectangle.setBounds(x+ 1, y - (powerCounter * tileSize)+1, explosionSize, explosionSize);
			for (LevelObject object : levelCreator.getLvlObjects()) {
				if (object.isHaveRectangle()) {
					if (UnitCollission.isColliding(rectangle, object.getRectangle())) {
						explodeUp = false;
						//checks if its is a box, and if it is, changes to ground and remove rectangle
						if (object.isBox()) {
							String power = generatePowerup();
							if (power == "FIRE") {
								levelCreator.getRemoveList().add(object);
							}
							else if (power == "BOMB") {
								levelCreator.getRemoveList().add(object);
							}else{
								object.setHaveRectangle(false);
								object.setImage(animationHandler.getTiles().getSubImage(96, 0, 32, 32));
							}
							oneLastUp = true;
						}
						break;
					}
				}
			}
			if (explodeUp) {
				explosionArray.add(new Explosion(animationHandler, x, y - powerCounter * tileSize));
			}
			if (oneLastUp) {
				explosionArray.add(new Explosion(animationHandler, x, y- powerCounter * tileSize));
				oneLastUp = false;
			}
		}
		
	}
	
}
