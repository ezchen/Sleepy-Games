package ezchen.apcs;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tile {

	protected boolean BLOCKED = true;
	protected Vector2 position;
	protected Rectangle rectangle = new Rectangle();
	
	public Tile(boolean blocked) {
		BLOCKED = blocked;
	}
}
