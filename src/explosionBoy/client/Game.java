package explosionBoy.client;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import explosionBoy.levelobjects.LevelObject;

public class Game {

	private ArrayList<Controller> controllArray;
	private ArrayList<Bomb> bombArray;
	private ArrayList<Bomb> addBombArray;
	private ArrayList<Bomb> removeBombArray;

	private SnakeBoy snakeBoy;
	private SnakeBoy snakeBoy2;

	private AnimationHandler animation;
	private LevelCreator level;
	private UnitCollission collision;

	public static final int WIDTH = 800;
	public static final int HEIGHT = 608;
	private ServerConnection connection;
	private ServerTCP serverTCP;
	private InputReader input;

	private long lastFrame;

	private int playerID;

	public Game(){
		initGL();
		playerID = 0;
		collision = new UnitCollission();
		animation = new AnimationHandler();
		controllArray = new ArrayList<Controller>();
		bombArray = new ArrayList<>();
		addBombArray = new ArrayList<Bomb>();
		removeBombArray = new ArrayList<>();
		connection = new ServerConnection(controllArray);
		input = new InputReader(connection);
		level = new LevelCreator(animation);
		serverTCP = new ServerTCP(this);
		new Thread(serverTCP).start();
		new Thread(connection).start();
		start();
	}

	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public void initGL(){
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setVSyncEnabled(true);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}


		glViewport(0, 0, WIDTH, HEIGHT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		glEnable(GL_TEXTURE_2D);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		//Ortho is the dimentions of the game in x, y and z axis
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

	}

	public void start(){

//		snakeBoy.setPlayerAnimation(animation, 16, 0, 16, false, false);
//		snakeBoy2.setPlayerAnimation(animation, 16, 0, 16, false, false);

		while(!Display.isCloseRequested()){
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			int delta = getDelta();
			update(delta);
			level.printLevel();
			updateBombs(delta);
			for (Controller player : controllArray) {
				player.getPlayer().update(delta);
			}
			checkCollisions(delta);
			cleanUp();
			if (!(playerID==0)) {
				input.readInput(playerID);
			}
			Display.update();
			Display.sync(60);
		}
		connection.close();
		serverTCP.closeAllConnections();
	}

	public void updateBombs(int delta){
		for (Bomb bomb : bombArray) {
			bomb.update(delta, level);
		}
		bombArray.addAll(addBombArray);
		addBombArray.clear();
	}

	public void cleanUp(){
		for (Bomb bomb : bombArray) {
			if (bomb.getExploded()) {
				removeBombArray.add(bomb);
			}
		}
		for (Bomb bomb : removeBombArray) {
			bombArray.remove(bomb);
		}
		removeBombArray.clear();
		for (LevelObject object : level.getRemoveList()) {
			level.getLvlObjects().remove(object);
		}
		level.getRemoveList().clear();
	}

	public void checkCollisions(int delta){
		for (Bomb bombToCheck : bombArray) {
			for (Explosion explosion : bombToCheck.getExplosionArray()) {
				for (Bomb bomb : bombArray) {
					if (!bomb.equals(bombToCheck)) {
						if (UnitCollission.isColliding(bomb.getRectangle(), explosion.getRectangle())) {
							bomb.setBombCountDown(0);
						}
					}
				}
			}
		}
		for (Controller player : controllArray) {
			player.setDelta(delta);
			Player p = player.getPlayer();
			for (LevelObject lvl : level.getLvlObjects()) {
				if (lvl.isHaveRectangle()) {
					boolean collision = UnitCollission.isColliding(p.getRectangle(), lvl.getRectangle());
					if (collision) {
						if (lvl.getRectangle().getMaxX()>p.getRectangle().getMinX()) {
							p.setX(p.oldx);
						}
						else if (lvl.getRectangle().getMinX()<p.getRectangle().getMaxX()) {
							p.setX(p.oldx);
						}
						if (lvl.getRectangle().getMaxY()>p.getRectangle().getMinY()) {
							p.setY(p.oldy);
						}
						else if (lvl.getRectangle().getMinY()<p.getRectangle().getMaxY()) {
							p.setY(p.oldy);
						}
					}
				}
			}
			player.autoMove();
		}
	}

	public void update(int delta) {

	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public ServerConnection getConnection() {
		return connection;
	}

	public void setConnection(ServerConnection connection) {
		this.connection = connection;
	}

	public ArrayList<Controller> getControllArray() {
		return controllArray;
	}

	public void setControllArray(ArrayList<Controller> controllArray) {
		this.controllArray = controllArray;
	}

	public ArrayList<Bomb> getBombArray() {
		return bombArray;
	}

	public void setBombArray(ArrayList<Bomb> bombArray) {
		this.bombArray = bombArray;
	}

	public ArrayList<Bomb> getAddBombArray() {
		return addBombArray;
	}

	public void setAddBombArray(ArrayList<Bomb> addBombArray) {
		this.addBombArray = addBombArray;
	}

	public ArrayList<Bomb> getRemoveBombArray() {
		return removeBombArray;
	}

	public void setRemoveBombArray(ArrayList<Bomb> removeBombArray) {
		this.removeBombArray = removeBombArray;
	}

	public SnakeBoy getSnakeBoy() {
		return snakeBoy;
	}

	public void setSnakeBoy(SnakeBoy snakeBoy) {
		this.snakeBoy = snakeBoy;
	}

	public SnakeBoy getSnakeBoy2() {
		return snakeBoy2;
	}

	public void setSnakeBoy2(SnakeBoy snakeBoy2) {
		this.snakeBoy2 = snakeBoy2;
	}

	public AnimationHandler getAnimation() {
		return animation;
	}

	public void setAnimation(AnimationHandler animation) {
		this.animation = animation;
	}

	public LevelCreator getLevel() {
		return level;
	}

	public void setLevel(LevelCreator level) {
		this.level = level;
	}

	public UnitCollission getCollision() {
		return collision;
	}

	public void setCollision(UnitCollission collision) {
		this.collision = collision;
	}

	public ServerTCP getServerTCP() {
		return serverTCP;
	}

	public void setServerTCP(ServerTCP serverTCP) {
		this.serverTCP = serverTCP;
	}

	public InputReader getInput() {
		return input;
	}

	public void setInput(InputReader input) {
		this.input = input;
	}

	public long getLastFrame() {
		return lastFrame;
	}

	public void setLastFrame(long lastFrame) {
		this.lastFrame = lastFrame;
	}
}
