package explosionBoy.server;

import java.awt.Rectangle;
import java.util.ArrayList;


public class GameHolder {

	private int gameID;
	private ArrayList<ConnectionReference> references;
	private ArrayList<Rectangle> lvlrectArray;
	private ArrayList<Rectangle> bombExplArra;
	private ArrayList<ServerBomb> serverBombArray;
	private ArrayList<ServerBomb> serverBombRemove;

	public GameHolder() {
		this.serverBombArray = new ArrayList<ServerBomb>();
		this.serverBombRemove = new ArrayList<ServerBomb>();
		this.gameID = 1;
		references = new ArrayList<>();
		references.add(new ConnectionReference(1,this));
		references.add(new ConnectionReference(2,this));
		lvlrectArray = genereRectangles();
		bombExplArra = new ArrayList<Rectangle>();
	}

	public void checkCollissions(ConnectionReference ref){
		int expIndex = serverBombArray.size()-1;
		ConnectionReference p = ref;
		ServerBomb bombToCheck1 = null;
		boolean bombCol = false;
		for (ServerBomb bombToCheck : serverBombArray) {
			for (ServerExplosion ex : bombToCheck.getExplArray()) {
				for (ServerBomb bomb : serverBombArray) {
					if (!bomb.equals(bombToCheck)) {
						UnitCollission.isColliding(bomb.getRect(), ex.getRect());
						bomb.setCountDown(0);
					}
				}
				for (Rectangle lvl : lvlrectArray) {
					if (UnitCollission.isColliding(ex.getRect(), lvl)){
						bombToCheck.cancelDirection(ex.getDirection());
					}
				}
				if (UnitCollission.isColliding(ex.getRect(), ref.getPlayerRect())){
					ref.setxPos(730);
					ref.setyPos(36);
				}

			}
		}
		for (Rectangle lvl : lvlrectArray) {
			if (expIndex>=0) {
				bombToCheck1 = serverBombArray.get(expIndex);
				if (serverBombArray.get(expIndex).checkIfRemove()) serverBombRemove.add(serverBombArray.get(expIndex));
				bombCol = explosionBoy.server.UnitCollission.isColliding(serverBombArray.get(expIndex).getRect(), p.getPlayerRect());
				if (!bombCol && bombToCheck1.isColliding() && bombToCheck1.getPlayerID()==ref.getpID()) serverBombArray.get(expIndex).setColliding(false);;
			}
			boolean collision = explosionBoy.server.UnitCollission.isColliding(p.getPlayerRect(), lvl);
			if (collision || (bombCol && !bombToCheck1.isColliding())) {
				System.out.println("COLLISION!");
				if (lvl.getMaxX()>p.getPlayerRect().getMinX()) {
					p.setxPos(p.getOldX());
				}
				else if (lvl.getMinX()<p.getPlayerRect().getMaxX()) {
					p.setxPos(p.getOldX());
				}
				if (lvl.getMaxY()>p.getPlayerRect().getMinY()) {
					p.setyPos(p.getOldY());
				}
				else if (lvl.getMinY()<p.getPlayerRect().getMaxY()) {
					p.setyPos(p.getOldY());
				}
			}
			expIndex--;
		}
		for (ServerBomb bomRem : serverBombRemove) {
			serverBombArray.remove(bomRem);
		}
		serverBombRemove.clear();
	}


	public boolean addBomb(ConnectionReference cr){
		int numberOfBombs=0;
		ServerBomb bombToAdd = new ServerBomb((int)cr.getxPos(),(int) cr.getyPos(), cr.getpID(), cr.getBombCountdown(), cr.getExplosionSpeed(), cr.getBombPower());
		for (ServerBomb bomb : serverBombArray) {
			if (bombToAdd.getX()==bomb.getX() && bombToAdd.getY()==bomb.getY()){
				return false;
			}
			if (bomb.getPlayerID()==cr.getpID()) {
				numberOfBombs++;
				if (numberOfBombs>=cr.getMaxBombs()) {
					System.out.println("NO bomb: "+numberOfBombs);
					return false;
				}
			}
		}
		for (ServerBomb bomRem : serverBombRemove) {
			serverBombArray.remove(bomRem);
		}
		serverBombRemove.clear();
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

	public ArrayList<java.awt.Rectangle> genereRectangles(){

		ArrayList<java.awt.Rectangle> rectArr = new ArrayList<java.awt.Rectangle>();

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

		for (int row = 0; row < 25; row++) {
			for (int col = 0; col < 19; col++) {
				boolean haveRectangle = true;


				switch (level[row][col]) {
				case 0:
					//					int n = rand.nextInt(32);

					//					if (n == 0) {
					haveRectangle = false;
					//					}
					//					else {
					//						image = tiles.getSubImage(128, 0, 32, 32);
					//					}
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
					rectArr.add(new java.awt.Rectangle(x, y, 32, 32));
				}
			}
		}

		return rectArr;
	}




}
