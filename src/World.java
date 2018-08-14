 import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/** World in which objects interact
 * @author Christian Gioia, 694112
 */
public class World {
	// Declare the objects in the Game's World 

	/** A helpful speed modifier for testing, speeds up vertical enemy movement as well as the timers used in the boss encounter */
	private static float SPEED_MOD =1;
	
	/** Background scrolling speed */
	private static final float BACKGROUND_SCROLL_SPEED = 0.2f;
	
	/** Offset of background for rendering purposes */
	private float backgroundOffset = 0;

	
	// Components for tracking life, score and displaying this
	/** Number of lives player still has */
	public static int life_number = 3;
	
	/** Score the player has achieved so far */
	public int score = 0;
	
	/** array of lives images to be displayed */
	public static Sprite[] lives = new Sprite[life_number];
	
	//The components used in reading the waves file into a easier format to process
	
	/** number of enemies to be generated */
	public static final int N_ENEMIES = 121;
	
	/** array of strings derived from the lines read in the wave file */
	private String arr[];
	private String words[];
	private String whole;
	private String[][] enemy_data = new String[N_ENEMIES][3];
	
	// Defining the background speed and the values used as essentially impossible values for rendering or comparison against runningtime	
	/** Out of bounds value used to clear  */
	public static final float OUT_OF_BOUNDS = 10000;
	
	/** Out of bounds number used in the processing stage of reading the waves data, *(assumes game wont be run longer than 2.77 hours) */
	public static final String N_OUT_OF_BOUNDS = "9999999999999999999999999999999999999999999999999999999999999999999999999999999";
	
	/** Scrolling speed of the background */
	public static final float BGROUND_SPEED = 0.2f;


	// The rest of the objects used 
	private Image background;
	
	private Player player;
	
	/** List of enemies */
	private List<Enemy> enemyList = new ArrayList<Enemy>();
	
	private Shield shield;
	
	/** List of powerups */
	private List<Powerup> powerupList = new ArrayList<Powerup>();
	
	/** Random number generator */
	private Random r = new Random();

		
/** Initialize the World of the game 
 * @throws SlickException
 */
	public World() throws SlickException {
		
		//INITIALIZE EVERY OBJECT// 
		
		// Assign space image to the background 
		background = new Image("res/space.png");
		
		// Creates the 3 lives 
		for(int i = 0; i<3; i++) {
			lives[i] = new Sprite("lives", 20 + 40*i, 696);
		}

		// Initialize the Player and its Shield
		player = new Player();
		
		shield = new Shield(player.getX(), player.getY()); 
		
		
	// Read through the waves file and process into an array format thats easier for wave generation 	
		 try(BufferedReader br = new BufferedReader(new FileReader("res/waves.txt")))
			{			
			 	String text;
			 	int count = 0;
				while ((text = br.readLine()) != null && count<N_ENEMIES) {
				
				if (text.charAt(0) == '#') {continue;}
				
				arr = text.split(",");
				words = arr[0].split("(?=[A-Z])");
				
				if(words.length == 2) {
				whole = String.format("%s-%s", words[0].toLowerCase(), words[1].toLowerCase());}
				
				else {
					whole = String.format("%s", words[0].toLowerCase());
				}
				
				enemy_data[count][0] = whole;
				enemy_data[count][1] = arr[1];
				enemy_data[count][2] = arr[2];
				
				count++;
				}
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	}
		 

	}

