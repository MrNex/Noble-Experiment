package Engine.Manager;

/**
 * Class defines a component of the engine which handles sprites and animations.
 * Tracks timing for animations and defines fps 
 * @author Nex
 *
 */
public class SpriteManager extends Manager {

	//Immutable Attributes
	private final double fps;
	
	//Attributes
	private long previousTime;		//Tracks the time previous cycle in ms
	private long currentTime;			//Tracks the time current cycle in ms
	private double deltaTime;			//Tracks the change in time from last frameChange to this frameChange in seconds
	private int deltaFrames;			//Tracks the change in frames from last cycle to this cycle
	
	
	//Accessors
	/**
	 * GEts the number of frames passed since last update
	 * @return
	 */
	public int getDeltaFrames(){
		return deltaFrames;
	}
	
	
	/**
	 * Constructs a sprite manager
	 * 
	 * Sets the FPS for all sprite animations
	 * Initialize all member variables
	 */
	public SpriteManager() {
		fps = 6.0;
		
	}

	/**
	 * Initializes all member variables
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		currentTime = System.currentTimeMillis();
		previousTime = currentTime;
		
		deltaTime = 0;
		deltaFrames = 0;
	}

	/**
	 * Updates the SpriteManager
	 * Gets time passed since last update,
	 * Increments time passed since last frame change
	 * Calculates number of frames passed since last frameChange
	 * Decrements time passed since last frameChange by time needed for deltaFrames to pass
	 * Updates previous time to be current time for next cycle
	 */
	@Override
	public void update() {
		
		currentTime = System.currentTimeMillis();
		
		deltaTime += ((double)(currentTime - previousTime) / 1000.0);
		deltaFrames = (int)(deltaTime * fps);
		
		deltaTime -= ((double)deltaFrames) * (1.0/(double)fps);
		previousTime = currentTime;
	}

}
