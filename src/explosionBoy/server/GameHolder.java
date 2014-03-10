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
	private ArrayList<ServerBomb> serverBombArray;
	private ArrayList<ServerBomb> serverBombRemove;
	private Server server;

	public GameHolder(Server server, int gameID) {
		this.server = server;
		this.serverBombArray = new ArrayList<ServerBomb>();
		this.serverBombRemove = new ArrayList<ServerBomb>();
		this.gameID = gameID;
		references = new ArrayList<>();
		lvlrectArray = genereRectangles();
		lvlRemoveArr = new ArrayList<LevelObject>();
	}

	public void checkCollissions(ConnectionReference ref){
		int serverBombIndex = serverBombArray.size()-1;
		ConnectionReference p = ref;
		ServerBomb bombToCheck1 = null;
		boolean bombCol = false;
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
							lvlRemoveArr.add(lvl);
						}
					}
				}
				if (UnitCollission.isColliding(ex.getRect(), ref.getPlayerRect())){
					ref.resetPosition();
					Json j = new Json();
					j.setDirection("RESET");
					j.setpID(ref.getpID());
					j.setxPos(ref.getxPos());
					j.setyPos(ref.getyPos());
					for (ConnectionReference reference : references) {
						if (reference.getIp() != null) {
							server.send(j, reference.getIp(), reference.getPort());
						}
					}
				}

			}
		}
		for (LevelObject lvl : lvlrectArray) {
			if (serverBombIndex>=0) {
				bombToCheck1 = serverBombArray.get(serverBombIndex);
				if (!bombToCheck1.isExploding()) {
					bombCol = explosionBoy.server.UnitCollission.isColliding(bombToCheck1.getRect(), p.getPlayerRect());
					if (bombToCheck1.getCollidingPlayers().contains(ref)) {
						bombCol = false;
					}
				}
				if (!bombCol && bombToCheck1.getCollidingPlayers().contains(ref)){ 
					bombToCheck1.getCollidingPlayers().remove(ref);
				}
				if (serverBombArray.get(serverBombIndex).checkIfRemove()) serverBombRemove.add(serverBombArray.get(serverBombIndex));
			}
			if (checkRange(lvl.getRectangle(), ref)) {
				boolean collision = explosionBoy.server.UnitCollission.isColliding(p.getPlayerRect(), lvl.getRectangle());
				if (collision || bombCol) {
					if (lvl.getRectangle().getMaxX()>p.getPlayerRect().getMinX()) {
						p.setxPos(p.getOldX());
					}
					else if (lvl.getRectangle().getMinX()<p.getPlayerRect().getMaxX()) {
						p.setxPos(p.getOldX());
					}
					if (lvl.getRectangle().getMaxY()>p.getPlayerRect().getMinY()) {
						p.setyPos(p.getOldY());
					}
					else if (lvl.getRectangle().getMinY()<p.getPlayerRect().getMaxY()) {
						p.setyPos(p.getOldY());
					}
				}
			}
			serverBombIndex--;
		}
		serverBombArray.removeAll(serverBombRemove);
		lvlrectArray.removeAll(lvlRemoveArr);
		lvlRemoveArr.clear();
		serverBombRemove.clear();
	}

	private boolean checkRange(Rectangle lvl, ConnectionReference ref){
		boolean inRange = true;
		if (lvl.getX()>(ref.getxPos()+32)) inRange = false;
		else if (lvl.getX()<(ref.getxPos()-32)) inRange = false;
		else if (lvl.getY()>(ref.getyPos()+32)) inRange = false;
		else if (lvl.getY()<(ref.getyPos()-32)) inRange = false;
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
				System.out.println("BOX X: "+lvl.getX());
				Json jToAdd = new Json();
				jToAdd.setxPos(lvl.getX());
				jToAdd.setyPos(lvl.getY());
				jSonToSend.getjArr().add(jToAdd);
			}
		}
		return jSonToSend;
	}


}
