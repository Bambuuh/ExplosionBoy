package explosionBoy.textHandler;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import explosionBoy.client.AnimationHandler;

public class TextHandler {
	
	private ArrayList<Letter> letterArray;
	private char[] identifier;

	private Image alphabet;
	
	private AnimationHandler animation;
	
	public TextHandler(AnimationHandler animation) {
		this.animation = animation;
		alphabet = animation.getAlphabet();
		letterArray = new ArrayList<>();
		identifier = new char[]{'a','b','c','d','e','f','g','h','i','j',
								'k','l','m','n','o','p','q','r','s','t',
								'u','v','w','x','y','z',
								'1','2','3','4','5','6','7','8','9','0',
								'.','?','!','/','&','-'};
		generateLetters();
	}
	
	public void drawText(String text, int x, int y){
		
		int counter = 0;
		
		char[] phrase = text.toLowerCase().toCharArray();
		
		for (char c : phrase) {
			findTile(c).draw(x*counter, y);
			counter += 24;
		}
		
		
	}
	
	public void generateLetters(){
		int counter = 0;
		for (Character c : identifier) {
			letterArray.add(new Letter(alphabet.getSubImage(counter*24, 0, 24, 24), c ));
			counter++;
		}
		
	}
	
	private Image findTile(char c){
		
		for (Letter letter : letterArray) {
			if (c == letter.getIdentifier()) {
				return letter.getImage();
			}
		}
		return null;
		
	}
	
}
