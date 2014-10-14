package Objects.ObjStates.MObjStates;

import Objects.MovableGameObject;
import Objects.ObjStates.ObjState;

/**
 * Defines any Object state which must be attached to an object which
 * is a movableGameObject
 * @author Nex
 *
 */
public abstract class MObjState extends ObjState{

	/**
	 * Constructs a movable object state
	 */
	public MObjState() {
		super();
	}
	
	/**
	 * Returns the attached game object casted to movable game object
	 * @return Attached game object (atachedTo) casted to movableGameObject
	 */
	protected MovableGameObject getAttachedMObj(){
		return (MovableGameObject)attachedTo;
	}

}
