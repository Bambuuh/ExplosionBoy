package explosionBoy.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

import explosionBoy.client.Json;

public class Server implements Runnable {

	private DatagramSocket datagramSocket;
	private byte[] recData, sendData;
	private Gson gson;
	private Json jsonRecive, jsonToSend;
	private ArrayList<GameHolder> holder, removeHolder;
	private long startTime, endTime, checkTime;
	private ArrayList<Integer> timeArray;
	private long checkInter = 10_000;
	private ServerSocket tcpServerSocket;
	private int gameID;

	public Server() {
		setGameID(0);
		try {
			this.tcpServerSocket = new ServerSocket(9877);
		} catch (IOException e1) {
			System.err.println("Failed to setup server socket: "+e1.getMessage());
			try {
				tcpServerSocket.close();
			} catch (IOException e) {
				System.err.println("Failed to close server socket: "+e.getMessage());
			}
		}
		this.timeArray = new ArrayList<Integer>();
		holder = new ArrayList<GameHolder>();
		removeHolder = new ArrayList<>();
		gson = new Gson();
		jsonRecive = new Json();
		jsonToSend = new Json();
		recData = new byte[512];
		sendData = new byte[512];
		try {
			datagramSocket = new DatagramSocket(9876);
			System.out.println("Server running! v0.1");
		} catch (SocketException e) {
			System.err.println("Creating socket failed: "+e.getMessage());
		}
	}

	private void recive() {
		DatagramPacket recivePacket = new DatagramPacket(recData, recData.length);
		try {
			datagramSocket.receive(recivePacket);
			this.startTime = System.nanoTime();
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
							cr.resetPosition();
							jsonToSend.setDirection("RESET");
							jsonToSend.setpID(cr.getpID());
							jsonToSend.setxPos(cr.getxPos());
							jsonToSend.setyPos(cr.getyPos());
							send(jsonToSend, cr.getIp(), cr.getPort());
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
							if (gh.addBomb(cr)) {
								cr.setAwayFromBomb(false);
								json.setDirection("BOMB");
								json.setxPos(cr.getxPos());
								json.setyPos(cr.getyPos());
								json.setSpeed(cr.getExplosionSpeed());
								json.setbCountDown(cr.getBombCountdown());
								json.setPower(cr.getBombPower());
								json.setpID(cr.getpID());
							}
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
		stopWatch();
	}

	public void send(Json json, InetAddress ip, int port) {
		jsonToSend = json;
		sendData = gson.toJson(jsonToSend, Json.class).getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
		try {
			datagramSocket.send(sendPacket);
//			stopWatch();
		} catch (IOException e) {
			System.err.println("Sending packet failed: "+e.getMessage());
			e.printStackTrace();
		}
		Arrays.fill(sendData,(byte) 0);
	}

	private void stopWatch() {
		this.endTime = System.nanoTime();
		calculateTime(startTime, endTime);
		if (System.currentTimeMillis()>checkTime+checkInter) {
			double average=0;
			double highest=0;
			double lowest= Double.MAX_VALUE;
			for (Integer time : timeArray) {
				average+=time;
				if (time>highest) {
					highest = time;
				}
				else if (time<lowest){
					lowest = time;
				}
			}
			System.out.println("Over the last "+(checkInter/1000)+"Sec the program has been working for: "+(average/1_000_000)+"ms");
			average = ((average/timeArray.size())/1_000_000);
			lowest = lowest/1_000_000;
			highest = highest/1_000_000;
			DecimalFormat dForm = new DecimalFormat("#.###");
			System.out.println("Average time from recive to send: "+dForm.format(average)+"ms | lowest: "+lowest+"ms | highest: "+highest+"ms | "+timeArray.size()+" requests");
			checkTime=System.currentTimeMillis();
			timeArray.clear();
		}
	}

	@Override
	public void run() {
		while (true) {
			recive();
		}
	}
	private void calculateTime(long startTime, long endTime){
		int time = (int) (endTime-startTime);
		timeArray.add(time);
	}
	public ServerSocket getTCPSocket(){
		return tcpServerSocket;
		
	}
	public void pairConnectionWithGame(Socket socket){
		boolean foundGame = false;
		//Createing new player/reference
		ConnectionReference cr=null;
		GameHolder game = null;
		Json json = new Json();
		json.setDirection("PSETTINGS");
		//Checking for empty games and removes them
		for (GameHolder gameToCheck : holder) {
			boolean removeGame = false;
			for (ConnectionReference conReference : gameToCheck.getReferences()) {
				if (!conReference.getTcpConnection().isCloseThisConnection()) {
					removeGame = false;
					break;
				}
				else removeGame = true;
			}
			if (removeGame) {
				removeHolder.add(gameToCheck);
				gameToCheck.setAvailable(false);
				System.out.println("Removing game #"+gameToCheck.getGameID());
			}
		}
		//Checking existing game if there are any space in them.
		for (GameHolder gh : holder) {
			if (gh.getReferences().size()<4 && gh.isAvailable() == true) {
				game = gh;
				cr = new ConnectionReference(gh.getReferences().size()+1, gh);
				gh.getReferences().add(cr);
				cr.setpID(gh.getReferences().size());
				foundGame = true;
				json.setgID(gh.getGameID());
				json.setpID(cr.getpID());
				System.out.println("Found a game!");
				break;
			}
		}
		//if all games are full or if there are no games, a new game is created.
		if (!foundGame) {
			gameID++;
			holder.add(new GameHolder(this, gameID));
			GameHolder gh = holder.get(holder.size()-1);
			game = gh;
			cr = new ConnectionReference(gh.getReferences().size()+1, gh);
			gh.getReferences().add(cr);
			json.setgID(holder.size());
			json.setpID(cr.getpID());
			foundGame = true;
			System.out.println("Creating new game #"+game.getGameID());
		}
		if (foundGame) {
			TCPConnection tcp = new TCPConnection(socket);
			Thread t = new Thread(tcp);
			t.start();
			cr.setTcpConnection(tcp);
			cr.getTcpConnection().send(gson.toJson(json, Json.class));
			cr.getTcpConnection().send(gson.toJson(game.getBoxes(), Json.class));
			System.out.println(gson.toJson(game.getBoxes()));
			System.out.println("Sending gamesettings to player");
			json.setDirection("NEWPLAYER");
			for (ConnectionReference conRef : game.getReferences()) {
				for (ConnectionReference conRef2 : game.getReferences()) {
					json.setpID(conRef2.getpID());
					json.setxPos(conRef2.getxPos());
					json.setyPos(conRef2.getyPos());
					conRef.getTcpConnection().send(gson.toJson(json, Json.class));
				}
			}
		}
		holder.removeAll(removeHolder);
		removeHolder.clear();
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
}