	/** Update logic that runs every frame
	 * @param input
	 * @param delta
	 */
	public void update(Input input, int delta) {
		
		backgroundOffset += BACKGROUND_SCROLL_SPEED * delta;
		backgroundOffset = backgroundOffset % background.getHeight();

		
		//Speed the elements helpful for testing x5 when S key is pressed 
		if(input.isKeyDown(Input.KEY_S)){
			setSPEED_MOD(5);								
		}
		else {setSPEED_MOD(1);}

		
		
// Continually check if the running time exceeds the delay value 		
		for(int i = 0; i< enemy_data.length; i++) {
			
			if(App.runningTime >= Float.parseFloat(enemy_data[i][2])) {
				
				if(enemy_data[i][0].equals("basic-enemy")) {	
					
				this.enemyList.add(new BasicEnemy(Float.parseFloat((enemy_data[i][1])) ));
				enemy_data[i][2] = N_OUT_OF_BOUNDS;
				
				}
				
				if(enemy_data[i][0].equals("sine-enemy")) {	
					
					this.enemyList.add(new SineEnemy(Float.parseFloat((enemy_data[i][1])) , Float.parseFloat(enemy_data[i][2] )));
					enemy_data[i][2] = N_OUT_OF_BOUNDS;
					
					}
				
				if(enemy_data[i][0].equals("basic-shooter")) {	
					
					this.enemyList.add(new BasicShooter(Float.parseFloat((enemy_data[i][1])) ));
					enemy_data[i][2] = N_OUT_OF_BOUNDS;
					
					}
				
					if(enemy_data[i][0].equals("boss")) {	
					
					this.enemyList.add(new Boss(Float.parseFloat((enemy_data[i][1])) ));
					enemy_data[i][2] = N_OUT_OF_BOUNDS;
					
					}
}
}
		
		
		//Update Player
		player.update(input, delta);
		
		//Update shield 
		shield.update(input, delta);

		//Check if play physically contacts a enemy, if so activate the shield and decrement life 
		for (int i=0;i<enemyList.size();i++) {
			if(player.contactSprite(enemyList.get(i)) == true) {
				
				if(shield.getIsActive() == false) {
				
				life_number--;
				shield.setTimer(3000);
				shield.setIsActive(true);
				
				}
			};
}		 
	
// Updates every enemy and then checks for if Player hits and Enemy with a Shot
			for(int i=0; i < this.enemyList.size(); i++){
				
				this.enemyList.get(i).update(input, delta);
				
				
				for(int j=0; j < player.getShotList().size(); j++){
					

					/* Check Collision, Delete corresponding enemy and shot and increase the score, also checks if enemy is inframe */
					if (this.enemyList.get(i).contactSprite(player.getShotList().get(j)) && (player.getShotList().get(j).getY() > Player.TOP_BARRIER)){
						
						
						// Boss Logic, remove a life if hits 
						if(this.enemyList.get(i) instanceof Boss) {
							
							((Boss)(this.enemyList.get(i))).shots--;
							player.getShotList().remove(j);
							
							if(((Boss)(this.enemyList.get(i))).shots == 0) {
								score += ((Boss)(this.enemyList.get(i))).getScore();
								this.enemyList.remove(i);
								System.exit(0);
							}

						}
						
						//Logic for all other enemies
						else {
						score = score + this.enemyList.get(i).getScore();
						
						// !Create a 5% chance of a powerup generated upon hit, then 50% chance of either powerup UPDATE THIS WHEN FINALIZING!
						float chance = r.nextFloat();
						
						if(chance <= 0.05f) {
						
						boolean chance2 = r.nextBoolean();
						
						if(chance2) {
							this.powerupList.add(new SpeedPower(this.enemyList.get(i).getX(), this.enemyList.get(i).getY()));
						}
						
						else{
							this.powerupList.add(new ShieldPower(this.enemyList.get(i).getX(), this.enemyList.get(i).getY()));
						}
						
						}
						
						
						// Enemy and Shot removal logic for all other enemies
						this.enemyList.remove(i);
						
						player.getShotList().remove(j);

						}
						
						if(i>0) i -= 1;			// Decrement i and j to avoid IndexOutOfBounds exception i and j have to be decreased 	*/
						if(j>0) j -= 1;			
						
						if(this.enemyList.size() == 0 || player.getShotList().size() == 0){
							return;
					}
						
				}
		}
}
			
			
			//Update all powerups in powerups list
			
			for(Powerup i: this.powerupList) {
				
				i.update(input, delta);
				
			}
			
			//Checks if player contacts a powerup, remove it from view and apply effect if true
			
			for (int x =0; x<this.powerupList.size();x++) {
				if(player.contactSprite(this.powerupList.get(x))) {
					
					// Shield PowerUp 
					if( this.powerupList.get(x) instanceof ShieldPower )
					shield.setTimer(5000);
					this.powerupList.get(x).clearImage();
					this.powerupList.get(x).getBB().setX(OUT_OF_BOUNDS);
					this.powerupList.get(x).getBB().setY(OUT_OF_BOUNDS);
					
					// Shield PowerUp 
					if( this.powerupList.get(x) instanceof SpeedPower ) {
					
					player.setIsPower(true);
					player.setPowerTimer(5000);
					
					this.powerupList.get(x).clearImage();
					this.powerupList.get(x).getBB().setX(OUT_OF_BOUNDS);
					this.powerupList.get(x).getBB().setY(OUT_OF_BOUNDS);

				}
			
			}

		}
				
			
			
		//Update player's shot list	
			for (Shot z: player.getShotList()) {
				z.update(delta);
			}
			
 			
			//Accessing the EnemyShotList of every BasicShooter object and the Boss and updating it
			for (Enemy x: this.enemyList) {
				
				if (x instanceof BasicShooter || x instanceof Boss) {
					
					for(int i=0; i< x.getShotList().size();i++) {
						
						x.getShotList().get(i).update(delta);
						
						if(x.getShotList().get(i).contactSprite(player)){
							
							if(shield.getIsActive() == false) {
							
							x.getShotList().remove(i);
							
							life_number--;
							
							shield.setTimer(3000);
							
							}
							
							else {
							}
							
							if(x.getShotList().size() == 0) {
								return;
							}
							
						}
					}
					
					
}
				
		
				
}

	
			// Continually update the running time
			
			App.runningTime += SPEED_MOD*delta;       
	        //Termination condition if lives reduced to 0
	        if(life_number==0) {
	        	System.exit(0);
	        }

		// Update all of the sprites in the game
	}
	
	/** Render all Sprites in the Frame
	 * 
	 */
	public void render() {
		
		// Render the 8 descending Tiles of Space 		

		for (int i = 0; i < App.SCREEN_WIDTH; i += background.getWidth()) {
			for (int j = -background.getHeight() + (int)backgroundOffset; j < App.SCREEN_HEIGHT; j += background.getHeight()) {
				background.draw(i, j);
			}
		}
		

		//Render the player 
		player.render();
		
		//Render the shield (if its active)
		if(shield.getIsActive()) {
		shield.render();
		}
		
		//Render every player's shot
		
		for(Shot i : player.getShotList()){
			i.render();
		}	
		
		//Render every shot from a BasicShooter or Boss
		for (Enemy x: this.enemyList) {
			if (x instanceof BasicShooter || x instanceof Boss) {
				for(EnemyShot y: x.getShotList()) {
					y.render();
				}
			}
		}

		
		
		
		// Render every enemy
		for(Enemy i : enemyList){
			i.render();
		}		
		
		//Render every powerup
		for(Powerup i: this.powerupList) {
			i.render();
		}
		

		//Render the life symbols 
		for(int i = 0; i<life_number; i++) {
			lives[i].render();
		}
		
		

	}

	/** get Speed Modifier
	 * @return
	 */
	public static float getSPEED_MOD() {
		return SPEED_MOD;
	}

	/** Set the speed modifier
	 * @param speedMod
	 */
	public static void setSPEED_MOD(float speedMod) {
		SPEED_MOD = speedMod;
	}
}
