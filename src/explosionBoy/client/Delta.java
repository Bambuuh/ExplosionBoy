package explosionBoy.client;

public class Delta {

	private long lastFrame;
	
	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	public long getTime() {
		return (System.currentTimeMillis() * 1000) / 1000;
	}
	
	public long getLastFrame(){
		return lastFrame;
	}
	
}
