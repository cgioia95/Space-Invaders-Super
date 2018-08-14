import java.util.List;

import org.newdawn.slick.Input;

/** Represents a general Enemy */
public class Enemy extends Sprite{
	
/** Score of the enemy acquired by player on their death */
private int score;

/** Creates a new Enemy
 * @param imageName
 * @param x
 * @param y
 * @param score
 */
	public Enemy(String imageName,float x, float y, int score) {
		super(imageName, x, -64);
		this.setScore(score);
	}
	
	/** Basic Movement and Firing logic
	 *  @param delta Time passed since last frame (milliseconds).
	 *  @param input Reads input from the user 
	 */
	public void update(Input input, int delta) {}

	//Getters and Setters for the Enemies Score and List
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<EnemyShot> getShotList() { 
		return null;
	}

}
