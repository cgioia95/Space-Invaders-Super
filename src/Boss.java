import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Input;

/** Represents the Boss, cycles through between 4 and 5 Phases of combat and movement */
public class Boss extends Enemy{
	
	/** Boss file name */
	public static final String NAME = "boss";
	
	/** Random object generator */
	private Random random = new Random();
	
	/** Randomly generated position */
	private float randomPosition;
	
	/** Speed #1 */
	public final float SPEED_1 = (float)0.05;
	
	/** Speed #2 */
	public final float SPEED_2 = (float)0.2;
	
	/** # of Shots to kill boss */
	public int shots = 3;
	
	/** First Phase of Boss Logic */
	private boolean PHASE_1 = true;
	
	/** Second Phase of Boss Logic */
	private boolean PHASE_2 = false;
	
	/** Third Phase of Boss Logic */
	private boolean PHASE_3 = false;
	
	/** Fourth Phase of Boss Logic */
	private boolean PHASE_4 = false;
	
	/** Fifth Phase of Boss Logic */
	private boolean PHASE_5 = false;

	/** Shotlist of the Boss */
	private List<EnemyShot> shotList = new ArrayList<EnemyShot>();
	
	/** Overall timer */
	private long timeO = 3000;
	
	/** Shooting delay within shooting phases */
	private long timeI = 200;

	/** Indicates if the Boss can fire */
	private boolean canFire;
	
	/** Timer #1 */
	private long time_1 = 5000;
	
	/** Timer #2 */
	private long time_2 = 2000;

	
		
	/** Creates a new Boss
	 * @param x
	 */
	public Boss(float x) {
		super(NAME, x, -64, 5000);
		// TODO Auto-generated constructor stub
	}
	
	/** Movement and Firing Logic, as well as cycling through phases 
	 *  @param delta Time passed since last frame (milliseconds).
	 *  @param input Reads input from the user 
	 */
	public void update(Input input, int delta) {
		
		
		// PHASE 1: MOVE DOWN TO 72 
		if(getY() < 72) {
		moveY(SPEED_1, delta);
		}
		
		else {
			
			if(PHASE_1) {
				
				time_1 = (long) (time_1 - delta*World.getSPEED_MOD());
				
				if( time_1 > 0) {

				}
				
				else{
					time_1 = 5000;
					PHASE_1 = false;
					PHASE_2 = true;
				    randomPosition = (float)random.nextInt(896 - 128) + 128;

				}	
			}
			
			
			//PHASE 2: MOVE TO RANDOM POSITION
			if(PHASE_2) {
				
				if( (getX() < randomPosition + 1   && (getX() > randomPosition - 1)))   {
					PHASE_3 = true;
					PHASE_2 = false;
				}
				
				
				else if (getX() > randomPosition) {
					
					moveX(-SPEED_2, delta);
				}
				
				else if(getX() < randomPosition ) {
					
					moveX(SPEED_2, delta);
				}

			}
			
			//PHASE 3 MOVE TO ANOTHER RANDOM X POSITION
			if(PHASE_3) {
				
				time_2 = (long) (time_2 - delta*World.getSPEED_MOD());
				
				if( time_2 > 0) {

				}
				
				else{
					time_2 = 2000;
					PHASE_3 = false;
					PHASE_4 = true;
				    randomPosition = (float)random.nextInt(896 - 128) + 128;

				}	
				
			}
			
			//PHASE 4: MOVING WHILE SHOOTING
			if(PHASE_4) {
				
				
				if( (getX() < randomPosition + 1   && (getX() > randomPosition - 1)))   {
					PHASE_4 = false;
					
					if( timeO >= 0) {
						PHASE_5 = true;
						timeI = 200;
					}
					
					else {PHASE_1 = true;
					timeO = 3000;
					}

					}
				
				
				else { 
 					
				timeI = (long) (timeI - delta*World.getSPEED_MOD());

				
				if (getX() > randomPosition) {
					
					moveX(-SPEED_2, delta);

				}
				
				else if(getX() < randomPosition ) {
					
					moveX(SPEED_2, delta);
				}
				
				
				if (timeI <=0) {
					canFire = true;
					timeI = 200;
				}
				
				else {canFire = false;}
				
				if(canFire == true) {
					
					fire();
					timeI = 200;

					}
				timeO = (long) (timeO - delta*World.getSPEED_MOD());


				}
				
				timeI = (long) (timeI - delta*World.getSPEED_MOD());

				}
			
			//PHASE 5: RUNS WHEN THE SHOOTING TIME HAS NOT REACHED 3000 MILLISECONDS
			if(PHASE_5) {
				
				if(timeO <= 0) {
					
					PHASE_5 = false;
					PHASE_1 = true;
					timeO = 3000;
				}
				
				else {
				

				if (timeI <=0) {
					canFire = true;
					timeI = 200;
				}
				
				else {canFire = false;}
				
				if(canFire == true) {
					
					fire();
					
					timeI = 200;

				}
				
				timeO = (long) (timeO - delta*World.getSPEED_MOD());
				
				}
				
				timeI = (long) (timeI - delta*World.getSPEED_MOD());

				
				}
				

				}

}
	
	/** Returns Basic Shooter's ShotList
	 * @return List
	 */	
	public List<EnemyShot> getShotList(){
		return shotList;
	}

/** Fire shots from 4 specified offsets */
public void fire() {
	this.shotList.add(new EnemyShot(this.getX() - 97,this.getY()));
	this.shotList.add(new EnemyShot(this.getX() -74,this.getY()));
	this.shotList.add(new EnemyShot(this.getX() + 74,this.getY()));
	this.shotList.add(new EnemyShot(this.getX() + 97,this.getY()));
	}
}

		
		
	



