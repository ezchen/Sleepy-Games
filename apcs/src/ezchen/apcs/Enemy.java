package ezchen.apcs;

import ezchen.apcs.Entity.State;

public class Enemy extends Entity {
	protected boolean seesPlayer;
	private int type;
	public static Enemy makeEnemy(int yPos){
		Enemy e = new Enemy();
		int t = (int) (2*Math.random());
		e = (t == 1 ? new Shooter() : new Runner());
		e.type = t;
		e.DIMENSION.x = 1;
		e.DIMENSION.y = 1;
		e.facesRight = false;
		e.seesPlayer = false;
		e.position.y = yPos;
		e.position.x = 0; /* edit */
		return e;
	}
	public void update(float deltaTime){
		//update seesPlayer
		if(type==1)
			updateS(deltaTime);
		else
			updateR(deltaTime);
		position.x += velocity.x + deltaTime;
		position.y += velocity.y + deltaTime;
	}
}
	
		
