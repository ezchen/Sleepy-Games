package ezchen.apcs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;

import ezchen.apcs.Entity.State;

public class Player extends Entity {
	
	private float ACCELERATION = 2f;
	private float GRAVITY = .5f;
	private float MAX_FALLSPEED = .5f;
	private boolean upPressed = false;
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean pPressed = false;
	
	
	public Player() {
		DIMENSION.x = 1;
		DIMENSION.y = 1;
		MAX_VELOCITY = 10f;
		JUMP_VELOCITY = 15f;
		DAMPING = .9f;
		state = State.Standing;
		facesRight = false;
		position.x = 0;
		position.y = 6;
		ACCELERATION = 2f;
		grounded = false;
	}
	
	public void update(float deltaTime) {
		if (deltaTime == 0)
			return;
		
		updateVelocity(deltaTime);
		stateTime += deltaTime;
		
		//update position
		position.x += velocity.x;
		if (!grounded) {
			position.y -= .01;
		}
	}
	
	//handles collision detection, moves player
	public void tryMove(float deltaTime) {
		//allows movement based on time
		velocity.scl(deltaTime);
		
		position.x += velocity.x;
		if (!grounded) {
			position.y += -.0001f;
		}
		velocity.scl(1/deltaTime);
	}
	
	public void updateVelocity(float deltaTime) {
		// jump
		if (upPressed) {
			// allows various heights when jumping
			if (velocity.y > JUMP_VELOCITY)
				velocity.y = JUMP_VELOCITY;
			else
				velocity.y += ACCELERATION;
			if (grounded) {
				state = state.Jumping;
				grounded = false;
			}
		}
		// walk
		if (leftPressed) {
			velocity.x -= ACCELERATION/10;
			if (grounded)
				state = state.Walking;
			facesRight = false;
		}
		if (rightPressed) {
			velocity.x += ACCELERATION/10;
			if (grounded)
				state = state.Walking;
			facesRight = true;
		}
		
		if(Math.abs(velocity.x) > MAX_VELOCITY)
			velocity.x = Math.signum(velocity.x) * MAX_VELOCITY;
		
		if(Math.abs(velocity.x) < 1 && grounded) {
			velocity.x = 0;
			state = state.Standing;
		}
		
		velocity.y -= GRAVITY;
	}
	
	//used to update state
	@Override
	public boolean keyPressed(int keyCode) {
		switch(keyCode) {
		case(Input.Keys.W):
			upPressed = true;
			break;
		case(Input.Keys.A):
			leftPressed = true;
			break;
		case(Input.Keys.D):
			rightPressed = true;
			break;
		case(Input.Keys.P):
			pPressed = true;
			break;
		}
		return true;
	}
	
	public boolean keyUp(int keyCode) {
		switch(keyCode) {
		case(Input.Keys.W):
			upPressed = false;
			break;
		case(Input.Keys.A):
			leftPressed = false;
			break;
		case(Input.Keys.D):
			rightPressed = false;
			break;
		case(Input.Keys.P):
			pPressed = false;
			break;
		}
		return true;
	}
}
