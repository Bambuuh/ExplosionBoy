package explosionBoy.server;

import java.util.ArrayList;

public class GameHolder {

	private int gameID;
	private ArrayList<ConnectionReference> references;

	public GameHolder() {
		this.gameID = 1;
		references = new ArrayList<>();
		references.add(new ConnectionReference(1));
		references.add(new ConnectionReference(2));
	}
	
	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public ArrayList<ConnectionReference> getReferences() {
		return references;
	}

	public void setReferences(ArrayList<ConnectionReference> references) {
		this.references = references;
	}
	
}
