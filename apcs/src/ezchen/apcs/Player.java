package ezchen.apcs;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Entity {
	
	private float ACCELERATION = 25f;
	private float GRAVITY = .5f;
	private float MAX_FALLSPEED = .5f;
	private boolean upPressed = false;
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean pPressed = false;
	
	
	public Player() {
		
		bounds = new Rectangle(0, 6, .5f, .5f);
		
		position.x = 0;
		position.y = 6;
		
		DIMENSION.x = 1;
		DIMENSION.y = 1;
		
		MAX_VELOCITY = 100f;
		JUMP_VELOCITY = 15f;
		DAMPING = .90f;
		ACCELERATION = 2f;
		
		state = State.Standing;
		
		facesRight = false;
		
		grounded = false;
	}
	
	public void update(float deltaTime) {
		if (deltaTime == 0)
			return;
		
		updateVelocity(deltaTime);
		stateTime += deltaTime;
		
		//update position
		tryMove(deltaTime);
	}
	
	//handles collision detection, moves player
	public void tryMove(float deltaTime) {
		//allows movement based on time
		velocity.scl(deltaTime);
		
		position.x += velocity.x;
		if (!grounded) {
			position.y += velocity.y;
		}
		bounds.setPosition(position);
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
			velocity.x -= ACCELERATION;
			if (grounded)
				state = state.Walking;
			facesRight = false;
		}
		if (rightPressed) {
			velocity.x += ACCELERATION;
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
		if (!leftPressed && !rightPressed)
			velocity.x *= DAMPING;
		
		velocity.y -= GRAVITY;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	//used to update state
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
