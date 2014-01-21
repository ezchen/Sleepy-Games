package ezchen.apcs;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Shooter extends Enemy {
	private Animation shooting;
	private float sinceShot = 0;
	private float reloadTime = 1; /* edit */
	public Shooter(){
		velocity = new Vector2(1f, 0); /* edit */
		stateTime = 0;
		state = State.Walking;
	}
	public void update(float deltaTime){
		velocity.scl(deltaTime);
		if (state == State.Shooting) {
			System.out.println("shoot");
		} else {
			if(seesPlayer() && sinceShot > reloadTime) {
				//shoot
				sinceShot = 0;
			}
			if(isProblem(deltaTime)) {
				velocity.x = -velocity.x;
				facesRight = !facesRight;
			}
		}
		
		position.x += velocity.x;
		bounds.x = position.x;
		
		velocity.scl(1/deltaTime);
		stateTime += deltaTime;
		sinceShot += deltaTime;
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}
		
}