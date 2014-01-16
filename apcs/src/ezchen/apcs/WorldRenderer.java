package ezchen.apcs;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class WorldRenderer {

	private World world;
	
	private float unitScale;
	
	private SpriteBatch spriteBatch;
	
	private Rectangle viewBounds;
	
	public WorldRenderer(World world) {
		this.world = world;
	}
	
	public WorldRenderer(World world, SpriteBatch spriteBatch) {
		this(world);
		this.spriteBatch = spriteBatch;
	}
	
	public void setView (OrthographicCamera camera) {
		spriteBatch.setProjectionMatrix(camera.combined);
		float width = camera.viewportWidth * camera.zoom;
		float height = camera.viewportHeight * camera.zoom;
		viewBounds.set(camera.position.x - width / 2, camera.position.y - height / 2, width, height);
	}

	public void render() {
		for (Floor floor : world.getFloors()) {
			for (Tile[] tArr : floor.getTiles()) {
				for (Tile t : tArr) {
				//	spriteBatch.draw()
				}
			}
		}
	}
	
	public World getWorld() {
		return world;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public float getUnitScale() {
		return unitScale;
	}
	
	public Rectangle getViewBounds() {
		return this.viewBounds;
	}

}
