import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/** Represents the Player */
public class Player extends Sprite{

// Define Constants used by Player 
/** Player's file name */
public final static String SHIP = "spaceship";

/** Player's movement speed */
public final static float SPEED = (float)0.5;

/**Player's horizontal offset for their shot */
public static final float INIT_SHOT_POS_X = 25;
/**Player's vertical offset for their shot */
public static final float INIT_SHOT_POS_Y = -5;

//Variables used by Player for bulletdelay and powerup timing 

/**Player's delay between being able to fire shots */
private long bulletDelay = 350;

/**Player's Shot Timer */
private long time = 0;

/**Indicates whether or not player can Fire */
private boolean canFire;

/** Indicates whether or not player has the shot speed powerup */
private boolean isPower = false;

/** Timer for how long player can be powered up  */
private float powerTimer = 5000;

/** Player's list of shots fired */
private List<Shot> shotList = new ArrayList<Shot>();


/** Creates a new player 
 * @throws SlickException
 */
	public Player() throws SlickException{	
			super(SHIP, 480,688);
	}
	
	/** General player logic 
	 *  @param delta Time passed since last frame (milliseconds).
	 *  @param input Reads input from the user 
	 */
	public void update(Input input, int delta) {
		
		//Logic for checking if shot can fire, based on ShotSpeed Powerup or just general shot timing 
		if(getIsPower()) {
			bulletDelay = 150;
			setPowerTimer(getPowerTimer() - delta);
		}
		else {
			bulletDelay = 350;
		}
		
		if(getPowerTimer() <= 0) {
			setIsPower(false);
		}

		
		if (time <=0) {
			canFire = true;
		}
		
		
				
		// Movement Logic
		
		if(input.isKeyDown(Input.KEY_X)){
			System.exit(0);								
		}
		
		inputMove(SPEED, delta, input);
		
		// Barrier Logic
		
		barrier();


		//If Player can fire, SPACE fires and resets the shot timer 
		if(input.isKeyDown(Input.KEY_SPACE)){
			
			if(canFire == true) {	
			this.getShotList().add(new Shot(this.getX(), this.getY()));	
			time = bulletDelay;
			canFire = false;			
			}
}
				

		
		
		// Decrement the shot timer every update 
		time -= delta;
	
	}
	
	//Getters and Setters
	
	public List<Shot> getShotList(){
		return shotList;
	}
	
	public  boolean getIsPower() {
		return isPower;
	}

	public  void setIsPower(boolean isPower) {
		this.isPower = isPower;
	}

	public float getPowerTimer() {
		return powerTimer;
	}

	public void setPowerTimer(float powerTimer) {
		this.powerTimer = powerTimer;
	}
		

	}
		

