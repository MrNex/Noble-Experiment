package Objects.ObjStates.MObjStates.EntityStates;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import Engine.Directory;
import MathHelp.Vector;
import Objects.*;
import Objects.Triggers.Trigger;

/**
 * Projectile state defines behavior which will cause a gameObject to travel towards a target
 * at the defined speed and deal damage upon collision
 * @author Nex
 *
 */
public class ProjectileState extends TargetableState{

	//Attributes
	//TODO: Find way to make this slower!
	static double speed = 50;		//speed in px/s
	Vector heading, initialPosition;
	double time, distance, previousTime;
	Entity target;
	GameObject shotBy;
	long timeStart;
	
	/**
	 * Constructs a projectile state
	 * @param x The entity being targetted that this projectile should seek
	 */
	public ProjectileState(Entity target, GameObject shooter) {
		super(true);
		//Set member variables
		this.target = target;
		shotBy = shooter;
	
	}

	/**
	 * Prepares the attached gameObject for the projectile state:
	 * Determines a direction vector toward the target,
	 * Determines a time in seconds it will take to reach target
	 * And sets this target to running if it isn't already
	 */
	@Override
	public void enter() {
		super.enter();
		
		//Attach trigger to damage target when hit
		attachedTo.setTriggerable(true);
		attachedTo.addTrigger(new Trigger(){

			/**
			 * If the projectile collides with its target, 
			 * Decrement the targets health by the attached gameObject's power
			 * Remove the attached gameObject from the current state
			 * Set the attached gameobject's state to null
			 * Set the attached gameObject's visibility to false.
			 */
			@Override
			public void action(GameObject triggeredBy) {
				if(triggeredBy == target){
					target.decrementCurrentHealth(((Entity)attachedTo).getPower());
					Directory.engine.getCurrentState().removeObj(attachedTo);	
					attachedTo.setState(null);	
					attachedTo.setVisible(false);
					attachedTo.removeTrigger(this);
					attachedTo.setTriggerable(false);
					
				}
			}
			
		});
		
		//Determine the amount of miliseconds until this projectile hits it's target
		//Find vector from this obj to target
		Vector direction = new Vector(target.getPos());
		direction.subtract(attachedTo.getPos());
		
		//Normalize direction vector and set as heading
		direction.normalize();
		heading = direction;
		
		//Set initial Position
		//initialPosition = attachedTo.getPos();
		
		//timeStart = System.currentTimeMillis();
		previousTime = System.currentTimeMillis();
	}

	/**
	 * Updates this projectile state, incrementing the attached objects position
	 * by the appropriate amount based on the elapsed time of arrival and the time passed since firing.
	 * And checks if the projectile is colliding with the target.
	 * If it is, the target will take damage and the projectile is removed from the current state.
	 */
	@Override
	public void update() {
		super.update();
		//iP + tM = fP
		
		//GEt elapsed time since "firing" in seconds
		double currentTime = System.currentTimeMillis();
		double elapsedTimeSince = (currentTime - previousTime) / 1000.0;
		
		//Set position to initialPosition + heading * distance(elapsedTime/time) to get current Position
		//attachedTo.setPos(Vector.add(initialPosition, Vector.setMag(heading, distance * (elapsedTime/time))));
		this.getAttachedMObj().move(Vector.setMag(heading, speed * (elapsedTimeSince)));

		previousTime = currentTime;
		
		//Update the objects shape
		attachedTo.updateShape();
	}

	/**
	 * Takes the attachedObject out of projectileState.
	 */
	@Override
	public void exit() {
		//Remove trigger
	}
	
	/**
	 * Returns true if the object is not the object that shot this projectile
	 * @param Object to check with
	 */
	@Override
	public boolean isColliding(GameObject obj){
		return obj != shotBy;
	}

}
