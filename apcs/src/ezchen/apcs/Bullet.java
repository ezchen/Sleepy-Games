package ezchen.apcs;

import com.badlogic.gdx.math.Vector2;

import ezchen.apcs.Entity.State;

public class Bullet extends Enemy {
	private float speed = 1; /* edit */
	public Bullet(int direction /*+1 or -1*/, int xPos, int yPos){
		position = new Vector2(xPos, yPos);
		velocity = new Vector2(speed*direction, 0);
	}
	public void update(float deltaTime){
		/* 
		 * move
		 * die if collision with player/wall and inflict damage on player if applicable 
		 */
	}
}