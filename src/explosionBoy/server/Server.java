package explosionBoy.server;

import java.net.DatagramSocket;
import java.net.SocketException;


public class Server {

	private DatagramSocket datagramSocket;
	private byte[] recData, sendData;
	
	
	public Server() {
		recData = new byte[1024];
		sendData = new byte[1024];
		
		try {
			datagramSocket = new DatagramSocket(9876);
		} catch (SocketException e) {
			System.err.println("Creating socket failed: "+e.getMessage());
		}
		
	}
}
