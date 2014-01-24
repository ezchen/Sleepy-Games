package ezchen.apcs;

/*
 * Interface Screen allows us to work with libgdx. This screen is the mainmenu/dead screen itself.
 * 
 * create() - called once after this screen is created
 * render() - basically the while loop, is called repeatedly
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenu implements Screen {

	// camera class that sets the viewport and allows scrolling
	private OrthographicCamera camera;
	//SpriteBatch for drawing
	private SpriteBatch batch;
	//keep a reference so we can switch screens
	private Apcs game;
	//font
	private BitmapFont font;
	
	public MainMenu(Apcs game, OrthographicCamera camera) {
		this.game = game;
		batch = new SpriteBatch();
		this.camera = camera;
		camera.setToOrtho(false);
		font = new BitmapFont();
	}
	
	@Override
	public void render(float delta) {
		//clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		// sets spriteBatch matrices to cameras. this allows the spriteBatch to draw where the camera sees
		batch.setProjectionMatrix(camera.combined);
		// In openGl, it's more efficient to send a batch of sprites and then render it.
		// spritebatch handles this for us in begin() and end()
		batch.begin();
		// draws text
		font.drawMultiLine(batch, "Press enter to continue", 100, 150);
		batch.end();
		
		// brings us back to the game Screen if enter is pressed
		if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			game.setScreen(new GameScreen(game));
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		dispose();
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

}