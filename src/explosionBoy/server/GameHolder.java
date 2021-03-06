package explosionBoy.server;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;


import explosionBoy.client.Json;


public class GameHolder {

	private int gameID;
	private ArrayList<ConnectionReference> references;
	private ArrayList<LevelObject> lvlrectArray;
	private ArrayList<LevelObject> lvlRemoveArr;
	private ArrayList<LevelObject> lvlAddArray;
	private ArrayList<ServerBomb> serverBombArray;
	private ArrayList<ServerBomb> serverBombRemove;
	private Server server;
	private Random rand;
	private boolean available;

	public GameHolder(Server server, int gameID) {
		this.setAvailable(true);
		this.rand = new Random();
		this.server = server;
		this.serverBombArray = new ArrayList<ServerBomb>();
		this.serverBombRemove = new ArrayList<ServerBomb>();
		this.lvlAddArray = new ArrayList<>();
		this.gameID = gameID;
		references = new ArrayList<>();
		lvlrectArray = genereRectangles();
		lvlRemoveArr = new ArrayList<LevelObject>();
	}

	public void checkCollissions(ConnectionReference ref){
		boolean bombCol = false;
		explosionsCollisions(ref);
		bombCol = playerVsBombCol(ref, bombCol);
		checkCollisionAndReset(ref, bombCol);
		clean();
	}

	private void explosionsCollisions(ConnectionReference ref) {
		for (ServerBomb bombToCheck : serverBombArray) {
			for (ServerExplosion ex : bombToCheck.getExplArray()) {
				for (ServerBomb bomb : serverBombArray) {
					if (!bomb.equals(bombToCheck)) {
						if (UnitCollission.isColliding(bomb.getRect(), ex.getRect())) {
							bomb.setCountDown(0);
						}
					}
				}
				for (LevelObject lvl : lvlrectArray) {
					if (UnitCollission.isColliding(ex.getRect(), lvl.getRectangle())){
						bombToCheck.cancelDirection(ex.getDirection());
						if (lvl.isBox()) {
							randomPowerUp(lvl);
							lvlRemoveArr.add(lvl);
						}
					}
				}
				lvlrectArray.addAll(lvlAddArray);
				if (UnitCollission.isColliding(ex.getRect(), ref.getPlayerRect())){
					ref.resetPosition();
					updatePosToClient(ref);
				}

			}
		}
	}

	private boolean playerVsBombCol(ConnectionReference ref, boolean bombCol) {
		for (ServerBomb bomb : serverBombArray) {
			bombCol = explosionBoy.server.UnitCollission.isColliding(bomb.getRect(), ref.getPlayerRect());
			if (bombCol && bomb.getCollidingPlayers().contains(ref)) {
				bombCol = false;
			}
			else if(bombCol && !bomb.getCollidingPlayers().contains(ref)){
				break;
			}
			else if (!bombCol && bomb.getCollidingPlayers().contains(ref)){ 
				bomb.getCollidingPlayers().remove(ref);
			}
			if (bomb.checkIfRemove()){
				serverBombRemove.add(bomb);
			}
		}
		return bombCol;
	}

	private void checkCollisionAndReset(ConnectionReference ref, boolean bombCol) {
		for (LevelObject lvl : lvlrectArray) {
			if (checkRange(lvl.getRectangle(), ref)) {
				boolean collision = explosionBoy.server.UnitCollission.isColliding(ref.getPlayerRect(), lvl.getRectangle());
				if (lvl.isBuff()) {
					if (lvl instanceof FlameBuff) {
						ref.setBombPower(ref.getBombPower()+1);
					}
					else if (lvl instanceof BombPower) {
						ref.setMaxBombs(ref.getMaxBombs()+1);
					}
					lvlRemoveArr.add(lvl);
					continue;
				}
				if (collision || bombCol) {
					if (lvl.getRectangle().getMaxX()>ref.getPlayerRect().getMinX()) {
						ref.setxPos(ref.getOldX());
					}
					else if (lvl.getRectangle().getMinX()<ref.getPlayerRect().getMaxX()) {
						ref.setxPos(ref.getOldX());
					}
					if (lvl.getRectangle().getMaxY()>ref.getPlayerRect().getMinY()) {
						ref.setyPos(ref.getOldY());
					}
					else if (lvl.getRectangle().getMinY()<ref.getPlayerRect().getMaxY()) {
						ref.setyPos(ref.getOldY());
					}
					updatePosToClient(ref);
				}
			}
		}
	}

	private void clean() {
		serverBombArray.removeAll(serverBombRemove);
		lvlrectArray.removeAll(lvlRemoveArr);
		lvlRemoveArr.clear();
		serverBombRemove.clear();
		lvlAddArray.clear();
	}

