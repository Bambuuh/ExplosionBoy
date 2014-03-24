package explosionBoy.client;

import java.io.IOException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class SFX {

	private Audio explosion, dropBomb, powerUp;

	public SFX() {
		this.explosion = loadSound("Explosion.aif", "AIF");
		this.dropBomb = loadSound("DropBomb.aif", "AIF");
		this.powerUp = loadSound("PowerUp.aif", "AIF");
	}

	private Audio loadSound(String filename, String filetype){
		Audio sound = null;
		try {
			sound = AudioLoader.getAudio(filetype, ResourceLoader.getResourceAsStream("res/sound/"+filename));
			System.out.println("Loaded sound: "+filename);
		} catch (IOException e) {
			System.err.println("Failed to load sound: "+e.getMessage());
			e.printStackTrace();
		}
		return sound;
	}

	public Audio getExplosion() {
		return explosion;
	}

	public Audio getDropBomb() {
		return dropBomb;
	}

	public Audio getPowerUp() {
		return powerUp;
	}

}
