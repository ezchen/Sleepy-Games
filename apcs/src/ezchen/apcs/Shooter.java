package ezchen.apcs;

/*
 * class will move around and shoot if it sees the player
 */
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Shooter extends Enemy {
	
	// for animation
	private Animation shooting;
	private TextureRegion moving = Resources.shooterWalkFrames[0];
	
	// keeping track of shots
	private float sinceShot = 0;
	private float reloadTime = 3;
	
	// constructor
	public Shooter(){
		shooting = new Animation(.2f, Resources.shooterShootFrames);
		shooting.setPlayMode(Animation.NORMAL);
		velocity = new Vector2(3f, 0); /* edit */
		facesRight = (velocity.x > 0);
		stateTime = 0;
		state = State.Walking;
	}
	
	// updates velocity
	public void update(float deltaTime){
		
		// velocity becomes change in position
		velocity.scl(deltaTime);
		
		// if shooting is necessary and possible
		if(!seesPlayer() && sinceShot > reloadTime) {
			System.out.println("shoot" + (facesRight? "right" : "left"));
			int direction = (facesRight? 1 : -1);
			
			// shoots by creating Bullet
			world.getBullets().add(new Bullet(-direction, position.x, position.y + .5f, world));
			sinceShot = 0;
			state = State.Shooting;
		}
		
		// collision -> switch directions
		if(isProblem(deltaTime)) {
			velocity.x = -velocity.x;
			facesRight = !facesRight;
		}
		
		// if not shooting, walk
		if (shooting.isAnimationFinished(stateTime)) {
			stateTime = 0;
			state = State.Walking;
		}
		
		// increment position
		position.x += velocity.x;
		bounds.x = position.x;
		
		velocity.scl(1/deltaTime);
		stateTime += deltaTime;
		sinceShot += deltaTime;
	}

	// draw the Shooter
	@Override
	public void render(SpriteBatch batch) {
		TextureRegion frame = moving;
		
		if (state == State.Shooting) {
			frame = shooting.getKeyFrame(stateTime);
		} else {
			frame = moving;
		}
		System.out.println(frame == null);
		
		if (frame != null) {
			if (!facesRight) {
				batch.draw(frame, position.x, position.y, DIMENSION.x/16f, DIMENSION.y/16f);
			} else {
				batch.draw(frame, position.x + bounds.width, position.y, -DIMENSION.x/16f, DIMENSION.y/16f);
			}
		}
	}
	
}