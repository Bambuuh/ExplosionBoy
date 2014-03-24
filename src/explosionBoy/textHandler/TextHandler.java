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
		int savedOffset = 0;

		char[] phrase = text.toLowerCase().toCharArray();

		for (char c : phrase) {
			savedOffset = findLetter(c).getImage().getWidth() - findLetter(c).getOffset();
			counter += savedOffset; 
			findLetter(c).getImage().draw(x+counter-savedOffset,y);
		}
		
//		savedOffset = 0;

	}

	public void generateLetters(){
		int counter = 0;
		for (Character c : identifier) {
			int offset = getOffsett(c);
			letterArray.add(new Letter(alphabet.getSubImage(counter*24, 0, 24, 24), c , offset));
			counter++;
		}

	}

	private int getOffsett(char c){
		int offset = 0;
		switch (c) {
		case 'm':
			offset=1;
			break;
		case 'w':
		case '/':
			offset=2;
			break;
		case 'o':
		case '8':
			offset=5;
			break;
		case '&':
			offset=4;
			break;
		case 'a':
		case 'n':
		case 'q':
		case 'x':
		case 'z':
		case '2':
		case '0':
			offset = 6;
			break;
		case 'd':
		case 'g':
		case 'h':
		case 'k':
		case 'p':
		case 'r':
		case 'u':
		case '9':
			offset = 7;
			break;
		case 'b':
		case 'c':
		case 's':
		case 'v':
		case 'y':
		case '3':
		case '4':
		case '6':
		case '?':
		case '!':
		case '-':
			offset=8;
			break;
		case 'e':
		case 'f':
		case 'j':
		case 't':
		case '5':
			offset=9;
			break;
		case '7':
			offset=10;
		case 'l':
			offset=11;
		case '1':
			offset=12;
			break;
		case 'i':
			offset=13;
			break;
		case '.':
			offset=15;
			break;

		default:
			break;
		}
		return offset;
	}

	private Letter findLetter(char c){

		for (Letter letter : letterArray) {
			if (c == letter.getIdentifier()) {
				return letter;
			}
		}
		return null;

	}

}
