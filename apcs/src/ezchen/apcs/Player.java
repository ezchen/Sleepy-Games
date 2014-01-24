package ezchen.apcs;

import java.util.ArrayList;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Entity {
	
	/*keep a reference to world so we can access the floors for collision
	 * detection
	 */
	private World world;
	
	/*
	 * Rectangle class, similar to java2d rectangle. We will use it
	 * to check for collisions
	 */
	private Rectangle testBounds = new Rectangle();
	
	//Velocity/Acceleration
	private float ACCELERATION = 25f;
	private float GRAVITY = 2f;
	private float MAX_FALLSPEED = 5f;
	private float KICK_SPEED = 10f;
	private float MAX_KICKTIME = 2f;
	
	//keys
	/*
	 * tells us what keys are pressed, logic will be handled in
	 * trymove()
	 */
	private boolean upPressed = false;
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean kPressed = false;
	private boolean lPressed = false;
	
	/*
	 * animation classes, holds an array of textures and will retrieve the textures later using
	 * getKeyFrame(float)
	 */
	private Animation chopping;
	private Animation down;
	private Animation kicking;
	private Animation jumping;
	
	/*
	 * used when attacking. Will be used for collision detection
	 */
	private Rectangle attackBounds = new Rectangle();
	
	private boolean isKicking = false;
	private float sinceKick = 0;
	
	private boolean canChop = true;
	private float chopTime = 0;
	
	/*
	 * so we know what texture to render
	 */
	private enum AttackState {
		Chopping,
		Kicking;
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
		chopping = new Animation(.2f, Resources.chopFrames);
		kicking = new Animation(.15f, Resources.kickFrames);
		down = new Animation(.15f, Resources.downFrames);
		jumping = new Animation(.15f, Resources.standFrames[0]);
		
		/*
		 * sets the play mode of the animations.
		 * Loop will get the key frame no matter what the input is, while
		 * NORMAL the input must be in the range [0, animation Time * number of frames]
		 */
		standing.setPlayMode(Animation.LOOP);
		walking.setPlayMode(Animation.LOOP);
		chopping.setPlayMode(Animation.NORMAL);
		
	}
	
	public void update(float deltaTime) {
		if (deltaTime == 0)
			return;
		
		//update the velocity
		updateVelocity(deltaTime);
		//update the stateTime (for animation)
		stateTime += deltaTime;
		
		//update position
		tryMove(deltaTime);
	}
	
	//handles collision detection, moves player
	public void tryMove(float deltaTime) {
		// remove all the tiles from the last tryMove()
		a.clear();
		
		// if there is top collision, than we must access the previous floor
		Floor floor = world.findPreviousFloor(this);
		
		// allows us to base movement on time, not the fps of the computer
		velocity.scl(deltaTime);
		
		//top collisions
		testBounds.set(bounds);
		//row has to be 5
		int row = floor.getHeight() - 1;
		
		//the two columns that the rectangle can possibly hit
		int column = (int) (Math.floor(position.x));
		int column2 = (int) (Math.floor(position.x + bounds.width));
		
		//add the tiles to the arraylist for collision detection
		a.add(floor.getTiles()[row][column]);
		
		//if the columns are not equal and the second column is not outside of the game world
		if (column != column2 && column2 <= 26) {
			a.add(floor.getTiles()[row][column2]);
		}
	
		// for every tile, test if the bounds cross
		for (Tile t : a) {
			if (t != null) {
				// returns true if rectangles overlap
				if (testBounds.overlaps(t.getBounds())) {
					velocity.y = 0;
					//set position to slightly under tile
					//you must subtract the height of the player(bounds.height) because rectangles
					//position are at the bottom left corner
					position.y = t.getBounds().y - bounds.height - .01f;
					break;
				}
			}
		}
		
		//remove the tiles for next set of collisions
		a.clear();
		
		//horizontal collisions
		floor = world.findFloor(this);
		
		// initialize variables
		int c, r1, r2;
		
		// if velocity.x > 0 check the right, otherwise check to the left
		if (velocity.x > 0) {
			c = (int)(Math.floor(position.x + bounds.width + velocity.x));
		} else {
			c = (int)(Math.floor(position.x + velocity.x));
		}
		
		//find the relative row by using floor.position.y
		r2 = (int)((floor.position.y - Math.floor(position.y)) % 6)+1;
		r1 = (int)(((floor.position.y - Math.floor(position.y + bounds.height))) % 6) + 1;
		
		// if the position is greater than 6 (when players position is a multiple of 6)
		// get the bottom row, otherwise use the rows
		if (r1 < 6)
			a.add(floor.getTiles()[r1][c]);
		if (r2 < 6)
			a.add(floor.getTiles()[r2][c]);
		else
			a.add(floor.getTiles()[5][c]);
		
		// set the bounds to the current bounds on player
		testBounds.set(bounds);
		// move the test bounds first before updating the player
		testBounds.x += velocity.x;
		
		// check collisions
		for (Tile t : a) {
			if (t != null) {
				// returns true if rectangles overlap between testbounds and tiles
				if (testBounds.overlaps(t.getBounds())) {
					// if velocity is greater than 0, put the player to the left of the tile
					// otherwise put him to the right of the tile
					if (velocity.x >= 0)
						position.x = t.getPosition().x - bounds.width - .01f;
					else
						position.x = t.getPosition().x + t.getBounds().width + .01f;
					//set the velocity to 0 since he hit a wall
					velocity.x = 0;
					// no need to cycle through the rest since he has already collided
					break;
				}
			}
		}
		//update position
		position.x += velocity.x;
		//update the bounds of the player
		bounds.x = position.x;
		testBounds.set(bounds);
		
		//bottom collisions
		
		//sets a to the tiles underneath the player
		a = collisionTilesY(floor);
		
		//move the testbounds first before updating
		testBounds.y += velocity.y;
		
		// allows us to fall, is constantly set to true
		// we must always check bottom collisions
		grounded = false;
		for (Tile t : a) {
			if (t != null) {
				// returns true if testbounds overlaps the tile bounds
				if (testBounds.overlaps(t.getBounds())) {
					// if they overlap, set grounded to true, stop the kicking attack
					// and reset doublejump
					grounded = true;
					isKicking = false;
					if(attackState == AttackState.Kicking)
						attackState = null;
					canDoubleJump = true;
					// update the position(move the player above the tile)
					position.y = t.getBounds().y + t.getBounds().height;
					velocity.y = 0;
				}
			}
		}
		// update the position
		position.y += velocity.y;
		bounds.y = position.y;
		
		//checks if enemies have hit the player
		checkEnemyCollisions(world.getEnemies());
		
		//checks if bullets have hit the player
		checkBulletCollisions(world.getBullets());
		
		//unscale the velocity back to normal
		velocity.scl(1/deltaTime);
	}
	
	public void checkEnemyCollisions(ArrayList<Enemy> enemies) {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			if (attackState != null) {
				if (attackState == AttackState.Chopping || attackState == AttackState.Kicking) {
					// find the new position of the attackBounds rectangle
					float x = (facesRight) ? position.x + (DIMENSION.x/16f)/2 : position.x - (DIMENSION.x/16f)/2;
					attackBounds.set(x, position.y, 1f, 1f);
					//check for collisions
					if (e.getBounds().overlaps(attackBounds)) {
						//removes the enemy from the arraylist
						e.destroy();
						//update the score
						world.setScore(world.getScore() + 50);
					}
				}
			// if player is not attacking, kill the player
			} else if (e.getBounds().overlaps(bounds)) {
				state = State.Dying;
				// sets the screen to main menu screen
				world.getGame().setScreen(new MainMenu(world.getGame(), new OrthographicCamera(), world.getScore()));
			}
		}
	}
	
	public void checkBulletCollisions(ArrayList<Enemy> bullets) {
		for (int i = 0; i < bullets.size(); i++) {
			Bullet e = (Bullet)bullets.get(i);
			if (e.getBounds().overlaps(bounds)) {
				state = State.Dying;
				e.destroy();
				world.getGame().setScreen(new MainMenu(world.getGame(), new OrthographicCamera(), world.getScore()));
			}
		}
	}
	
	public void updateVelocity(float deltaTime) {
		sinceKick += deltaTime;
		isKicking = isKicking && (sinceKick < MAX_KICKTIME);
		//jump
		if (upPressed && !isKicking) {
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
		//kick and chop
		if (lPressed && !grounded) {
			attackState = AttackState.Kicking;
			velocity.x = (facesRight? 1 : -1) * KICK_SPEED;
			velocity.y = -KICK_SPEED;
			sinceKick = 0;
		} else if (kPressed) {
			chopTime += deltaTime;
			if (canChop) {
				attackState = AttackState.Chopping;
				canChop = false;
			}
		}
		
		// if velocity is greater than max, cap it at max
		if(Math.abs(velocity.x) > MAX_VELOCITY)
			velocity.x = Math.signum(velocity.x) * MAX_VELOCITY;
		
		// set velocity to 0 if it is less than 1
		if(Math.abs(velocity.x) < 1 && grounded) {
			velocity.x = 0;
			state = State.Standing;
		}
		// update the velocity
		if (!grounded && !isKicking)
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
		case(Input.Keys.L):
			lPressed = true;
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
			canChop = true;
			break;
		case(Input.Keys.L):
			lPressed = false;
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
				chopTime = 0;
				stateTime = 0;
			}
		} else if (attackState == AttackState.Kicking) {
			if (grounded) {
				frame = kicking.getKeyFrame(stateTime);
			} else {
				frame = down.getKeyFrame(stateTime);
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
