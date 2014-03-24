package explosionBoy.textHandler;

import org.newdawn.slick.Image;

public class Letter {

	private Image image;
	private char identifier;
	private int offset;
	
	public Letter(Image image, char c, int offset) {
		this.image = image;
		identifier = c;
		this.offset = offset;
	}
	
	public Image getImage(){
		return image;
	}
	
	public char getIdentifier(){
		return identifier;
	}
	
	public int getOffset(){
		return offset;
	}
}
