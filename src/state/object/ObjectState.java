package state.object;

import java.awt.Graphics2D;

import objects.GameObject;
import state.State;

/**
 * Defines A state attachable to every game object
 * @author Nex
 *
 */
public abstract class ObjectState extends State {


	//Attributes
	protected GameObject attachedTo;

	//Accessors / Modifiers
	/**
	 * Gets the gameObject which this state is currently attached to
	 * @return The gameobject attached to this state
	 */
	public GameObject getAttachedGameObject(){
		return attachedTo;
	}
	
	
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
	 * Constructs an ObjState
	 */
	public ObjectState() {
		super();
	}

	/**
	 * Initializes all members of state
	 */
	@Override
	abstract protected void init();

	/**
	 * This method is called upon this state being pushed onto an object's stateStack
	 */
	@Override
	abstract public void enter();

	/**
	 * This method updates the state every gameLoop
	 */
	@Override
	abstract public void update();

	/**
	 * This method makes any state-specific draw calls. This will only be called if the
	 * attached gameObject is visible AND this state is at the top of an object's state stack
	 * @param g2d A reference to the graphics renderer with which to render this draw call.
	 */
	@Override
	abstract public void draw(Graphics2D g2d);

	
	/**
	 * This method is called upon this state being removed from a gameObject's state stack.
	 */
	@Override
	abstract public void exit();

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
