package explosionBoy.client;

public class Main {

	public static void main(String[] args) {
		new LoadNative().loadNatives();
		new Game().initGL();
	}

}