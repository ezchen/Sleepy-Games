package ezchen.apcs;

import com.badlogic.gdx.math.Vector2;

public class Runner extends Enemy {
	private float ACCELERATION = 0.1f; /* edit */
	public Runner(){
		MAX_VELOCITY = 1f; /* edit */
		velocity = new Vector2(0, 0);
	}
	public void updateR(float deltaTime){
		if(state == State.Standing && super.seesPlayer()){
			state = State.Walking;
		}
		else {
			/*
			 * if collision with wall, stop 
			 * 
			 */
			velocity.x += ACCELERATION*deltaTime;
		}
	}
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
}