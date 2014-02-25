package explosionBoy.client;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

public abstract class Player {

	protected Controller con;
	protected int x;
	protected int y;
	protected int speed;
	
	private Texture texture;
	
	public Player(int x, int y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
}
