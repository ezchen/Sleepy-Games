package ezchen.apcs;

import ezchen.apcs.Entity.State;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Shooter extends Enemy {
	private Animation shooting;
	private float sinceShot;
	private float reloadTime = 1; /* edit */
	public Shooter(){
		velocity.x = STD_VELOCITY; /* edit */
	}
	public void update(float deltaTime){
		if(state != State.Shooting){ /* create Shooting State */
			if(!seesPlayer() || sinceShot < reloadTime){
				sinceShot += deltaTime;
				if(isProblem(deltaTime)){
					velocity.x = -velocity.x;
					facesRight = !facesRight;
				} else {
					position.x += velocity.x * deltaTime;
				}
			}
			else {
				state = State.Shooting;
			}
		}
		else {
			/*
			 * run through shooting animations
			 * if last animation (or equivalently if sinceShot is a certain size), make bullet
			 */
		}
	}
		
}