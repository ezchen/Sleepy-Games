package ezchen.apcs;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class World {
	
	private int WIDTH;
	
	private Player player;
	private ArrayList<Floor> floors;
	private ArrayList<Enemy> enemies;
	private TextureRegion[] blockTextures = Resources.regions;
	
	public World(int width) {
		WIDTH = width;
		player = new Player(this);
		player.position.x = 5;
		player.position.y = -5;
		floors = new ArrayList<Floor>();
		create();
	}
	
	public void create() {
		floors.add(new Floor(WIDTH,6,10));
		floors.add(new Floor(WIDTH,6,4));
		floors.add(new Floor(WIDTH,6,-2));
		floors.add(new Floor(WIDTH,6,-8));
	}
	
	public void update(float deltaTime, OrthographicCamera camera) {
		if (camera.position.y + camera.viewportHeight/2 < floors.get(2).position.y ) {
			floors.remove(0);
			System.out.println("deleting");
		}
		
		if (camera.position.y - camera.viewportHeight/2 < floors.get(floors.size()-2).position.y ) {
			addFloor();
			System.out.println("adding floor");
		}
		
		player.update(deltaTime);
		//for(Enemy e : enemies)
		//	e.update(deltaTime);
		camera.position.y = player.position.y;
		
		if (!(player.position.x - camera.viewportWidth/2 <= 0 || player.position.x + camera.viewportWidth/2 >= WIDTH))
				camera.position.x = player.position.x;
		camera.update();
	}
	
	public Floor findFloor(Player player) {
		int i = 0;
		while (player.position.y < floors.get(i).getPosition().y)
			i++;
		return floors.get(i-1);
	}
	
	public Floor findPreviousFloor(Player player) {
		int i = 0;
		while (player.position.y < floors.get(i).getPosition().y)
			i++;
		return floors.get(i-2);
	}

	//rendering
	public void render() {
	}
	
	//level generation
	public void addFloor() {
		int yPos = (int) (floors.get(floors.size()-1).getPosition().y - floors.get(floors.size()-1).getHeight());
		floors.add(new Floor(WIDTH, 6, yPos));
		//enemies.add(Enemy.makeEnemy(floors.get(floors.size()-1), player));
	}
	
	//getters
	public ArrayList<Floor> getFloors() {
		return this.floors;
	}
	
	public ArrayList<Enemy> getEnemies() {
		return this.enemies;
	}
	
	public TextureRegion[] getBlockTextures() {
		return this.blockTextures;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	// handle input
	public void keyPressed(int keyCode) {
		player.keyPressed(keyCode);
	}
	
	public void keyUp(int keyCode) {
		player.keyUp(keyCode);
	}
	
	public void createDemoWorld() {
		floors.add(new Floor(15,4,10));
		floors.add(new Floor(15,4,6));
	}
}
	