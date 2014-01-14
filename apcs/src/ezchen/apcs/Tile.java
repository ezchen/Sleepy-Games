package ezchen.apcs;

import com.badlogic.gdx.math.Vector2;

public class Tile {

	protected boolean BLOCKED = true;
	protected Vector2 position;
	
	public Tile(boolean blocked) {
		BLOCKED = blocked;
	}
}
