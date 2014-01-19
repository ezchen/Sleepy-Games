package ezchen.apcs;

import ezchen.apcs.Entity.State;

public class Runner extends Enemy {
	MAX_VELOCITY = 1; /* edit */
	private float ACCELERATION = 0.1f; /* edit */
	public Runner(){
		velocity = new Vector2(0, 0);
	}
	public void updateR(float deltaTime){
		if(state == State.Standing && seesPlayer){
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
}