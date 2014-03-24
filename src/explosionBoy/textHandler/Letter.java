package explosionBoy.textHandler;

import org.newdawn.slick.Image;

public class Letter {

	private Image image;
	private char identifier;
	
	public Letter(Image image, char c) {
		this.image = image;
		identifier = c;
	}
	
	public Image getImage(){
		return image;
	}
	
	public char getIdentifier(){
		return identifier;
	}
}
