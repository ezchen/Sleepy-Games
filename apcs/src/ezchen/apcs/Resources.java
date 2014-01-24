package ezchen.apcs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Resources {

	/*
	 * a region of a texture. Holds an image basically.
	 * This is an array of these regions
	 */
	public static TextureRegion[][] tiles;
	
	public static TextureRegion[] walkFrames;
	public static TextureRegion[] standFrames;
	public static TextureRegion[] chopFrames;
	public static TextureRegion[] kickFrames;
	public static TextureRegion[] downFrames;
	
	public static TextureRegion[] runnerFrames;
	
	public static TextureRegion[] shooterWalkFrames;
	public static TextureRegion[] shooterShootFrames;
	
	public static TextureRegion[] bulletFrames;
	
	
	public static Sound newFloor;
	public static Sound chop;
	public static Sound shoot;
	
	public static void load() {
		/*
		 * texture atlas is a tool in libgdx to make the binding of images
		 * more efficient. Instead of loading each image separately, a texture atlas
		 * loads all the image and binds them all at once. We then hold a reference to all
		 * the textures we need so we only have to load each texture once at the beginning of the game.
		 */
		TextureAtlas atlas;
		
		atlas = new TextureAtlas("data/sprites.pack");
		
		Texture tileset = new Texture("data/tileset.png");
		
		tiles = TextureRegion.split(tileset, 16, 16);
		
		// Player animations
		standFrames = new TextureRegion[2];
		standFrames[0] = atlas.findRegion("PlayerR1");
		standFrames[1] = atlas.findRegion("PlayerR2");
		
		walkFrames = new TextureRegion[4];
		walkFrames[0] = atlas.findRegion("PlayerRWalking1");
		walkFrames[1] = atlas.findRegion("PlayerRWalking2");
		walkFrames[2] = atlas.findRegion("PlayerRWalking3");
		walkFrames[3] = atlas.findRegion("PlayerRWalking4");
		
		chopFrames = new TextureRegion[4];
		for (int i = 0; i < chopFrames.length; i++) {
			chopFrames[i] = atlas.findRegion("PlayerRChop" + (i + 1));
		}
		
		kickFrames = new TextureRegion[2];
		for (int i = 0; i < kickFrames.length; i++) {
			kickFrames[i] = atlas.findRegion("PlayerRKick" + (i + 1));
		}
		
		downFrames = new TextureRegion[2];
		for (int i = 0; i < downFrames.length; i++) {
			downFrames[i] = atlas.findRegion("PlayerRDown" + (i + 1));
		}
		
		// Runner
		runnerFrames = new TextureRegion[1];
		runnerFrames[0] = atlas.findRegion("RunnerR");
		
		// Shooter
		shooterWalkFrames = new TextureRegion[1];
		shooterWalkFrames[0] = atlas.findRegion("ShooterR");
		
		shooterShootFrames = new TextureRegion[4];
		for (int i = 0; i < shooterShootFrames.length; i++) {
			shooterShootFrames[i] = atlas.findRegion("ShootingR" + (i+1));
		}
		
		// Bullets
		bulletFrames = new TextureRegion[2];
		for (int i = 0; i < bulletFrames.length; i++) {
			bulletFrames[i] = atlas.findRegion("BulletR" + (i+1));
		}
		
		newFloor = Gdx.audio.newSound(Gdx.files.internal("data/sounds/chop.mp3"));
	}
}
