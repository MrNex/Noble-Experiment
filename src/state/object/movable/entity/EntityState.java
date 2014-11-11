package state.object.movable.entity;

import objects.Entity;
import state.object.movable.MovableObjectState;

/**
 * Defines any MObjState which must be attached to an entity.
 * @author Nex
 *
 */
public abstract class EntityState extends MovableObjectState{

	/**
	 * Constructs an entity state
	 */
	public EntityState() {
		super();
	}
	
	/**
	 * Gets the attached game object as an entity
	 * @return The attached game object (attachedTo) cast to an entity
	 */
	protected Entity getAttachedEntity(){
		return (Entity)attachedTo;
	}

}
