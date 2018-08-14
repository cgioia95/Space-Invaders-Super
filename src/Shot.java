/** Represents a Player's Shot */

public class Shot extends Sprite {
	
	/** Player's Shot Moement Speed  */
	public static final float SPEED = 3;

	/** Player's Shot file name */
	public static final String SHOT = "shot";

/** Creates a new Player Shot
 * @param x
 * @param y
 */
	public Shot(float x, float y) {
		super(SHOT, x , y);
	}
	
/** General movement logic 
	 *  @param delta Time passed since last frame (milliseconds).
 */
public void update(int delta){
		
		moveY(-SPEED, delta);	
	}





}
