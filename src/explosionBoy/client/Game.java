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
import explosionBoy.levelobjects.PowerUp;
import explosionBoy.textHandler.TextHandler;

public class Game {

	private ArrayList<Controller> controllArray;
	private ArrayList<Bomb> bombArray;
	private ArrayList<Bomb> addBombArray;
	private ArrayList<Bomb> removeBombArray;

	private SnakeBoy snakeBoy;
	private VortexBoy vortexBoy;
	private SnakeBoy snakeBoy2;
	private LevelCreator level;
	private AnimationHandler animation;
	private UnitCollission collision;
	private BorderDisplay border;
	

	private SFX sfx;
	
	public static final int BORDERHEIGHT = 64;
	public static final int BOARDHEIGHT = 600;
	public static final int WIDTH = 800;
	public static final int HEIGHT = BOARDHEIGHT + BORDERHEIGHT;
	private ServerConnection connection;
	private ServerTCP serverTCP;
	private InputReader input;

	private long lastFrame;

	private int playerID;

	public Game(){
		initGL();
		playerID = 0;
		sfx = new SFX();
		collision = new UnitCollission();
		animation = new AnimationHandler();
		controllArray = new ArrayList<Controller>();
		bombArray = new ArrayList<>();
		addBombArray = new ArrayList<Bomb>();
		removeBombArray = new ArrayList<>();
		level = new LevelCreator(animation);
		connection = new ServerConnection(controllArray, level);
		input = new InputReader(connection);
		serverTCP = new ServerTCP(this);
		border = new BorderDisplay(controllArray, animation);
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

		while(!Display.isCloseRequested()){
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			int delta = getDelta();
			
			input();
			update(delta);
			render(delta);
			
			Display.update();
			Display.sync(60);
		}
		connection.close();
		serverTCP.closeAllConnections();
	}
	
	public void input(){
		if (!(playerID==0)) {
			input.readInput(playerID);
		}
	}
	
	public void update(int delta) {
		updatePlayers(delta);
		updateBombs(delta);
		checkCollisions(delta);
		cleanUp();
	}
	
	public void render(int delta){
		level.printLevel();
		renderBombs();
		renderPlayers(delta);
		renderExplosions();
		border.render();
	}
	
	public void updatePlayers(int delta){
		for (Controller player : controllArray) {
			player.autoMove();
			player.getPlayer().update(delta);
		}
	}

	public void updateBombs(int delta){
		for (Bomb bomb : bombArray) {
			bomb.update(delta, level);
		}
		bombArray.addAll(addBombArray);
		addBombArray.clear();
	}
	
	public SFX getSfx() {
		return sfx;
	}

	public void renderPlayers(int delta){
		for (Controller player : controllArray) {
			player.getPlayer().render(delta);
		}
	}
	
	public void renderBombs(){
		for (Bomb bomb : bombArray) {
			bomb.render(level, sfx);
		}
	}
	
	public void renderExplosions(){
		for (Bomb bomb : bombArray) {
			bomb.renderExplosions();
		}
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
				if (lvl instanceof PowerUp) {
					boolean collision = UnitCollission.isColliding(p.getRectangle(), lvl.getRectangle());
					if (collision) {
						level.getRemoveList().add(lvl);
						sfx.getPowerUp().playAsSoundEffect(1f, 0.2f, false);
					}
				}
				else if (lvl.isHaveRectangle()) {
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
		}
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
