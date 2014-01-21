package ezchen.apcs;

import com.badlogic.gdx.math.Rectangle;

public abstract class Enemy extends Entity {
	protected float STD_VELOCITY = 0.1f; /* edit */
	protected int type;
	protected Player player;
	public Floor floor;
	public static Enemy makeEnemy(Floor f, Player p){
		Enemy e;
		int t = (int) (2*Math.random());
		e = (t == 1 ? new Shooter() : new Runner());
		e.type = t;
		e.DIMENSION.x = 16;
		e.DIMENSION.y = 16;
		e.facesRight = false;
		e.position.y = f.getTiles()[f.getHeight()-2][0].getPosition().y;
		e.floor = f;
		e.player = p;
		while (e.isProblem(.1f)) {
			e.position.x = (int)(1 + (Math.random() * f.getTiles()[0].length));
		}
		e.bounds = new Rectangle(e.position.x, e.position.y, e.DIMENSION.x/16f, e.DIMENSION.y/16f);
		return e;
	}
	public abstract void update(float deltaTime);
	
	public boolean seesPlayer(){
		float ebottom = position.y;
		float etop = position.y + 1;
		float pbottom = player.position.y;
		float ptop = player.position.y + player.getBounds().width;
		return (facesRight ^ (player.position.x - position.x > 0))
				&&((etop >= ptop && ebottom <= ptop)
				|| (etop <= ptop && ebottom >= pbottom)
				|| (etop >= pbottom && ebottom <= pbottom));
	}
	
	public boolean intersectX(float tx, float left, float right){
		float tleft = tx;
		float tright = tx + 1;
		return (right >= tright && left <= tright)
				|| (right <= tright && left >= tleft)
				|| (right >= tleft && left <= tleft);
	}
	
	public boolean isProblem(float deltaTime){
		Tile[] below = floor.getTiles()[floor.getHeight()-1];
		Tile[] at = floor.getTiles()[floor.getHeight()-2];
		float nextLeft = position.x + (velocity.x * deltaTime);
		float nextRight = nextLeft + 1;
		for(int i=0; i<below.length; i++){
			if((below[i] == null && intersectX(floor.getPosition().x + i, nextLeft, nextRight))
			|| (at[i] != null && intersectX(at[i].getPosition().x, nextLeft, nextRight))){
				return true;
			}
		}
		return false;		
	}
}
	
		
