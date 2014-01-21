package ezchen.apcs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen extends InputAdapter implements Screen {

	//keep a reference to the game incase we want to switch screens
	//ex. PauseScreen, Menuscreen
	
	private Apcs game;
	
	private SpriteBatch spriteBatch;
	
	private World world;
	
	private WorldRenderer renderer;
	
	private OrthographicCamera camera;
	
	//Allows us to draw text
	private BitmapFont font;
	
	public GameScreen(Apcs game) {
		this.game = game;
		
		Resources.load();
		
		//defaults to arial
		font = new BitmapFont();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 18, 12);
		camera.position.x = camera.viewportWidth / 2;
		camera.update();
		
		world = new World((int)(camera.viewportWidth * 3/2f));
		
		renderer = new WorldRenderer(world);
		renderer.setView(camera);
		
		Gdx.input.setInputProcessor(this);
	}
	
	public void render(float delta) {
		//clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//updates the world based on time
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		//update the World
		//should update the entities and tiles
		world.update(deltaTime, camera);
		
		//Render the World
		//should handle the rendering of all entities and tiles
		renderer.setView(camera);
		renderer.render();
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	@Override
	public boolean keyDown(int keyCode) {
		switch(keyCode) {
		case(Input.Keys.P):
			//pause
			break;
		default:
			world.keyPressed(keyCode);
		}
		return true;
	}
	
	public boolean keyUp(int keyCode) {
		world.keyUp(keyCode);
		return true;
	}

}
