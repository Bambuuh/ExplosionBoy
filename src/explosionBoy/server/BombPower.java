package explosionBoy.server;

public class BombPower extends LevelObject {

	public BombPower(int x, int y, boolean haveRectangle, boolean isBox) {
		super(x, y, haveRectangle, isBox);
		this.setBuff(true);
	}

}
