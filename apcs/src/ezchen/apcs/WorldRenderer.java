package ezchen.apcs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class WorldRenderer {

	private World world;
	
	private Player player;
	
	private float width;
	private float height;
	
	private float unitScaleX;
	private float unitScaleY;
	
	private SpriteBatch spriteBatch;
	
	private Rectangle viewBounds;
	
	private ShapeRenderer debugRenderer = new ShapeRenderer();
	
	public WorldRenderer(World world) {
		this.world = world;
		player = world.getPlayer();
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
						spriteBatch.draw(world.getBlockTextures()[0][0], 
								t.getPosition().x, t.getPosition().y,
								t.getSize(), t.getSize());
					}
				}
			}
		}
		player.render(spriteBatch);
		for (int i = 0; i < world.getEnemies().size(); i++) {
			world.getEnemies().get(i).render(spriteBatch);
		}
		for (int i = 0; i < world.getBullets().size(); i++) {
			world.getBullets().get(i).render(spriteBatch);
		}
		spriteBatch.end();
		//debug();
	}
	
	public void renderPlayer() {
	}
	
	public void renderEntities() {
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
		Rectangle rect = player.getBounds();
		debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		for (Enemy e : world.getEnemies()) {
			rect = e.getBounds();
			if (e instanceof Runner) {
				debugRenderer.setColor(new Color(Color.BLUE));
			} else {
				debugRenderer.setColor(new Color(Color.CYAN));
			}
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		for (Enemy e : world.getBullets()) {
			rect = e.getBounds();
			debugRenderer.setColor(new Color(Color.BLUE));
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
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
