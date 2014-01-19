package ezchen.apcs;

import ezchen.apcs.Entity.State;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Shooter extends Enemy {
	private Animation shooting;
	private float sinceShot;
	private float reloadTime = 1; /* edit */
	public Shooter(){
		/* velocity? */
	}
	public void updateS(float deltaTime){
		if(state != State.Shooting){ /* create Shooting State */
			if(!seesPlayer || sinceShot < reloadTime){
				/* move */
			}
			else {
				/* 
				 * change state to shooting 
				 * stop moving
				 */
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