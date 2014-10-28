package Objects.ObjStates.MObjStates.EntityStates;

import java.awt.Graphics2D;

import Engine.Directory;
import Engine.States.BattleState;
import Equations.Equation;
import Equations.InvalidEquationException;
import MathHelp.Vector;
import Objects.Entity;

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

		elapsedTime = 0;
		previousTime = 0;
		burstRoundCount = 0;
		burstDelay = 10;
		shotDelay = 1;
		numBurstRounds = 3;
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
		worldPos = new Vector(attachedTo.getPos());

		//Set position
		Vector posVector = new Vector(2);
		posVector.setComponent(0, Directory.screenManager.getPercentageWidth(85.0));
		posVector.setComponent(1, Directory.screenManager.getPercentageHeight(45.0));

		//Set position
		attachedTo.setPos(posVector);
		
		//Refresh attached movable game object's previous position due to engine state change.
		getAttachedMObj().refresh();

		//Set battleState dimensions
		attachedTo.setWidth(75);
		attachedTo.setHeight(300);

		attachedTo.updateShape();

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
		//Revert back to overworld pos
		attachedTo.setPos(worldPos);

		//Revert back to original overworld dimensions
		attachedTo.setWidth(25);
		attachedTo.setHeight(25);
		attachedTo.updateShape();
	}

}
