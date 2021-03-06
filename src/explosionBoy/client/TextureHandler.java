package explosionBoy.client;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.newdawn.slick.opengl.Texture;

public class TextureHandler{
	
	//Draw a texture on the screen based on the x and y values it takes in.
	public void drawTexture(Texture tex, float x, float y){
		tex.bind();
		glBegin(GL_QUADS);
		
			glTexCoord2f(0, 1);
			glVertex2f(x, y);
			
			glTexCoord2f(1, 1);
			glVertex2f(x+tex.getTextureWidth(), y);
			
			glTexCoord2f(1, 0);
			glVertex2f(x+tex.getTextureWidth(), y+tex.getTextureHeight());
			
			glTexCoord2f(0, 0);
			glVertex2f(x, y+tex.getTextureHeight());
			
			glEnd();
	}
	
	//rotates and then draws a texture to the screen.
	public void drawRotatingTexture(Texture tex, float x, float y, float rotation){
		float xCenter = tex.getTextureWidth()/2;
		float yCenter = tex.getTextureHeight()/2;
		tex.bind();
		glPushMatrix();
		glTranslatef(x+xCenter,y+yCenter, 0);
		glRotatef(rotation, 0f, 0f, 1f);
		glTranslatef(-x-xCenter,-y-yCenter, 0);
		glBegin(GL_QUADS);
		
			glTexCoord2f(0, 1);
			glVertex2f(x, y);
			
			glTexCoord2f(1, 1);
			glVertex2f(x+tex.getTextureWidth(), y);
			
			glTexCoord2f(1, 0);
			glVertex2f(x+tex.getTextureWidth(), y+tex.getTextureHeight());
			
			glTexCoord2f(0, 0);
			glVertex2f(x, y+tex.getTextureHeight());
			
			glEnd();
			glPopMatrix();
	}
}
