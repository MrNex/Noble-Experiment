package Objects.ObjStates;

import java.awt.Graphics2D;

import Objects.GameObject;

/**
 * Defines A state attachable to every game object
 * @author Nex
 *
 */
public abstract class ObjState{

	//Attributes
	protected GameObject attachedTo;
	
	/**
	 * Constructs an ObjState
	 */
	public ObjState() {
		
	}
	
	/**
	 * This method is called upon this state reaching the top of a stateStack
	 */
	abstract public void enter();
	
	/**
	 * This method updates the state every gameLoop
	 */
	abstract public void update();
	
	/**
	 * This method makes any state-specific draw calls. This will only be called if the
	 * attached gameObject is visible AND running!
	 * @param g2d A reference to the graphics renderer with which to render this draw call.
	 */
	abstract public void draw(Graphics2D g2d);
	
	/**
	 * This method is called upon detaching this state from a gameObject.
	 * You detach a state from a gameObject by calling setState on the attached gameObject and passing it any state.
	 * The already attached state will be removed and have it's exit method called
	 */
	abstract public void exit();
	
	/**
	 * Sets the attached gameObject to this state. This method should never be called directly.
	 * In order to set the attached gameObject just call setState on any GameObject and pass this state to it.
	 * The gameObject itself will call setAttachedGameObject passing itself as the parameters.
	 * @param attachTo The gameObject being attached to this state.
	 */
	public void setAttachedGameObject(GameObject attachTo){
		attachedTo = attachTo;
	}
	
	/**
	 * States have an isColliding method, allowing states to modify which objects
	 * will register as colliding.
	 * By default, all object states return true unless changed.
	 * @param obj Object to check with
	 */
	public boolean isColliding(GameObject obj){
		return true;
	}
}
