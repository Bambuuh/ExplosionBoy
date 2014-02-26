package explosionBoy.client;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;





import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.Texture;

public class Player {

	protected int x;
	protected int y;
	protected int speed;
	
	protected Animation playerAnimation;
	
	public Player(int x, int y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	public void setPlayerAnimation(){
		
	}
	
	public void move(int x, int y, float speed){
		
		this.y += (y*speed);
		this.x += (x*speed);
		
	}
	
	public void drawPlayer(Image image){
		
		
		image.bind();
		glBegin(GL_QUADS);
		
			glTexCoord2f(0, 1);
			glVertex2f(x, y);
			
			glTexCoord2f(1, 1);
			glVertex2f(x+image.getWidth(), y);
			
			glTexCoord2f(1, 0);
			glVertex2f(x+image.getWidth(), y+image.getHeight());
			
			glTexCoord2f(0, 0);
			glVertex2f(x, y+image.getHeight());
			
			glEnd();
			
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
}
