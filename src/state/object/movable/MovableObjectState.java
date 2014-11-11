package state.object.movable;

import objects.MovableGameObject;
import state.object.ObjectState;

/**
 * Defines any Object state which must be attached to an object which
 * is a movableGameObject
 * @author Nex
 *
 */
public abstract class MovableObjectState extends ObjectState{

	/**
	 * Constructs a movable object state
	 */
	public MovableObjectState() {
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
