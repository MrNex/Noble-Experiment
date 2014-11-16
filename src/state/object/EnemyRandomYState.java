package state.object;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.util.Random;

import javax.swing.Timer;

import engine.Directory;
import mathematics.Vector;
import objects.Entity;
import sprites.Sprite;

/**
 * Defines basic test enemy behavior during battleState
 * @author Nex
 *
 */
public class EnemyRandomYState extends TargetableState{

	//Static attributes
	private static int attackSpeed = 3;				//Set all basic enemys attack speeds to 5 seconds to start
	private static double shotSpeed = 75;			//Set all basic enemys projectile speed to 50
	
	//Attributes
	private Vector worldPos;
	private Timer attackTimer;
	private double previousTime;
	private double elapsedTime;

	/**
	 * Constructs an Enemies battle state
	 * 
	 */
	public EnemyRandomYState() {
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
		posVector.setComponent(0, Directory.screenManager.getPercentageWidth(80.0));
		posVector.setComponent(1, Directory.screenManager.getPercentageHeight(45.0));
		
		getAttachedEntity().setPos(posVector);
		
		//Refresh attached movable game object's previous position due to engine state change.
		getAttachedEntity().refresh();

		//Set battleState dimensions
		attachedTo.setWidth(150);
		attachedTo.setHeight(200);

		attachedTo.updateShape();
		
		//Set players sprite
		getAttachedEntity().setSprite(Directory.spriteLibrary.get("Lich"));
		
		//Run animation
		attachedTo.getSprite().queueAnimation(0, true);

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
		
		if(elapsedTime > attackSpeed){
			elapsedTime = 0;
			shoot();
		}
		
		previousTime = currentTime;

	}
	
	/**
	 * Creates a new projectile and adds the projectile to the current state:
	 * Creates projectile and sets as solid
	 * Sets image and visibility of projectile
	 * Sets the state of projectile
	 * Add the projectile to the current state of engine
	 */
	private void shoot(){
		//Get random yPos
		Random rn = new Random();
		double randYPos = 150 * rn.nextDouble();
		randYPos += attachedTo.getYPos() - 50;
		
		//Create a  projectile
		Entity projectile = new Entity(attachedTo.getXPos(), randYPos, 50, 50, 1, 1, 1);
		projectile.setSolid(true);
		
		//Attach sprite to projectile
		projectile.setSprite(Directory.spriteLibrary.get("Bullet_Yellow"));
		
		//Set the shape
		//projectile.setShape(new Ellipse2D.Double(), Color.black);
		
		
		//Set visible
		projectile.setVisible(true);
		//Set the state
		projectile.setState(new ProjectileState(Directory.profile.getPlayer(), attachedTo, EnemyRandomYState.shotSpeed));
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
