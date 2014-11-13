package state.object;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;

import javax.swing.Timer;

import engine.Directory;
import mathematics.Vector;
import objects.Entity;
import sprites.Sprite;

/**
 * An enemy state which fires several bullets at once
 * @author Robert Schrupp
 *
 */
public class EnemyScatterShotState extends TargetableState{

	//Static attributes
	private static int attackSpeed = 7;				//Set enemy's attack speed to 10 seconds to start
	private static double shotSpeed = 50;			//Set enemy's projectile speed to 50
	
	//Attributes
	private Vector worldPos;
	private Timer attackTimer;
	private double previousTime;
	private double elapsedTime;
	private int numBulletsPerShot;

	/**
	 * Constructs an Enemies battle state
	 * 
	 */
	public EnemyScatterShotState() {
		super(false);
		
	}
	
	/**
	 * Initializes all internal membervariables
	 */
	@Override
	public void init(){
		super.init();
		
		elapsedTime = 0;
		previousTime = 0;
		numBulletsPerShot = 3;
	}

	/**
	 * Enters & Prepares the battlestate
	 * Stores the worldposition and re-positions entity for battle
	 * Sets width and Height for battle state view
	 * Updates the shape of the object
	 * Sets the equation visibility to true
	 * Begins attacking timer
	 */
	@Override
	public void enter() {
		super.enter();
		//Store worldPos
		//worldPos = new Vector(attachedTo.getPos());

		
		
		//Set position
		Vector posVector = new Vector(2);
		posVector.setComponent(0, Directory.screenManager.getPercentageWidth(85.0));
		posVector.setComponent(1, Directory.screenManager.getPercentageHeight(45.0));
		
		//Set position
		attachedTo.setPos(posVector);
		
		//Refresh attached movable game object's previous position due to engine state change.
		getAttachedEntity().refresh();

		//Set battleState dimensions
		attachedTo.setWidth(75);
		attachedTo.setHeight(300);

		attachedTo.updateShape();

		//Upon entering this state, start timing
		
		//Set previousTime to currentTime
		previousTime = System.currentTimeMillis();

	}

	/**
	 * Updates the enemiesBattleState
	 * Gets the current time,
	 * Increments elapsed time since last shot
	 * IF it's time to shoot again, set elapsed time to 0 and shoot
	 * Finally, set previousTime to the currentTime.
	 */
	@Override
	public void update() {
		super.update();
		
		double currentTime = System.currentTimeMillis();
		elapsedTime += (currentTime - previousTime) / 1000.0;
		
		if(elapsedTime > EnemyScatterShotState.attackSpeed){
			elapsedTime = 0;
			
			// TESTING
			//shoot(attachedTo.getYPos() + 50.00);
			
			// shoot numBulletsPerShot
			
			for(int i=0; i<numBulletsPerShot; i++){
				shoot(attachedTo.getYPos() + (50*(i-1)));
			}
			
		}
		
		previousTime = currentTime;

	}
	
	/**
	 * Creates new projectiles and adds them to the current state:
	 * Creates projectiles and sets them as solid
	 * Sets image and visibility of projectiles
	 * Sets the state of projectiles
	 * Add the projectiles to the current state of engine
	 */
	private void shoot(double yPos){
		//Create a  projectile
		Entity projectile = new Entity(attachedTo.getXPos(), yPos, 50, 50, 1, 1, 1);
		projectile.setSolid(true);
		
		//Attach sprite to projectile
		projectile.setSprite(Directory.spriteLibrary.get("Bullet_Yellow"));
		
		//Set the shape
		//projectile.setShape(new Ellipse2D.Double(), Color.black);
		
		
		//Set visible
		projectile.setVisible(true);
		//Set the state
		projectile.setState(new ProjectileState(Directory.profile.getPlayer(), attachedTo, EnemyScatterShotState.shotSpeed));
		//Add projectile to current engine state
		Directory.engine.getCurrentState().addObj(projectile);
	}

	/**
	 * Exits the enemies battle state
	 * Sets position back to worldPos,
	 * Reverts back to overworld dimensions,
	 * Updates the objects shape
	 */
	@Override
	public void exit() {
		super.exit();
		

	}

}
