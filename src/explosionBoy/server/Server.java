package explosionBoy.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

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
			System.out.println("Server running!");
		} catch (SocketException e) {
			System.err.println("Creating socket failed: "+e.getMessage());
		}
	}

	private void recive() {
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
			CheckIp:
			for (GameHolder gh : holder) {
				if (gh.getGameID()==jsonRecive.getgID()) {
					for (ConnectionReference cr : gh.getReferences()) {
						if (cr.getIp() != null && cr.getIp().equals(recivePacket.getAddress()) && cr.getPort()==recivePacket.getPort()) {
							break CheckIp;
						}
						else if (cr.getpID()==jsonRecive.getpID()) {
							cr.setIp(recivePacket.getAddress());
							cr.setPort(recivePacket.getPort());
							System.out.println("Set new ip/port to: "+cr.getpID());
							break CheckIp;
						}
					}
				}
			}
			Json json = new Json();
			gameloop:
			for (GameHolder gh : holder) {
				if (gh.getGameID()==jsonRecive.getgID()) {
					for (ConnectionReference cr : gh.getReferences()) {
						if (jsonRecive.getDirection().equals("BOMB") && jsonRecive.getpID() == cr.getpID()) {
							json.setDirection("BOMB");
							json.setxPos(cr.getxPos());
							json.setyPos(cr.getyPos());
							json.setSpeed(cr.getExplosionSpeed());
							json.setbCountDown(cr.getBombCountdown());
							json.setPower(cr.getBombPower());
							json.setpID(cr.getpID());
							//add bomb to array here!
							break;
						}
						else if (jsonRecive.getpID() == cr.getpID()) {
							cr.setDir(jsonRecive.getDirection());
							json.setpID(cr.getpID());
							json.setDirection(cr.getDir());
							json.setxPos(cr.getxPos());
							json.setyPos(cr.getyPos());
							json.setSpeed(cr.getSpeed());
							break;
						}
					}
					for (ConnectionReference cr : gh.getReferences()) {
						if (cr.getIp() != null) {
							send(json, cr.getIp(), cr.getPort());
						}
					}
					break gameloop;
				}
			}
			Arrays.fill(recData,(byte) 0);
	}

	public void send(Json json, InetAddress ip, int port) {
		jsonToSend = json;
		sendData = gson.toJson(jsonToSend, Json.class).getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
		try {
			datagramSocket.send(sendPacket);
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
