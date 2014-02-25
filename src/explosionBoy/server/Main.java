package explosionBoy.server;


public class Main {

	public static void main(String[] args) {
		
		Server s = new Server();
		Thread t = new Thread(s);
		t.start();
		
	}

}
