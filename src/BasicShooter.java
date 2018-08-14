import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Input;

/** Represents a Basic Shooter, moves to a random vertical point then fires at given intervals */

public class BasicShooter extends Enemy{
	
	/** Basic Shooter file name */
	public static final String NAME = "basic-shooter";
	/** Basic Enemy movement speed */
	public static final float  SPEED = (float)0.2;
	/** Random object generator */
	private Random random = new Random();
	/** Random position generated */
	private float randomPosition;
	/** List of Shots fired by the enemy */
	private List<EnemyShot> shotList = new ArrayList<EnemyShot>();
	/** Delay it takes to fire another bullet */
	private  long bulleyDelay = 3500;
	/** Timer that counts down as Basic Shooter is in action */
	private  long time = 3500;
	/** Indicator of whether or not Basic Shooter can fire */
	private boolean canFire;
	
/** Creates a new Basic Shooter
 * @param x
 */
	public BasicShooter(float x) {
		super(NAME, x, -64, 200);
		randomPosition = random.nextInt(464 - 48) + 48;
	}
	
	/** Basic Movement and Firing logic
	 *  @param delta Time passed since last frame (milliseconds).
	 *  @param input Reads input from the user 
	 */
	public void update(Input input, int delta) {
		
		//Shooting logic, dictated by canFire and its shot timer 
		if (time <=0) {
			canFire = true;
		}
		
		else {canFire = false;}
		
		if(canFire == true) {
			
		this.shotList.add(new EnemyShot(this.getX(),this.getY()));
		time = bulleyDelay;

		}
		
		
		//Movement logic dictated by if y position at a random position
		if(getY() <= randomPosition) {
			moveY(SPEED, delta);		
		}
		
		time -= delta;

	}
	
	/** Returns Basic Shooter's ShotList
	 * @return List
	 */
	public List<EnemyShot> getShotList(){
		return shotList;
	}
	

}
