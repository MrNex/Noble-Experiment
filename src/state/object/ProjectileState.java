package state.object;


import engine.Directory;
import mathematics.Vector;
import objects.*;
import triggers.Trigger;

/**
 * Projectile state defines behavior which will cause a gameObject to travel towards a target
 * at the defined speed and deal damage upon collision
 * @author Nex
 *
 */
public class ProjectileState extends TargetableState{

	//Attributes
	//TODO: Find way to make this slower!
	private double speed;		//speed in px/s
	private Vector heading, initialPosition;
	private double time, distance, previousTime;
	private Entity target;
	private GameObject shotBy;
	private long timeStart;
	
	/**
	 * Constructs a projectile state
	 * @param x The entity being targetted that this projectile should seek
	 */
	public ProjectileState(Entity target, GameObject shooter, double shootSpeed) {
		super(true);
		//Set member variables
		this.target = target;
		shotBy = shooter;
		speed = shootSpeed;
	
	}
	
	@Override
	public void init(){
		super.init();
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
		
		//Set previous time to construction time to start
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
		this.getAttachedEntity().move(Vector.setMag(heading, speed * (elapsedTimeSince)));

		previousTime = currentTime;
		
		//Update the objects shape
		attachedTo.updateShape();
	}

	/**
	 * Takes the attachedObject out of projectileState.
	 * A projectile should never need to do this, as it will just be removed from game.
	 */
	@Override
	public void exit() {
		
	}
	
	/**
	 * Returns true if the object is not the object that shot this projectile
	 * @param Object to check with
	 */
	@Override
	public boolean isColliding(GameObject obj){
		return obj != shotBy && !(obj.getState() instanceof ProjectileState);
	}

}
