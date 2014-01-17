package ezchen.apcs;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class World {
	
	private Player player;
	private ArrayList<Floor> floors;
	private TextureRegion[] blockTextures = Resources.regions;
	private static Vector2 position;
	
	public World() {
		player = new Player();
		floors = new ArrayList<Floor>();
		create();
	}
	
	public void create() {
		floors.add(new Floor(15,4,10));
		floors.add(new Floor(15,4,6));
		floors.add(new Floor(15,4,2));
		floors.add(new Floor(15,4,-2));
		floors.add(new Floor(15,4,-6));
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
		camera.position.y = player.position.y;
		camera.position.x = player.position.x;
		camera.update();
	}

	//rendering
	public void render() {
	}
	
	//level generation
	public void addFloor() {
		int yPos = (int) (floors.get(floors.size()-1).getPosition().y - floors.get(floors.size()-1).getHeight());
		floors.add(new Floor(15, 4, yPos));
	}
	
	//getters
	public ArrayList<Floor> getFloors() {
		return this.floors;
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
	