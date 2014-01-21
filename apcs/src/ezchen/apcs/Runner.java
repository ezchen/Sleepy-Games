package ezchen.apcs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Runner extends Enemy {
	private TextureRegion texture = Resources.runnerFrames[0];
	public Runner(){
		MAX_VELOCITY = 1f; /* edit */
		velocity = new Vector2(1f, 0);
		DIMENSION.x = 16f;
		DIMENSION.y = 16f;
	}
	
	@Override
	public void update(float deltaTime){
		velocity.scl(deltaTime);
		if (isProblem(deltaTime)) {
			velocity.x *= -1;
		}
		
		position.x += velocity.x;
		bounds.x = position.x;
		velocity.scl(1/deltaTime);
	}

	@Override
	public void render(SpriteBatch batch) {
		
		if (facesRight) {
			batch.draw(texture, position.x, position.y, texture.getRegionWidth()/16f, texture.getRegionHeight()/16f);
		} else {
			batch.draw(texture, position.x + texture.getRegionWidth()/16f, position.y, -texture.getRegionWidth()/16f, texture.getRegionHeight()/16f);
		}
	}
}