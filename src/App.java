import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.AppletGameContainer.Container;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


/**
 * Main class for the game.
 * Handles initialisation, input and rendering.
 */
public class App extends BasicGame {
	
 	/** Running Time of the program */
	public static long runningTime =0;
 	/** screen width, in pixels */
    public static final int SCREEN_WIDTH = 1024;
    /** screen height, in pixels */
    public static final int SCREEN_HEIGHT = 768;
    
    
    /** World object that captures all interactions in the program */
    private World world;

    public App() {    	
        super("Shadow Wars");
    }

    @Override
    /** Initializes the Game with World
     * @param gc The Slick game container object
     */
    public void init(GameContainer gc)
    		throws SlickException {
    	//Initialize our World Object 
    	world = new World();
    }

    /** Update the game state for a frame.
     * @param gc The Slick game container object.
     * @param delta Time passed since last frame (milliseconds).
     */
    @Override
    public void update(GameContainer gc, int delta)
    		throws SlickException {
        // Get data about the current input (keyboard state).
        Input input = gc.getInput();
        //Update the World object every frame 
        world.update(input, delta);
        

        
       
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param gc The Slick game container object.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(GameContainer gc, Graphics g)
    		throws SlickException {
    	//Update the World render 
    	world.render();
    	g.drawString(Integer.toString(world.score), 20, 738);
    	


    }

    /** Start-up method. Creates the game and runs it.
     * @param args Command-line arguments (ignored).
     */
    public static void main(String[] args)
    		throws SlickException {
        AppGameContainer app = new AppGameContainer(new App());
        app.setShowFPS(true);
        app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
        app.start();	
        
        app.setMaximumLogicUpdateInterval(1);

    }

}