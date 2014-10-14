package Objects.ObjStates.MObjStates.EntityStates;

import Objects.Entity;
import Objects.ObjStates.ObjState;
import Objects.ObjStates.MObjStates.MObjState;

/**
 * Defines any MObjState which must be attached to an entity.
 * @author Nex
 *
 */
public abstract class EntityState extends MObjState{

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
