package ezchen.apcs;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	//width and height of entity
	protected Vector2 DIMENSION = new Vector2();
	
	//Velocity
	protected float MAX_VELOCITY;
	protected float JUMP_VELOCITY;
	
	//Slows character down if key is not pressed
	protected float DAMPING;
	
	protected Rectangle Bounds;
	
	//Animations
	protected Animation standing;
	protected Animation walking;
	protected Animation dying;
	
	//Rectangle used for detecting collisions
	protected Rectangle bounds = new Rectangle();
	
	//if this entity hits tiles
	protected boolean collides = true;
	
	//is this entity on the floor
	protected boolean grounded = false;
	
	protected boolean facesRight = false;
	
	public Vector2 position = new Vector2();
	public Vector2 velocity = new Vector2();
	// used for updating animation frames
	protected float stateTime = 0;
	
	protected State state;
	
	public Rectangle getBounds(){
		return bounds;
	}
	
	protected enum State {
		Standing,
		Walking,
		Running,
		Jumping,
		Dying,
		Chopping,
		Kicking,
		Shooting
	}
}
