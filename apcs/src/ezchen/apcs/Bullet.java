package ezchen.apcs;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Enemy {
	private float speed = 4f; /* edit */
	private int arrayIndex;
	private Animation moving;
	public Bullet(int direction /*+1 or -1*/, float xPos, float yPos, int arrayIndex, World world){
		position = new Vector2(xPos, yPos);
		velocity = new Vector2(speed*direction, 0);
		facesRight = (direction > 0);
		bounds = new Rectangle(xPos, yPos, .5f, .5f);
		this.arrayIndex = arrayIndex;
		this.world = world;
		moving = new Animation(.15f, Resources.bulletFrames);
		moving.setPlayMode(Animation.LOOP);
		DIMENSION.x = 16f;
		DIMENSION.y = 16f;
	}
	
	public void update(float deltaTime){
		velocity.scl(deltaTime);
		position.x += velocity.x;
		bounds.x = position.x;
		
		if (bounds.x < 1 || bounds.x > world.getWidth() - 1)
			destroy();
		stateTime += deltaTime;
		velocity.scl(1/deltaTime);
	}
	
	public void destroy() {
		world.getBullets().remove(this);
	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion frame = null;
		
		frame = moving.getKeyFrame(stateTime);
		if (frame != null) {
			if (facesRight) {
				batch.draw(frame, position.x, position.y, DIMENSION.x/16f, DIMENSION.y/16f);
			} else {
				batch.draw(frame, position.x + DIMENSION.x/16f, position.y, -DIMENSION.x/16f, DIMENSION.y/16f);
			}
		}
	}
}