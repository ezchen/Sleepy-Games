package ezchen.apcs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Runner extends Enemy {
	public Runner(){
		MAX_VELOCITY = 1f; /* edit */
		velocity = new Vector2(1f, 0);
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
		// TODO Auto-generated method stub
		
	}
}