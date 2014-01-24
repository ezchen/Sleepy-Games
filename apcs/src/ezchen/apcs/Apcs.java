package ezchen.apcs;

/*
 * class is the entry point to the game. This is called in the respective
 * Main.java in the desktop version and MainActivity.java in the android version.
 */

import com.badlogic.gdx.Game;


public class Apcs extends Game {
	
	@Override
	public void create() {		
		setScreen(new GameScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
