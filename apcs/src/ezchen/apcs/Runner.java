package ezchen.apcs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Runner extends Enemy {
	private TextureRegion texture = Resources.runnerFrames[0];
	private float WALK_SPEED = 3f;
	private float RUN_SPEED = 12f;
	private boolean isRunning = false;
	public Runner(){
		velocity = new Vector2(WALK_SPEED, 0);
		DIMENSION.x = 16f;
		DIMENSION.y = 16f;
	}
	
	@Override
	public void update(float deltaTime){
		if (!isRunning && seesPlayer()) {
			isRunning = true;
		}
		velocity.x = (isRunning? RUN_SPEED : WALK_SPEED) * (facesRight? 1 : -1);
		velocity.scl(deltaTime);
		if (isProblem(deltaTime)) {
			velocity.x *= -1;
			facesRight = !facesRight;
			isRunning = false;
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