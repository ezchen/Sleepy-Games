package ezchen.apcs;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class World {
	

	private Player player;
	private ArrayList<Floor> floors;
	
	
	public World() {
		generate();
		player = new Player();
	}
	public void update(float deltaTime, OrthographicCamera camera) {
		if (camera.position.y < floors.get(1).position.y ) {
			floors.remove(0);
		}
		
		if (camera.position.y + camera.viewportHeight > floors.get(floors.size()-2).position.y ) {
			generate();
		}
		
	}

	public void render() {
	}
	
	public void generate() {
		
	}
	
}
