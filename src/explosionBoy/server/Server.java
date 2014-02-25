package explosionBoy.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.lwjgl.Sys;

import com.google.gson.Gson;


public class Server implements Runnable {

	private DatagramSocket datagramSocket;
	private byte[] recData, sendData;
	private Gson gson;
	private Json jsonRecive, jsonToSend;
	
	
	public Server() {
		gson = new Gson();
		jsonRecive = new Json();
		jsonToSend = new Json();
		recData = new byte[1024];
		sendData = new byte[1024];
		try {
			datagramSocket = new DatagramSocket(9876);
		} catch (SocketException e) {
			System.err.println("Creating socket failed: "+e.getMessage());
		}
		
	}


	private void echo() {
			DatagramPacket recivePacket = new DatagramPacket(recData, recData.length);
			try {
				datagramSocket.receive(recivePacket);
				System.out.println("Packet recived!");
			} catch (IOException e) {
				System.err.println("Reciving packet failed: "+e.getMessage());
				e.printStackTrace();
			}
			jsonRecive = gson.fromJson(new String(recivePacket.getData()), Json.class);
			
			jsonToSend.setDirection(jsonRecive.getDirection());
			jsonToSend.setSpeed(8);
			
			sendData = gson.toJson(jsonToSend, Json.class).getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, recivePacket.getAddress(), recivePacket.getPort());
			try {
				datagramSocket.send(sendPacket);
			} catch (IOException e) {
				System.err.println("Sending packet failed: "+e.getMessage());
				e.printStackTrace();
			}
	}


	@Override
	public void run() {
		while (true) {
			echo();
		}
	}
}
