package ezchen.apcs;

import com.badlogic.gdx.math.Vector2;

public class Floor {

	//width in tiles
	private int WIDTH;
	
	//height in tiles
	private int HEIGHT;
	
	//holds all the tile in the floor
	private Tile[][] tiles;
	
	// a vector with the position, x and y
	protected Vector2 position;
	
	/*
	 * create a floor with width * height tiles
	 * at position yPos
	 */
	public Floor(int width, int height, int yPos) {
		WIDTH = width;
		HEIGHT = height;
		
		tiles = new Tile[HEIGHT][WIDTH];
		position = new Vector2(0, yPos);
		generate();
	}
	/*
	 * create a floor with width * height tiles
	 * at position position
	 */
	public Floor(int width, int height, Vector2 position) {
		this.WIDTH = width;
		this.HEIGHT = height;
		
		tiles = new Tile[HEIGHT][WIDTH];
		this.position = position;
		generate();
	}
	
	/*
	 * makes the level
	 */
	public void generate() {
		makeWalls();
		makeFloor();
	}
	
	/*
	 * makes the walls for the floor
	 */
	private void makeWalls() {
		for (int r = 0; r < tiles.length; r++) {
			setTile(r, (int) position.x, new Tile(true, new Vector2(position.x, position.y - r), 1));
			
			int c = (int) position.x + tiles[r].length - 1;
			setTile(r, c, new Tile(true, new Vector2(position.x + c, position.y - r), 1));
		}
	}
	
	/*
	 * makes the floor for the floor
	 */
	private void makeFloor() {
		int r = tiles.length-1;
		for (int c = 0; c < tiles[r].length; c++) {
			tiles[r][c] = new Tile(true, new Vector2(position.x + c, position.y - r), 0);
		}
		
		int numHoles = (int) (1 + (2 * Math.random()));
		
		for (; numHoles > 0; numHoles--) {
			int holePosition = (int) (1 + ((WIDTH - 3) * Math.random()));
			setTile(tiles.length-1, holePosition, null);
			setTile(tiles.length-1, holePosition+1, null);
		}
	}
	
	private void setTile(int row, int column, Tile tile) {
		tiles[row][column] = tile;
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
