import org.newdawn.slick.Input;

/** Represents a Basic Enemy, moves down in linear pattern */
public class BasicEnemy extends Enemy {
	
	/** Basic Enemy file name */
	public static final String NAME = "basic-enemy";
	/** Basic Enemy movement speed */
	public static float SPEED = (float)0.2;

/** Creates a new Basic Enemy
 * @param x
 */
	public BasicEnemy(float x) {
		super(NAME, x, -64, 50);
	}

	/** Basic Movement logic
	 *  @param delta Time passed since last frame (milliseconds).
	 *  @param input Reads input from the user 
	 */
	public void update(Input input, int delta) {
		
		moveY(SPEED, delta);
		
	}

	
	

}

