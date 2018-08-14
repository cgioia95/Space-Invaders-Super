import org.newdawn.slick.Input;
import java.lang.Math;

/** Represents a SineEnemy */
public class SineEnemy extends Enemy{

/** SineEnemy file name */
public static final String NAME = "sine-enemy";

/** SineEnemy movement speed */
public static float SPEED = (float)0.15;

/** Amplitude Constant */
public static float aConstant = 96;

/** Period Constant */
public static float pConstant = 1500;


/** Delay value for enemy generation and horizontal movement control */
private float delay;

/** Original x value enemy generated at */
private float orig_x;



/** Creates a new SineEnemy
 * @param x
 * @param delay
 */
public SineEnemy(float x, float delay) {
		super(NAME, x, -64, 100);
		this.delay = delay;
		this.orig_x = x;
	}

/** Details downward movement and sine-based horizontal movement
 	 *  @param delta Time passed since last frame (milliseconds).
	 *  @param input Reads input from the user 
 */
public void update(Input input, int delta) {
		
		moveY(SPEED, delta);

		setX((float)( this.orig_x + aConstant*Math.sin(((2*Math.PI)/pConstant)*(App.runningTime - delay))) ) ; 
		getBB().setX(getX());	

	}

}
