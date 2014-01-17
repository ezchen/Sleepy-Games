package ezchen.apcs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class WorldRenderer {

	private World world;
	
	private float width;
	private float height;
	
	private float unitScaleX;
	private float unitScaleY;
	
	private SpriteBatch spriteBatch;
	
	private Rectangle viewBounds;
	
	private ShapeRenderer debugRenderer = new ShapeRenderer();
	
	public WorldRenderer(World world) {
		this.world = world;
		this.spriteBatch = new SpriteBatch();
		viewBounds = new Rectangle();
	}
	
	public void setView (OrthographicCamera camera) {
		spriteBatch.setProjectionMatrix(camera.combined);
		float width = camera.viewportWidth * camera.zoom;
		float height = camera.viewportHeight * camera.zoom;
		viewBounds.set(camera.position.x - width / 2, camera.position.y - height / 2, width, height);
		camera.update();
		debugRenderer.setProjectionMatrix(camera.combined);
	}

	public void render() {
		spriteBatch.begin();
		for (Floor floor : world.getFloors()) {
			for (Tile[] tArr : floor.getTiles()) {
				for (Tile t : tArr) {
					if (!(t==null)) {
						spriteBatch.draw(world.getBlockTextures()[t.getTileNum()], 
								t.getPosition().x, t.getPosition().y,
								t.getSize(), t.getSize());
					}
				}
			}
		}
		spriteBatch.end();
		debug();
	}
	
	public void debug() {
		debugRenderer.begin(ShapeType.Line);
		for (Floor floor : world.getFloors()) {
			for (Tile[] tArr : floor.getTiles()) {
				for (Tile t : tArr) {
					if (!(t==null)) {
						Rectangle rect = t.getBounds();
						float x1 = t.getPosition().x;
						float y1 = t.getPosition().y;
						debugRenderer.setColor(new Color(1, 0, 0, 1));
						debugRenderer.rect(x1,y1,rect.width,rect.height);
					}
				}
			}
		}
		debugRenderer.end();
	}
	
	public World getWorld() {
		return world;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public Rectangle getViewBounds() {
		return this.viewBounds;
	}

}
