package explosionBoy.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.google.gson.Gson;

import explosionBoy.server.Json;

public class ServerConnection implements Runnable {
	private DatagramSocket datagramSocket;
	private byte[] recData, sendData;
	private Gson gson;
	private Json jsonRecive, jsonToSend;
	private InetAddress ip;
	
	public ServerConnection() {
		gson = new Gson();
		jsonRecive = new Json();
		jsonToSend = new Json();
		recData = new byte[1024];
		sendData = new byte[1024];
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			System.err.println("Get localhost failed: "+e1.getMessage());
			e1.printStackTrace();
		}
		try {
			datagramSocket = new DatagramSocket();
		} catch (SocketException e) {
			System.err.println("Creating socket failed: "+e.getMessage());
		}
	}
	
	public void send(Json json){
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
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, recivePacket.getPort());
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
		DatagramPacket recivePacket = new DatagramPacket(recData, recData.length);
		try {
			datagramSocket.receive(recivePacket);
			System.out.println("Packet recived!");
		} catch (IOException e) {
			System.err.println("Reciving packet failed: "+e.getMessage());
			e.printStackTrace();
		}
		jsonRecive = gson.fromJson(new String(recivePacket.getData()), Json.class);
		
		}
	}
}
