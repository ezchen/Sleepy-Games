package ezchen.apcs;

import java.util.ArrayList;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Entity {
	
	private World world;
	
	private Rectangle testBounds = new Rectangle();
	
	//Velocity/Acceleration
	private float ACCELERATION = 25f;
	private float GRAVITY = 2f;
	private float MAX_FALLSPEED = 5f;
	
	//keys
	private boolean upPressed = false;
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean kPressed = false;
	
	private Animation chopping;
	private Animation down;
	private Animation kicking;
	private Animation jumping;
	
	private Rectangle attackBounds = new Rectangle();
	
	private enum AttackState {
		Chopping(1f),
		Kicking(1f),
		Shooting(1f);
		
		private float attackTime;
		
		AttackState(float attackTime) {
			this.attackTime = attackTime;
		}
		
		public float getAttackTime() {
			return this.attackTime;
		}
	}
	
	AttackState attackState;
	
	private boolean canDoubleJump = false;
	ArrayList<Tile> a = new ArrayList<Tile>();
	ArrayList<Enemy> enemies;
	
	public Player(World world) {
		this.world = world;
		this.enemies = world.getEnemies();
		
		//number of pixels in each direction
		DIMENSION.x = 16;
		DIMENSION.y = 21;
		
		bounds = new Rectangle(0, 6, 10/16f, 19/16f);
		
		position.x = 0;
		position.y = 6;
		
		MAX_VELOCITY = 7f;
		JUMP_VELOCITY = 25f;
		DAMPING = .90f;
		ACCELERATION = 10f;
		
		state = State.Standing;
		
		facesRight = false;
		
		grounded = false;
		
		standing = new Animation(.4f, Resources.standFrames);
		walking = new Animation(.15f, Resources.walkFrames);
		chopping = new Animation(.1f, Resources.chopFrames);
		kicking = new Animation(.15f, Resources.kickFrames);
		down = new Animation(.15f, Resources.downFrames);
		jumping = new Animation(.15f, Resources.standFrames[0]);
		standing.setPlayMode(Animation.LOOP);
		walking.setPlayMode(Animation.LOOP);
		chopping.setPlayMode(Animation.NORMAL);
		
	}
	
	public void update(float deltaTime) {
		if (deltaTime == 0)
			return;
		
		updateVelocity(deltaTime);
		stateTime += deltaTime;
		
		//update position
		tryMove(deltaTime);
	}
	
	//handles collision detection, moves player
	public void tryMove(float deltaTime) {
		a.clear();
		
		Floor floor = world.findPreviousFloor(this);
		
		velocity.scl(deltaTime);
		
		//top collisions
		testBounds.set(bounds);
		int row = 5;
		int column = (int) (Math.floor(position.x));
		int column2 = (int) (Math.floor(position.x + bounds.width));
		a.add(floor.getTiles()[row][column]);
		if (column != column2 && column2 <= 26) {
			a.add(floor.getTiles()[row][column2]);
		}
	
		for (Tile t : a) {
			if (t != null) {
				if (testBounds.overlaps(t.getBounds())) {
					velocity.y = 0;
					position.y = t.getBounds().y - bounds.height - .01f;
					break;
				}
			}
		}
		
		a.clear();
		
		//horizontal collisions
		floor = world.findFloor(this);
		int c, r1, r2;
		
		if (velocity.x > 0) {
			c = (int)(Math.floor(position.x + bounds.width + velocity.x));
		} else {
			c = (int)(Math.floor(position.x + velocity.x));
		}
		
		r2 = (int)((floor.position.y - Math.floor(position.y)) % 6)+1;
		r1 = (int)(((floor.position.y - Math.floor(position.y + bounds.height))) % 6) + 1;
		if (r1 < 6)
			a.add(floor.getTiles()[r1][c]);
		if (r2 < 6)
			a.add(floor.getTiles()[r2][c]);
		else
			a.add(floor.getTiles()[5][c]);
		testBounds.set(bounds);
		testBounds.x += velocity.x;
		for (Tile t : a) {
			if (t != null) {
				if (testBounds.overlaps(t.getBounds())) {
					if (velocity.x >= 0)
						position.x = t.getPosition().x - bounds.width - .01f;
					else
						position.x = t.getPosition().x + t.getBounds().width + .01f;
					velocity.x = 0;
					break;
				}
			}
		}
		position.x += velocity.x;
		bounds.x = position.x;
		testBounds.set(bounds);
		
		//bottom collisions
		a = collisionTilesY(floor);
		testBounds.y += velocity.y;
		grounded = false;
		for (Tile t : a) {
			if (t != null) {
				if (testBounds.overlaps(t.getBounds())) {
					grounded = true;
					canDoubleJump = true;
					position.y = t.getBounds().y + t.getBounds().height;
					velocity.y = 0;
				}
			}
		}
		position.y += velocity.y;
		bounds.y = position.y;
		checkEnemyCollisions(world.getEnemies());
		checkBulletCollisions(world.getBullets());
		velocity.scl(1/deltaTime);
	}
	
	public void checkEnemyCollisions(ArrayList<Enemy> enemies) {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			if (attackState != null) {
				if (attackState == AttackState.Chopping) {
					float x = (facesRight) ? position.x + (DIMENSION.x/16f)/2 : position.x - (DIMENSION.x/16f)/2;
					attackBounds.set(x, position.y, 1f, 1f);
					if (e.getBounds().overlaps(attackBounds)) {
						e.destroy();
						world.setScore(world.getScore() + 50);
					}
				}
			} else if (e.getBounds().overlaps(bounds)) {
				state = State.Dying;
				world.getGame().setScreen(new MainMenu(world.getGame(), new OrthographicCamera()));
			}
		}
	}
	
	public void checkBulletCollisions(ArrayList<Enemy> bullets) {
		for (int i = 0; i < bullets.size(); i++) {
			Bullet e = (Bullet)bullets.get(i);
			if (e.getBounds().overlaps(bounds)) {
				state = State.Dying;
				e.destroy();
				world.getGame().setScreen(new MainMenu(world.getGame(), new OrthographicCamera()));
			}
		}
	}
	
	public void updateVelocity(float deltaTime) {
		// jump
		if (upPressed) {
			// allows various heights when jumping
			if (grounded) {
				velocity.y = JUMP_VELOCITY;
				state = State.Jumping;
				grounded = false;
				canDoubleJump = true;
			} else {
				if (canDoubleJump && velocity.y < 0) {
					velocity.y = JUMP_VELOCITY;
					canDoubleJump = false;
				}
			}
		}
		// walk
		if (leftPressed && rightPressed || !leftPressed && !rightPressed)
			velocity.x *= DAMPING;
		else {
			if (leftPressed) {
				velocity.x -= ACCELERATION;
				if (grounded)
					state = State.Walking;
				facesRight = false;
			}
			if (rightPressed) {
				velocity.x += ACCELERATION;
				if (grounded)
					state = State.Walking;
				facesRight = true;
			}
		}
		if (kPressed) {
			attackState = AttackState.Chopping;
		}
		
		if(Math.abs(velocity.x) > MAX_VELOCITY)
			velocity.x = Math.signum(velocity.x) * MAX_VELOCITY;
		
		if(Math.abs(velocity.x) < 1 && grounded) {
			velocity.x = 0;
			state = State.Standing;
		}
		
		if (!grounded)
			velocity.y -= GRAVITY;
		

	}
	
	private ArrayList<Tile> collisionTilesY(Floor floor) {
		ArrayList<Tile> a = new ArrayList<Tile>();
		
		//tiles underneath the player
		if (velocity.y <= 0) {
			int row = (int) (Math.abs((floor.position.y - Math.floor(position.y)) + 1));
			int column = (int) (Math.floor(position.x));
			int column2 = (int) (Math.floor(position.x + bounds.width));
			if (row < 6) {
				a.add(floor.getTiles()[row][column]);
				if (column != column2 && column2 <= 26) {
					a.add(floor.getTiles()[row][column2]);
				}	
			}
		}
		return a;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	//used to update state
	public boolean keyPressed(int keyCode) {
		switch(keyCode) {
		case(Input.Keys.W):
			upPressed = true;
			grounded = false;
			break;
		case(Input.Keys.A):
			leftPressed = true;
			break;
		case(Input.Keys.D):
			rightPressed = true;
			break;
		case(Input.Keys.K):
			kPressed = true;
			break;
		}
		return true;
	}
	
	public boolean keyUp(int keyCode) {
		switch(keyCode) {
		case(Input.Keys.W):
			upPressed = false;
			break;
		case(Input.Keys.A):
			leftPressed = false;
			break;
		case(Input.Keys.D):
			rightPressed = false;
			break;
		case(Input.Keys.K):
			kPressed = false;
			break;
		}
		return true;
	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion frame = null;
		if (attackState == AttackState.Chopping) {
			if (!chopping.isAnimationFinished(stateTime)) {
				frame = chopping.getKeyFrame(stateTime);
			} else {
				attackState = null;
				stateTime = 0;
			}
		} else if (state == State.Standing) {
			frame = standing.getKeyFrame(stateTime);
		} else if (state == State.Walking) {
			frame = walking.getKeyFrame(stateTime);
		} else if (state == State.Jumping) {
			frame = jumping.getKeyFrame(stateTime);
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
