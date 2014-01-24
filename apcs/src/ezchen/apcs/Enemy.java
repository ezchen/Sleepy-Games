package ezchen.apcs;

import com.badlogic.gdx.math.Rectangle;

public abstract class Enemy extends Entity {
	// Shooter or Runner
	protected int type;
	
	// variables from world
	protected Player player;
	protected World world;
	public Floor floor;
	
	// In lieu of constructor (must choose randomly between Shooter and Runner)
	public static Enemy makeEnemy(Floor f, Player p, World world){
		
		// initialization
		Enemy e;
		int t = (int) (2*Math.random());
		e = (t == 1 ? new Shooter() : new Runner());
		e.type = t;
		
		// instance variables
		e.DIMENSION.x = 14;
		e.DIMENSION.y = 14;
		e.facesRight = false;
		e.position.y = f.getTiles()[f.getHeight()-2][0].getPosition().y;
		e.floor = f;
		e.player = p;
		while (e.isProblem(.1f)) {
			e.position.x = (int)(1 + (Math.random() * f.getTiles()[0].length));
		}
		e.bounds = new Rectangle(e.position.x, e.position.y, e.DIMENSION.x/16f, e.DIMENSION.y/16f);
		e.world = world;
		return e;
	}
	
	public abstract void update(float deltaTime);
	
	// upon death
	public void destroy() {
		world.getEnemies().remove(this);
	}
	
	// Causes Shooters to shoot, Runners to run
	public boolean seesPlayer(){
		
		// bounds
		float ebottom = position.y;
		float etop = position.y + 1;
		float pbottom = player.position.y;
		float ptop = player.position.y + player.getBounds().width;
		
		// checks for intersection of bounds, and whether the enemy faces the player
		return (!(facesRight ^ (player.position.x > position.x)))
				&&((etop >= ptop && ebottom <= ptop)
				|| (etop <= ptop && ebottom >= pbottom)
				|| (etop >= pbottom && ebottom <= pbottom));
	}
	
	// Checks for collisions on one axis
	public boolean intersectX(float tx, float left, float right){
		
		// bounds
		float tleft = tx;
		float tright = tx + 1;
		
		// checks for intersection (3 types)
		return (right >= tright && left <= tright)
				|| (right <= tright && left >= tleft)
				|| (right >= tleft && left <= tleft);
	}
	
	// checks for collisions
	public boolean isProblem(float deltaTime){
		
		// Tile arrays for checking
		Tile[] below = floor.getTiles()[floor.getHeight()-1];
		Tile[] at = floor.getTiles()[floor.getHeight()-2];
		
		// future x-pos
		float nextLeft = position.x + (velocity.x * deltaTime);
		float nextRight = nextLeft + 1;
		
		// checks for horizontal collisions with walls, walking onto empty floors
		for(int i=0; i<below.length; i++){
			if((below[i] == null && intersectX(floor.getPosition().x + i, nextLeft, nextRight))
			|| (at[i] != null && intersectX(at[i].getPosition().x, nextLeft, nextRight))){
				return true;
			}
		}
		return false;		
	}
}
	
		
