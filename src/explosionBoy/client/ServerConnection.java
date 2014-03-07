package explosionBoy.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

import explosionBoy.client.Json;

public class ServerConnection implements Runnable {
	private DatagramSocket datagramSocket;
	private byte[] recData, sendData;
	private Gson gson;
	private Json jsonRecive;
	private InetAddress ip;
	private ArrayList<Controller> controllant;
	private boolean isReciving;
	
	public ServerConnection(ArrayList<Controller> controller) {
		this.controllant = controller;
		isReciving = true;
		gson = new Gson();
		jsonRecive = new Json();
		recData = new byte[512];
		sendData = new byte[512];
		try {
			ip = InetAddress.getByName("gurey.synology.me");
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
		json.setgID(1);
		sendData = gson.toJson(json, Json.class).getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, 9876);
		try {
			datagramSocket.send(sendPacket);
		} catch (IOException e) {
			System.err.println("Sending packet failed: "+e.getMessage());
			e.printStackTrace();
		}
		Arrays.fill(sendData, (byte)0);
	}

	@Override
	public void run() {
		System.out.println("recive thread started!");
		while (isReciving) {
		DatagramPacket recivePacket = new DatagramPacket(recData, recData.length);
		try {
			datagramSocket.receive(recivePacket);
		} catch (IOException e) {
			System.err.println("Reciving packet failed: "+e.getMessage());
			e.printStackTrace();
		}
		String incomming = new String(recivePacket.getData());
		incomming = incomming.trim();
		jsonRecive = gson.fromJson(incomming, Json.class);
		for (Controller controller : controllant) {
			if (controller.id == jsonRecive.getpID()) {
				controller.controll(jsonRecive);
				break;
			}
		}
		Arrays.fill(recData, (byte)0);
		}
	}
	
	public void close(){
		datagramSocket.close();
		isReciving = false;
	}
}
