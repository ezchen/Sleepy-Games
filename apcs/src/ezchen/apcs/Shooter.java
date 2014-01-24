package ezchen.apcs;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Shooter extends Enemy {
	
	private Animation shooting;
	private TextureRegion moving = Resources.shooterWalkFrames[0];
	private float sinceShot = 0;
	private float reloadTime = 5; /* edit */
	
	public Shooter(){
		shooting = new Animation(.4f, Resources.shooterShootFrames);
		shooting.setPlayMode(Animation.NORMAL);
		velocity = new Vector2(3f, 0); /* edit */
		facesRight = (velocity.x > 0);
		stateTime = 0;
		state = State.Walking;
	}
	
	public void update(float deltaTime){
		velocity.scl(deltaTime);
		if(seesPlayer() && sinceShot > reloadTime) {
			System.out.println("shoot" + (facesRight? "right" : "left"));
			int direction = (facesRight? 1 : -1);
			world.getBullets().add(new Bullet(-direction, position.x, position.y + .5f, world.getBullets().size(), world));
			sinceShot = 0;
			state = State.Shooting;
		}
		if(isProblem(deltaTime)) {
			velocity.x = -velocity.x;
			facesRight = !facesRight;
		}
		if (shooting.isAnimationFinished(stateTime)) {
			stateTime = 0;
			state = State.Walking;
		}
		
		position.x += velocity.x;
		bounds.x = position.x;
		
		velocity.scl(1/deltaTime);
		stateTime += deltaTime;
		sinceShot += deltaTime;
	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion frame = moving;
		
		if (state == State.Shooting) {
			frame = shooting.getKeyFrame(stateTime);
		} else {
			frame = moving;
		}
		
		if (frame != null) {
			if (facesRight) {
				batch.draw(frame, position.x, position.y, DIMENSION.x/16f, DIMENSION.y/16f);
			} else {
				batch.draw(frame, position.x + bounds.width, position.y, -DIMENSION.x/16f, DIMENSION.y/16f);
			}
		}
	}
	
}