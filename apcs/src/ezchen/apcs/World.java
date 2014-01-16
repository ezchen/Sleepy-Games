package ezchen.apcs;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class World {
	
	private Player player;
	private ArrayList<Floor> floors;
	private ArrayList<TextureRegion> blocks;
	private static Vector2 position;
	
	public World() {
		player = new Player();
		floors = new ArrayList<Floor>();
		create();
	}
	
	public void create() {
		floors.add(new Floor(15,4,10));
		floors.add(new Floor(15,4,6));
		//generate();
	}
	public void update(float deltaTime, OrthographicCamera camera) {
		/*
		if (camera.position.y < floors.get(1).position.y ) {
			floors.remove(0);
		}
		
		if (camera.position.y + camera.viewportHeight > floors.get(floors.size()-2).position.y ) {
			generate();
		}
		
		camera.position.y = player.position.y;
		camera.position.x = player.position.x;
		*/
		player.update(deltaTime);
	}

	//rendering
	public void render() {
	}
	
	//level generation
	public void addFloor() {
		position = floors.get(floors.size()-1).getPosition();
		position.y -= floors.get(floors.size()-1).getHeight();
		floors.add(new Floor(15, 4, position));
	}
	
	public ArrayList<Floor> getFloors() {
		return this.floors;
	}
	
	// handle input
	public void keyPressed(int keyCode) {
		player.keyPressed(keyCode);
	}
	
	public void keyUp(int keyCode) {
		player.keyUp(keyCode);
	}
}
	