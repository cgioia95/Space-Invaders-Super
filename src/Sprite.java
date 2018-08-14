import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import utilities.BoundingBox;

/** Represents a general Sprite, object rendered onto the screen */

public class Sprite {
	
	/** Certain Sprite's leftmost point they can go to*/
	public static final float LEFT_BARRIER = 30;

	/**Certain Sprite's rightmost point they can go to */
	public static final float RIGHT_BARRIER = 1000;

	/**Certain Sprite's upmost point they can go to */
	public static final float TOP_BARRIER = 30;

	/**Certain Sprite's bottomost point they can go to */
	public static final float BOTTOM_BARRIER = 735;
	
	/** Actual image of the Sprite */
	private Image img;
	
	/** x-coordinate of the sprite on the screen */
	private float x;
	
	/** y-coordinate of the sprite on the screen */
	private float y;
	
	/** Hitbox of the Sprite */
	private BoundingBox hitbox;

	/**
	 * @param imageName 
	 * @param x
	 * @param y
	 */
	public Sprite(String imageName,float x, float y) {
		loadImage(imageName);
		setX(x);
		setY(y);
		createHitBox(getImage(),getX(), getY());
	}
	
	//Intialization Functions, loading the image, setting the position and creating the hitbox 
	/** Loads the image into the Sprite
	 * @param imageName
	 */
	public void loadImage(String imageName)
	{
	    try
	    {
	        img = new Image("res/"+imageName+".png");
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	    }
	}	
	
	/** Sets the initial position of the Sprite
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y)
	{
	    this.x = x;
	    this.y = y;
	}	

	/** create the sprites hitBox */
	public void createHitBox(Image img, float x, float y) {
		hitbox = new BoundingBox(this.img, this.x, this.y);
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
//Getters and Setters and a Function to nullify the Sprite's Image 
	
	public void clearImage() {
		img = null;
	}
	
	public Image getImage() {
		return img;
	}
	
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}

	public BoundingBox getBB() {
		return hitbox;
	}
	
	public void clearHitBox(){
		hitbox = null;
	}

	
	/** Render sprite to its coordinates */
	public void render() {
		
		
		
		if (this.img != null)
		{
			this.img.drawCentered(this.x, this.y);
		}
		
	}
	

	// Methods available to sprites for differing purposes
	
	/** Return true if one sprite interesects hitbox of another sprite 
	 * @param other
	 * @return
	 */
	public boolean contactSprite(Sprite other) {
		if(hitbox.intersects(other.hitbox)) {
			return true;
		}
		
		return false;
		}

	/** Moves object vertically (default orientation to the left)
	 * @param speed - speed of sprite
	 *  @param delta Time passed since last frame (milliseconds).
	 */
public void moveY(float speed, int delta) {
	setY((getY() +  (delta*speed*World.getSPEED_MOD())));
	getBB().setY(getY());	
}

/** Moves object horizontally (default orientation to the right)
 * @param speed - speed of sprite
 *  @param delta Time passed since last frame (milliseconds).
 */
public void moveX(float speed, int delta) {
	setX((getX() +  (delta*speed*World.getSPEED_MOD())));
	getBB().setX(getX());	
}

/** Applies a barrier to certain sprites so they cannot move out of these bounds
 * 
 */
public void barrier() {
	if (getX()>=RIGHT_BARRIER) {
		setX(RIGHT_BARRIER);
	}
	
	if (getX()<=LEFT_BARRIER) {
		setX(LEFT_BARRIER);
	}
	
	if (getY()<=TOP_BARRIER) {
		setY(TOP_BARRIER);
	}

	if (getY()>=BOTTOM_BARRIER) {
		setY(BOTTOM_BARRIER);
	}

}

/** Movement logic for objects that take input
 * 
 * @param speed
 * @param delta
 * @param input
 */
public void inputMove(float speed, int delta, Input input) {
	if(input.isKeyDown(Input.KEY_UP)){
		moveY(-speed, delta);
	}
	
	if(input.isKeyDown(Input.KEY_DOWN)){
		moveY(speed,delta);
	}
	
	if(input.isKeyDown(Input.KEY_RIGHT)){
		moveX(speed, delta);					
	}
	
	if(input.isKeyDown(Input.KEY_LEFT)){
		moveX(-speed, delta);
	}

}

}
