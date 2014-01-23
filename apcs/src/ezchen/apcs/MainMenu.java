package ezchen.apcs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenu implements Screen {

	OrthographicCamera camera;
	SpriteBatch batch;
	Apcs game;
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
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		font.drawMultiLine(batch, "Press enter to continue", 100, 150);
		batch.end();
		
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