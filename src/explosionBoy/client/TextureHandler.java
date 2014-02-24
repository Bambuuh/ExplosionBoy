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

import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class TextureHandler{
	
	
	private Texture number1tex;
	private Texture number2tex;
	private Texture number3tex;
	
	private Texture star1Tex;
	private Texture star2Tex;
	private Texture star3Tex;
	private Texture star4Tex;
	private Texture star5Tex;
	
	private Texture cowTex;
	private Texture starBuffTex;
	private Texture cookieTex;
	private Texture plusTex;
	private Texture asteroidTex;
	private Texture magnet;
	private Texture arrowKeys;
	private Texture shiftKey;
	
	private Texture optionsTex, startGameTex, highScoreTex, cowSexy, cowCool, dragonCow;
	
	
	public TextureHandler(){
		
		 number1tex = loadTexture("number1.png");
		 number2tex = loadTexture("number2.png");
		 number3tex = loadTexture("number3.png");
		
		 star1Tex = loadTexture("Star1.png");
		 star2Tex = loadTexture("Star2.png");
		 star3Tex = loadTexture("Star3.png");
		 star4Tex = loadTexture("Star4.png");
		 star5Tex = loadTexture("Star5.png");
		
		 cowTex = loadTexture("Moo.png");
		 cowSexy = loadTexture("MooSexy.png");
		 cowCool = loadTexture("MooCool.png");
		 dragonCow = loadTexture("MooDragonIsh.png");
		 
		 starBuffTex = loadTexture("StarBuff.png");
		 cookieTex = loadTexture("Cookie.png");
		 plusTex = loadTexture("plus.png");
		 asteroidTex = loadTexture("asteroid.png");
		 magnet = (loadTexture("magnet.png"));
		 
		 optionsTex = loadTexture("options.png");
		 startGameTex = loadTexture("startgame.png");
		 highScoreTex = loadTexture("highscore.png");
		 
		 arrowKeys = loadTexture("arrowkeys.png");
		 shiftKey = loadTexture("shift.png");
	}
	//Loads a image based on the Path and puts it on a texture.
	private Texture loadTexture(String file){
		Texture tex = null;
		try {
			InputStream path = getClass().getClassLoader().getResourceAsStream("images/"+file);
			tex = TextureLoader.getTexture("PNG", path);
		} catch (IOException e) {
			e.printStackTrace();		}
		return tex;
	}
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

	public Texture getStar1File() {
		return star1Tex;
	}

	public Texture getStar2File() {
		return star2Tex;
	}

	public Texture getStar3File() {
		return star3Tex;
	}

	public Texture getStar4File() {
		return star4Tex;
	}

	public Texture getStar5File() {
		return star5Tex;
	}

	public Texture getCowTex() {
		return cowTex;
	}

	public Texture getStarBuffTex() {
		return starBuffTex;
	}

	public Texture getCookieTex() {
		return cookieTex;
	}

	public Texture getNumber1tex() {
		return number1tex;
	}

	public Texture getNumber2tex() {
		return number2tex;
	}

	public Texture getNumber3tex() {
		return number3tex;
	}

	public Texture getPlusTex() {
		return plusTex;
	}

	public Texture getAsteroidTex() {
		return asteroidTex;
	}

	public Texture getMagnet() {
		return magnet;
	}
	public Texture getOptionsTex() {
		return optionsTex;
	}
	public Texture getStartGameTex() {
		return startGameTex;
	}
	public Texture getHighScoreTex() {
		return highScoreTex;
	}
	public Texture getArrowKeys() {
		return arrowKeys;
	}
	public Texture getShiftKey() {
		return shiftKey;
	}
	public void setCowTex(Texture tex){
		this.cowTex = tex;
	}
	public Texture getCowsad() {
		return cowSexy;
	}
	public void setCowsad(Texture cowsad) {
		this.cowSexy = cowsad;
	}
	public Texture getCowCool() {
		return cowCool;
	}
	public void setCowCool(Texture cowCool) {
		this.cowCool = cowCool;
	}
	public Texture getDragonCow() {
		return dragonCow;
	}
	public void setDragonCow(Texture dragonCow) {
		this.dragonCow = dragonCow;
	}
}
