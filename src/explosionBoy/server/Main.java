package explosionBoy.server;

import java.io.IOException;
import java.net.Socket;

public class Main {

	
	public static void main(String[] args) {
		Server s = new Server();
		Thread t = new Thread(s);
		t.start();
		while (true) {
			try {
				Socket clientSocket = s.getTCPSocket().accept();
				s.pairConnectionWithGame(clientSocket);
			} catch (IOException e) {
				System.err.println("Failed to setup ");
			}
		}
	}
}