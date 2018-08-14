import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/** Represents a Shield */
public class Shield extends Sprite{
	
	/**Shield's movement speed */
	public static float SPEED = (float)0.5;
	
	/**Shield file name */
	public static final String NAME = "shield";
	
	/**Shield timer for when its active */
	private long timer = 3000;
	
	/** Indicates if Shield is active */
	private boolean isActive = true;
	
	

/** Creates a new Shield
 * @param x
 * @param y
 * @throws SlickException
 */
	public Shield(float x, float y) throws SlickException {
		
		super(NAME, x, y);
		
	}

	/**
	 *  @param delta Time passed since last frame (milliseconds).
	 *  @param input Reads input from the user 
	 */
	public void update(Input input, int delta) {
		
		inputMove(SPEED, delta, input);
		
		barrier();
		
		if(getTimer()>= 0) {
			
			setIsActive(true);
			setTimer(getTimer() - delta);
		}
		
		else {setIsActive(false);}
				
	}



// Getters and Setters

	public boolean getIsActive() {
		return isActive;
	}


	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}



	public long getTimer() {
		return timer;
	}


	public void setTimer(long timer) {
		this.timer = timer;
	}


}

