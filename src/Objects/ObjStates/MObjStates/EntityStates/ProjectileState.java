package Objects.ObjStates.MObjStates.EntityStates;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import Engine.Directory;
import MathHelp.Vector;
import Objects.*;

/**
 * Projectile state defines behavior which will cause a gameObject to travel towards a target
 * at the defined speed and deal damage upon collision
 * @author Nex
 *
 */
public class ProjectileState extends TargetableState{

	//Attributes
	//TODO: Find way to make this slower!
	static double speed = .05;		//speed in px/s
	Vector heading, initialPosition;
	double time, distance;
	Entity target;
	long timeStart;
	
	/**
	 * Constructs a projectile state
	 * @param x The entity being targetted that this projectile should seek
	 */
	public ProjectileState(Entity x) {
		super(true);
		//X marks the spot
		target = x;
	
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
		
		//Determine the amount of miliseconds until this projectile hits it's target
		//Find distance from this obj to target
		//Find vector from this obj to target
		Vector direction = new Vector(target.getPos());
		//direction.setComponent(1, target.getYPos()+100);
		direction.subtract(attachedTo.getPos());
		
		//Find the magnitude of this vector and store as distance.
		distance = direction.getMag();
		
		//Find the time it will take to reach target
		//v = x/t, vt = x, x/v = t
		time = (distance / speed) / 1000;
		System.out.println(time);
		
		
		
		//Normalize direction vector and set as heading
		direction.normalize();
		heading = direction;
		
		//Set initial Position
		initialPosition = attachedTo.getPos();
		
		timeStart = System.currentTimeMillis();
		
		
		
		//Start the timer
		//timer.start();
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
		double elapsedTime = (System.currentTimeMillis() - timeStart) / 1000.0;
		
		//Set position to initialPosition + heading * distance(elapsedTime/time) to get current Position
		attachedTo.setPos(Vector.add(initialPosition, Vector.setMag(heading, distance * (elapsedTime/time))));
		
		if(attachedTo.isColliding(target))
		{
			target.decrementCurrentHealth(((Entity)attachedTo).getPower());
			
			System.out.println("Hurt");
			
			Directory.engine.getCurrentState().removeObj(attachedTo);
			attachedTo.setRunning(false);
			attachedTo.setVisible(false);
		}
		
		//Update the objects shape
		attachedTo.updateShape();
	}

	/**
	 * Takes the attachedObject out of projectileState.
	 */
	@Override
	public void exit() {
		
	}

}