	private void updatePosToClient(ConnectionReference ref) {
		Json j = new Json();
		j.setDirection("RESET");
		j.setpID(ref.getpID());
		j.setxPos(ref.getxPos());
		j.setyPos(ref.getyPos());
		for (ConnectionReference reference : references) {
			if (reference.getIp() != null && !reference.getTcpConnection().isCloseThisConnection()) {
				server.send(j, reference.getIp(), reference.getPort());
			}
		}
	}

	private boolean checkRange(Rectangle lvl, ConnectionReference ref){
		boolean inRange = true;
		if (lvl.getX()>(ref.getxPos()+32)) inRange = false;
		if (lvl.getX()<(ref.getxPos()-32)) inRange = false;
		if (lvl.getY()>(ref.getyPos()+32)) inRange = false;
		if (lvl.getY()<(ref.getyPos()-32)) inRange = false;
		return inRange;
	}

	public boolean addBomb(ConnectionReference cr){
		int numberOfBombs=0;
		ServerBomb bombToAdd = new ServerBomb((int)cr.getxPos(),(int) cr.getyPos(), cr.getpID(), cr.getBombCountdown(), cr.getExplosionSpeed(), cr.getBombPower());
		for (ServerBomb bomb : serverBombArray) {
			if (bombToAdd.getX()==bomb.getX() && bombToAdd.getY()==bomb.getY()){
				return false;
			}
			if (bomb.getPlayerID()==cr.getpID()) {
				if (!bomb.isExploding()) {
					numberOfBombs++;
				}
				if (numberOfBombs>=cr.getMaxBombs()) {
					System.out.println("NO bomb: "+numberOfBombs);
					return false;
				}
			}
		}
		for (ConnectionReference ref : references) {
			if (UnitCollission.isColliding(ref.getPlayerRect(), bombToAdd.getRect())) {
				bombToAdd.getCollidingPlayers().add(ref);
			}
		}
		serverBombArray.add(bombToAdd);
		return true;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public ArrayList<ConnectionReference> getReferences() {
		return references;
	}

	public void setReferences(ArrayList<ConnectionReference> references) {
		this.references = references;
	}

	public ArrayList<LevelObject> genereRectangles(){

		ArrayList<LevelObject> rectArr = new ArrayList<LevelObject>();

		int[][] level = new int[][]{
				{1,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4},
				{6,10,10,0,0,0,0,0,0,0,0,0,0,0,0,0,10,10,8},
				{6,10,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,10,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,0,8},
				{6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{6,10,9,0,9,0,9,0,9,0,9,0,9,0,9,0,9,10,8},
				{6,10,10,0,0,0,0,0,0,0,0,0,0,0,0,0,10,10,8},
				{2,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,3},
		};
		Random rand = new Random();
		for (int row = 0; row < 25; row++) {
			for (int col = 0; col < 19; col++) {
				boolean haveRectangle = true;
				boolean isBox = false;

				switch (level[row][col]) {
				case 0:
					int n = rand.nextInt(2);
					if (n == 0) {
						haveRectangle = false;
					}
					else {
						isBox = true;
					}
					break;
				case 10:
					haveRectangle = false;
					break;
				default:
					break;
				}
				int x = 32*row;
				int y = 32*col;
				if (haveRectangle) {
					rectArr.add(new LevelObject(x, y, haveRectangle, isBox));
				}
			}
		}

		return rectArr;
	}

	public Json getBoxes(){
		Json jSonToSend = new Json();
		jSonToSend.setDirection("BOXARRAY");
		for (LevelObject lvl : lvlrectArray) {
			if (lvl.isBox()) {
				Json jToAdd = new Json();
				jToAdd.setxPos(lvl.getX());
				jToAdd.setyPos(lvl.getY());
				jSonToSend.getjArr().add(jToAdd);
			}
		}
		return jSonToSend;
	}

	private void randomPowerUp(LevelObject lvl){
		Random randomizer = new Random();
		int random = randomizer.nextInt(2);
		if (rand.nextInt(2)<1) {
			Json j = new Json();
			j.setpID(99);
			switch (random) {
			case 0:
				j.setDirection("BOMBPOWER");
				lvlAddArray.add(new BombPower(lvl.getX(), lvl.getY(), true, false));
				break;
			case 1:
				j.setDirection("FIREPOWER");
				lvlAddArray.add(new FlameBuff(lvl.getX(), lvl.getY(), true, false));
				break;
			default:
				break;
			}
			j.setxPos(lvl.getX());
			j.setyPos(lvl.getY());
			for (ConnectionReference reference : references) {
				if (reference.getIp() != null && !reference.getTcpConnection().isCloseThisConnection()) {
					server.send(j, reference.getIp(), reference.getPort());
				}
			}
		}
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

}
