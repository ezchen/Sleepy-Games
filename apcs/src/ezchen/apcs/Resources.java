package ezchen.apcs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Resources {
	//public static final Texture walkFrames;
	
	public static final Texture test = new Texture("data/testPicture.gif");
	
	public static final TextureRegion[] regions = TextureRegion.split(test, 16, 16)[0];
}
