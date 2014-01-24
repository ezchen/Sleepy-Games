package ezchen.apcs;

/*
 * Interface Screen allows us to work with libgdx. This screen is the gameplay itself.
 * 
 * create() - called once after this screen is created
 * render() - basically the while loop, is called repeatedly
 */

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
	//game is the entry point into the game
	private Apcs game;
	//used to draw textures onto the screen
	private SpriteBatch spriteBatch;
	//holds all the tiles, enemies and player
	private World world;
	//Renders everything to the screen
	private WorldRenderer renderer;
	
	/*
	 * used primarily for scrolling, but also converts pixels into world units.
	 * We use 16x16 tiles
	 */
	private OrthographicCamera camera;
	
	//Allows us to draw text
	private BitmapFont font;
	
	public GameScreen(Apcs game) {
		//reference to entry point
		this.game = game;
		
		//load all the resources
		Resources.load();
		
		//defaults to arial
		font = new BitmapFont();
		
		camera = new OrthographicCamera();
		
		//set the camera to orthographic projection(we're using 2d)
		//set y to be increasing upwards, and the width of the camera to 18
		//the height of the camera to 12
		camera.setToOrtho(false, 18, 12);
		
		//set the camera in the middle of the world initially
		//viewportWidth is the width of the camera
		//origin of the camera is the center while everything else in libgdx
		//is bottom right corner
		camera.position.x = camera.viewportWidth / 2;
		
		//used after changing any camera value to update the camera
		camera.update();
		
		//create a new world, width is the camera but slightly larger (3/2f)
		//keep a reference to the game
		world = new World((int)(camera.viewportWidth * 3/2f), game);
		
		// new spriteBatch used for drawing
		spriteBatch = new SpriteBatch();
		
		// create renderer
		renderer = new WorldRenderer(world);
		// sets the renderer's viewport to the camera's viewport
		// also holds a reference to the camera for rendering
		renderer.setView(camera);
		
		//allows us to the key input and touch screen on android
		//an inputProcessor must implement the interface inputProcessor
		Gdx.input.setInputProcessor(this);
	}
	
	public void render(float delta) {
		//clear the screen to black
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
	
	/*
	 * passes the input down. KeyDown and KeyUp is needed
	 * because inputProcessor detects it once when u press it and once
	 * when you stop pressing it rather than constantly
	 */
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
