package ezchen.apcs;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tile {

	// does this tile cause collisions
	private boolean BLOCKED = true;
	
	//position
	private Vector2 position;
	
	//bounds
	private Rectangle bounds = new Rectangle();
	
	//references to an index of textures in World
	private int tileNum = 0;
	
	//number of pixels in a tile
	private int dimensions = 16;
	
	//tile is 1 unit in the world
	private int size = 1;
	
	
	public Tile(boolean blocked, Vector2 position) {
		BLOCKED = blocked;
		this.position = position;
		bounds.set(position.x, position.y, 1, 1);
	}
	
	public int getTileNum() {
		return tileNum;
	}
	
	public int getSize() {
		return size;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
}
