package ezchen.apcs;

import com.badlogic.gdx.math.Vector2;

public class Floor {

	//width in tiles
	private int WIDTH;
	
	//height in tiles
	private int HEIGHT;
	
	private Tile[][] tiles;
	
	protected Vector2 position;
	
	public Floor(int width, int height, int yPos) {
		this.WIDTH = width;
		this.HEIGHT = height;
		
		tiles = new Tile[HEIGHT][WIDTH];
		position = new Vector2(0, yPos);
		generate();
	}
	
	public Floor(int width, int height, Vector2 position) {
		this.WIDTH = width;
		this.HEIGHT = height;
		
		tiles = new Tile[HEIGHT][WIDTH];
		this.position = position;
		generate();
	}
	
	public void generate() {
		for (int r = 0; r < tiles.length; r = r + 2) {
			for (int c = 0; c < tiles[r].length; c++) {
				tiles[r][c] = new Tile(true, new Vector2(position.x + c, position.y - r ));
			}
		}
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
}
