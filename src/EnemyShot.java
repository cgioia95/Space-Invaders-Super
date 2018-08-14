/** Represents a Shot fired by an Enemy */
public class EnemyShot extends Sprite{
	
	/** Enemy Shot's Movement Speed */
	public static final float SPEED = (float) 0.7;
	
	/** Enemy Shot file name */
	public static final String SHOT = "enemy-shot";

	/** Creates an Enemy Shot 
	 * @param x
	 * @param y
	 */
	public EnemyShot(float x, float y) {
		super(SHOT, x , y);
	}
	
	/** General movement logic
	 *  @param delta Time passed since last frame (milliseconds).
	 */
public void update(int delta){
		
        moveY(SPEED, delta);
	}

}
