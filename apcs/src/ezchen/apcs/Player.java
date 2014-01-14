package ezchen.apcs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Player extends Entity {
	
	public Player() {
		DIMENSION.x = 1;
		DIMENSION.y = 1;
		MAX_VELOCITY = 10f;
		JUMP_VELOCITY = 15f;
		DAMPING = .9f;
		state = State.Standing;
		facesRight = false;
		position.x = 20;
		position.y = 5;
	}
	
	public void update(float deltaTime) {
		if (deltaTime == 0)
			return;
		
		stateTime += deltaTime;
		
	}
}
