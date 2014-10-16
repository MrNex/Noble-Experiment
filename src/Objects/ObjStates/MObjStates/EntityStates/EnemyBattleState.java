package Objects.ObjStates.MObjStates.EntityStates;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;

import javax.swing.Timer;

import Engine.Directory;
import MathHelp.Vector;
import Objects.Entity;

public class EnemyBattleState extends TargetableState{

	//Attributes
	private Vector worldPos;
	private static int attackSpeed = 5000;				//Set all enemies attack speeds to 10 seconds to start
	private Timer attackTimer;

	/**
	 * Constructs an Enemies battle state
	 * Creates an attack timer which will
	 */
	public EnemyBattleState() {
		super(false);
		
		//Create attackTimer
		attackTimer = new Timer(attackSpeed, new ActionListener(){

			//When the timer is finished
			@Override
			public void actionPerformed(ActionEvent e) {
				//Create a destructable projectile
				Entity projectile = new Entity(attachedTo.getXPos(), attachedTo.getYPos(), 50, 50, 1, 1, 1);
				// Set image
				projectile.setImage(Directory.imageLibrary.get("Bullet_Yellow"));
				//Set the shape
				//projectile.setShape(new Ellipse2D.Double(), Color.black);
				//Set visible
				projectile.setVisible(true);
				//Set the state
				projectile.setState(new ProjectileState(Directory.profile.getPlayer()));
				//SEt projectile as running
				projectile.setRunning(true);
				//Add projectile to current engine state
				Directory.engine.getCurrentState().addObj(projectile);
			}
		});

		//SEt the timer to repeat
		attackTimer.setRepeats(true);
	}

	/**
	 * Enters & Prepares the battlestate
	 * Stores the worldposition and re-positions entity for battle
	 * Sets width and Height for battle state view
	 * Updates the shape of the object
	 * Sets the equation visibility to true
	 * Begins attacking
	 */
	@Override
	public void enter() {
		super.enter();
		
		//Store worldPos
		worldPos = new Vector(attachedTo.getPos());

		//Set position
		Vector posVector = new Vector(2);
		posVector.setComponent(0, Directory.screenManager.getPercentageWidth(85.0));
		posVector.setComponent(1, Directory.screenManager.getPercentageHeight(45.0));
		attachedTo.setPos(posVector);

		//Set battleState dimensions
		attachedTo.setWidth(75);
		attachedTo.setHeight(300);

		attachedTo.updateShape();

		//Upon entering this state, start the timer
		attackTimer.start();

	}

	/**
	 * Updates the enemiesBattleState
	 * Checks if the player is dead yet, if he/she is, ends the battle
	 */
	@Override
	public void update() {
		super.update();

	}

	/**
	 * Exits the enemies battle state
	 * Stops atacking,
	 * Sets equation to invisible,
	 * Sets position back to worldPos,
	 * Reverts back to overworld dimensions,
	 * Updates the objects shape
	 */
	@Override
	public void exit() {
		//Upon exiting this state stop attackTimer
		attackTimer.stop();
		
		//Turn equation visibility off
		//((Entity)attachedTo).setEquationVisibility(false);

		//Revert back to overworld pos
		attachedTo.setPos(worldPos);

		//Revert back to original overworld dimensions
		attachedTo.setWidth(25);
		attachedTo.setHeight(25);
		attachedTo.updateShape();

	}

}
