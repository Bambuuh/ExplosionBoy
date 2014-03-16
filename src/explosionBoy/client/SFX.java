package explosionBoy.client;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SFX {

	private Sound explosion;
	
	public SFX() {
		try {
			this.setExplosion(new Sound("/res/sound/explosion.ogg"));
		} catch (SlickException e) {
			System.err.println("Could not load sound: "+e.getMessage());
			e.printStackTrace();
		}
	}

	public Sound getExplosion() {
		return explosion;
	}

	public void setExplosion(Sound explosion) {
		this.explosion = explosion;
	}
	
}
