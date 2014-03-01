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
	private ArrayList<Bomb> removeBombArray;
	
	private SnakeBoy snakeBoy;
	private SnakeBoy snakeBoy2;

	private AnimationHandler animation;
	private LevelCreator level;

	public static final int WIDTH = 800;
	public static final int HEIGHT = 608;
	private ServerConnection connection;
	private InputReader input;

	private long lastFrame;

	public Game(){
		initGL();
		animation = new AnimationHandler();
		controllArray = new ArrayList<Controller>();
		bombArray = new ArrayList<>();
		removeBombArray = new ArrayList<>();
		snakeBoy = new SnakeBoy(40,30 , animation, 1);
		snakeBoy2 = new SnakeBoy(WIDTH-50, 30, animation, 2);
		controllArray.add(new Controller(snakeBoy));
		controllArray.add(new Controller(snakeBoy2));
		connection = new ServerConnection(controllArray);
		input = new InputReader(connection);
		level = new LevelCreator();
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

		snakeBoy.setPlayerAnimation(animation, 16, 0, 16, false, false);
		snakeBoy2.setPlayerAnimation(animation, 16, 0, 16, false, false);

		while(!Display.isCloseRequested()){
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			int delta = getDelta();
			update(delta);
			level.printLevel();
//			snakeBoy2.update(delta);
			snakeBoy.update(delta, bombArray);
			updateBombs(delta);
			checkCollisions(delta);
			removeBombs();
			input.readInput();

			Display.update();
			Display.sync(60);
		}
		connection.close();
	}
	
	public void updateBombs(int delta){
		for (Bomb bomb : bombArray) {
			bomb.update(delta);
		}
	}
	
	public void removeBombs(){
		for (Bomb bomb : bombArray) {
			if (bomb.getExploded()) {
				removeBombArray.add(bomb);
			}
		}
		for (Bomb bomb : removeBombArray) {
			bombArray.remove(bomb);
		}
	}
	
	public void checkCollisions(int delta){
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
		}
	}

	public void update(int delta) {

	}
}
