import org.newdawn.slick.Input;

/** Represents a general power */
public class Powerup extends Sprite {
	
	/** Movement speed of a powerup */
	public final float SPEED = (float)0.1;

/**
 * @param power_name
 * @param x
 * @param y
 */
	public Powerup(String power_name, float x, float y) {
		
		super(power_name, x, y);
	}
	
	/** General movement logic
	 *  @param delta Time passed since last frame (milliseconds).
	 *  @param input Reads input from the user 
	 */
	public void update(Input input, int delta) {
		
		moveY(SPEED, delta);		
	}


}
