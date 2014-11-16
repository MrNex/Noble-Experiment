package state.object;

import java.awt.Graphics2D;

import engine.Directory;
import equations.Equation;
import equations.InvalidEquationException;
import mathematics.Vector;
import objects.Entity;
import state.engine.BattleState;

/**
 * An enemy state which fires short bursts of bullets
 * @author Nex
 *
 */
public class EnemyBurstFireState extends TargetableState{

	//STatic attributes
	private static double shotSpeed = 100;		//Set all burst enemies projectile speed to 100
	
	//Attributes
	private Vector worldPos;
	private int burstDelay;				//Delay between bursts
	private int shotDelay;				//Delay between shots in burst
	private int numBurstRounds;			//Number of shots in each burst
	private int burstRoundCount;
	private boolean bursting;
	private Equation currentBurstEquation;
	private double previousTime;
	private double elapsedTime;

	/**
	 * Constructs an enemy battle state
	 */
	public EnemyBurstFireState() {
		super(false);

	}
	
	/**
	 * Initializes all internal member variables
	 */
	@Override
	public void init(){
		super.init();
		
		//Set timing to start at 0
		elapsedTime = 0;
		previousTime = 0;
		
		//Set number of shots currently done bursting to 0
		burstRoundCount = 0;
		
		//Set a 10 second delay between bursts
		burstDelay = 10;
		
		//Set a 1 second delay between shots in a burst
		shotDelay = 1;
		
		//Set the number of bursts per round to 3
		numBurstRounds = 3;
		
		//The enemy will not start bursting.
		bursting = false;
	}


	/**
	 * Called when an Entity is attached to this state
	 * Saves current world position
	 * Positions entity on right side of screen
	 * Refreshes entity so previous position is also set to right side of screen
	 * Alters entities dimensions to battle dimensions
	 * Updates entities shape to move it to new positions fitting new dimensions
	 * Sets previousTime to the current time to begin timing shots
	 */
	@Override
	public void enter() {
		super.enter();

		//Store worldpos for later
		//worldPos = new Vector(attachedTo.getPos());

		//Set position
		Vector posVector = new Vector(2);
		posVector.setComponent(0, Directory.screenManager.getPercentageWidth(80.0));
		posVector.setComponent(1, Directory.screenManager.getPercentageHeight(45.0));

		//Set position
		getAttachedEntity().setPos(posVector);
		
		//Refresh attached movable game object's previous position due to engine state change.
		getAttachedEntity().refresh();

		//Set battleState dimensions
		attachedTo.setWidth(200);
		attachedTo.setHeight(200);

		attachedTo.updateShape();
				
		//Set players sprite
		getAttachedEntity().setSprite(Directory.spriteLibrary.get("Werewolf"));
						
		//Run animation
		attachedTo.getSprite().queueAnimation(0, true);

		//Upon entering this state, start timing
		//Set previousTime
		previousTime = System.currentTimeMillis();
	}

	/**
	 * Updates the burstFireEnemyState
	 * Gets the current time and calculates difference from previous adding it to to elapsed time
	 * Updates previousTime
	 * If not bursting, check if elapsed time is greater than burstDelay
	 * 		If so, begin bursting and decrement elapsed time. Else, do nothing
	 * If bursting, check if elapsed time is greater than shotDelay
	 * 		If so, shoot again and decrement elapsed time. Else do nothing
	 * 		Once the number of shots is equal to the shots per burst stop bursting.
	 */
	@Override
	public void update() {
		super.update();
		
		//Get current time and set elapsed time
		double currentTime = System.currentTimeMillis();
		elapsedTime += (currentTime - previousTime) / 1000.0;
		previousTime = currentTime;

		//Check if bursting
		if(bursting){
			//Bursting, check if it's time to shoot
			if(elapsedTime >= shotDelay){
				elapsedTime -= shotDelay;
				shoot();
				burstRoundCount++;
				if(burstRoundCount >= numBurstRounds){
					bursting = false;
					burstRoundCount = 0;
					elapsedTime = 0;
				}
			}
		}
		else{
			//Not yet bursting, check if it's time to burst
			if(elapsedTime >= burstDelay){
				bursting = true;
				elapsedTime -= burstDelay;
				
				//Determine new burst equation
				currentBurstEquation = Equation.GenRndEquation(BattleState.difficulty);
			}
		}
	}
	
	/**
	 * Creates a new projectile adding it to the current state
	 * Sets projectile attributes to be Solid, and visible, and running a projectile state
	 * Sets the projectile states equation to the current burst equation
	 * Add projectile to the current state
	 */
	private void shoot(){
		//Create projectile and set attributes
		Entity projectile = new Entity(attachedTo.getXPos(), attachedTo.getYPos(), 50, 50, 1, 1, 1);
		projectile.setSolid(true);
		projectile.setSprite(Directory.spriteLibrary.get("Bullet_Yellow"));
		projectile.setVisible(true);
		
		//Create the projectile's state
		ProjectileState projState = new ProjectileState(Directory.profile.getPlayer(), attachedTo, EnemyBurstFireState.shotSpeed);
		
		
		try {
			//Alter projState to have burstEquation
			projState.setEquation(new Equation(currentBurstEquation.getExpressionList()));
		} catch (InvalidEquationException e) {
			//Should never happen
			System.out.println("Burst fire attempted to assign invalid burst equation.");
		}
		
		//Set projectile's state
		projectile.setState(projState);
		
		//Add projectile to current state
		Directory.engine.getCurrentState().addObj(projectile);

		
	}

	/**
	 * Called when an entity leaves this state
	 * Sets its position back to the worldPosition
	 * Sets its dimensions back to original overworld dimensions
	 */
	@Override
	public void exit() {
		super.exit();
	}

}
