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

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import explosionBoy.levelobjects.LevelObject;

public class Game {
	
	private SnakeBoy snakeBoy;
	
	private AnimationHandler animation;
	private LevelCreator level;
	
	private int dWidth = 800;
	private int dHeight = 608;
	private ServerConnection connection;
	private Controller controller;
	private InputReader input;
	
	
	private long lastFrame;
	
	public Game(){
		initGL();
		animation = new AnimationHandler();
		snakeBoy = new SnakeBoy(100, 100, animation);
		controller = new Controller(snakeBoy);
		connection = new ServerConnection(controller);
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
			Display.setDisplayMode(new DisplayMode(dWidth, dHeight));
			Display.setVSyncEnabled(true);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		
		glViewport(0, 0, dWidth, dHeight);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		glEnable(GL_TEXTURE_2D);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		//Ortho is the dimentions of the game in x, y and z axis
		glOrtho(0, dWidth, dHeight, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

	}
	
	public void start(){
		
		snakeBoy.setPlayerAnimation(animation, 16, 0, 16, false, false);
		
		while(!Display.isCloseRequested()){
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			int delta = getDelta();
			update(delta);
			
			level.printLevel();
			
			input.readInput();
			snakeBoy.update(delta);
			
			for (LevelObject lvl : level.getLvlObjects()) {
				if (lvl.isHaveRectangle()) {
					boolean collision = UnitCollission.isColliding(snakeBoy.getRectangle(), lvl.getRectangle());
					if (collision) {
						snakeBoy.setX(snakeBoy.oldx);
						snakeBoy.setY(snakeBoy.oldy);
					}
				}
			}
			
			Display.update();
			Display.sync(60);
		}
		connection.close();
	}
	
	public void update(int delta) {
		
		
		
	}
}
