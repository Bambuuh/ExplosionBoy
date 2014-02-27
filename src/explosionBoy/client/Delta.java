package explosionBoy.client;

import org.lwjgl.Sys;

public class Delta {

	private long lastFrame;
	
	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public long getLastFrame(){
		return lastFrame;
	}
	
}
