package explosionBoy.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

import explosionBoy.client.Game;
import explosionBoy.client.Json;

public class Server implements Runnable {

	private DatagramSocket datagramSocket;
	private byte[] recData, sendData;
	private Gson gson;
	private Json jsonRecive, jsonToSend;
	private ArrayList<GameHolder> holder;
	
	public Server() {
		holder = new ArrayList<GameHolder>();
		holder.add(new GameHolder());
		gson = new Gson();
		jsonRecive = new Json();
		jsonToSend = new Json();
		recData = new byte[512];
		sendData = new byte[512];
		try {
			datagramSocket = new DatagramSocket(9876);
		} catch (SocketException e) {
			System.err.println("Creating socket failed: "+e.getMessage());
		}
	}

	private void recive() {
			DatagramPacket recivePacket = new DatagramPacket(recData, recData.length);
			try {
				datagramSocket.receive(recivePacket);
				System.out.println("Packet recived!");
			} catch (IOException e) {
				System.err.println("Reciving packet failed: "+e.getMessage());
				e.printStackTrace();
			}
			
			String incomming = new String(recivePacket.getData());
			System.out.println(incomming.length());
			System.out.println(incomming);
			incomming = incomming.trim();
			System.out.println(incomming.length());
			
			jsonRecive = gson.fromJson(incomming, Json.class);
			CheckIp:
			for (GameHolder gh : holder) {
				if (gh.getGameID()==jsonRecive.getgID()) {
					System.out.println("Found game!");
					for (ConnectionReference cr : gh.getReferences()) {
						if (cr.getIp().equals(recivePacket.getAddress()) && cr.getPort()==recivePacket.getPort()) {
							System.out.println("Breaking ipcheck because address exists allrdy");
							break CheckIp;
						}
						else if (cr.getpID()==jsonRecive.getpID()) {
							cr.setIp(recivePacket.getAddress());
							cr.setPort(recivePacket.getPort());
							System.out.println("Set new ip/port to: "+cr.getpID());
						}
					}
				}
			}
			ConnectionReference conRef = new ConnectionReference();
			for (GameHolder gh : holder) {
				if (gh.getGameID()==jsonRecive.getgID()) {
					for (ConnectionReference cr : gh.getReferences()) {
						if (jsonRecive.getpID() == cr.getpID()) {
							cr.setDir(jsonRecive.getDirection());
							conRef.setpID(cr.getpID());
							conRef.setDir(cr.getDir());
							break;
						}
					}
					for (ConnectionReference cr : gh.getReferences()) {
							send(conRef, cr.getIp(), cr.getPort());
					}
				}
			}
			Arrays.fill(recData,(byte) 0);
	}

	public void send(ConnectionReference cr, InetAddress ip, int port) {
		jsonToSend.setDirection(cr.getDir());
		jsonToSend.setSpeed(cr.getSpeed());
		jsonToSend.setxPos(cr.getxPos());
		jsonToSend.setyPos(cr.getyPos());
		jsonToSend.setpID(cr.getpID());
		sendData = gson.toJson(jsonToSend, Json.class).getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
		try {
			datagramSocket.send(sendPacket);
			System.out.println("SENDING!");
		} catch (IOException e) {
			System.err.println("Sending packet failed: "+e.getMessage());
			e.printStackTrace();
		}
		Arrays.fill(sendData,(byte) 0);
	}

	@Override
	public void run() {
		while (true) {
			recive();
		}
	}
}
