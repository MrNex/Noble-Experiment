package Objects;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RectangularShape;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

import MathHelp.Vector;
import Objects.ObjStates.ObjState;
import Objects.Sprites.Sprite;
import Objects.Triggers.Trigger;

/**
 * Defines any object in the game
 * @author Nex
 *
 */
public class GameObject {

	//Attributes
	protected Vector position;
	protected double width, height;
	protected boolean visible/*, running*/;
	protected boolean triggerable, solid;
	protected RectangularShape shape;
	//protected BufferedImage image;
	protected Sprite image;
	protected Color color;
	protected Stack<ObjState> stateStack;
	protected ArrayList<Trigger> triggers;
	

	/**
	 * Creates a basic GameObject with a position and size
	 * GameObject defaults to not running and not visible with a null state
	 * @param xx X Position in worldspace
	 * @param yy Y Position in worldspace
	 * @param w object width
	 * @param h object height
	 */
	public GameObject(double xx, double yy, double w, double h) {
		//Set designated attributes
		position = new Vector(2);
		position.setComponent(0, xx);
		position.setComponent(1, yy);

		width = w;
		height = h;

		//Set default attributes
		visible = false;
		//running = false;
		
		stateStack = new Stack<ObjState>();

		shape = null;
		color = Color.black;
		
		image = null;
	}

	//Accessors
	/**
	 * Gets the position vector
	 * @return the position vector
	 */
	public Vector getPos(){
		return position;
	}

	/**
	 * Sets the position vector
	 * @param v New position vector
	 */
	public void setPos(Vector v){
		position = v;
	}

	/**
	 * Gets the X component of the position vector
	 * @return
	 */
	public double getXPos(){
		return position.getComponent(0);
	}

	/**
	 * Gets the Y component of the position {@link mathematics.Vec}
	 * @return the Y component of position {@link mathematics.Vec}
	 */
	public double getYPos(){
		return position.getComponent(1);
	}
	
	/**
	 * GEts the width of this gameObject's image/shape/bounding box
	 * @return The width of this gameObject's image/shape/bounding box
	 */
	public double getWidth(){
		return width;
	}
	
	/**
	 * Gets the height of this gameObject's image/shape/bounding box
	 * @return The height of this gameObject's image/shape/bounding box
	 */
	public double getHeight(){
		return height;
	}
	
	/**
	 * Sets the width of this gameobject's image/shape/bounding box
	 * @param newWidth Width to set
	 */
	public void setWidth(int newWidth){
		width = newWidth;
	}
	
	/**
	 * Sets the height of this gameObject's image/shape/bounding box
	 * @param newHeight Height to set
	 */
	public void setHeight(int newHeight){
		height = newHeight;
	}
	
	/**
	 * Gets a position vector representing the center of this object
	 * @return A vector holding the coordinates of the exact center of this object
	 */
	public Vector getCenter(){
		Vector centerVec = new Vector(2);
		centerVec.copy(position);
		centerVec.incrementComponent(0, width/2.0);
		centerVec.incrementComponent(1, height/2.0);
		return centerVec;
	}

	/**
	 * Sets the current of an {@link GameObject}.
	 * Replaces the state currently on top of the state stack.
	 * IF a state is being replaced, that states exit method is called.
	 * If the state replacing it is not null, that states entermethod is called
	 * If the new state is not null, the object is set to running
	 * Else, the object is set to not running.
	 * @param newState State to attach to object
	 */
	public void setState(ObjState newState){
		//If not leaving a null state
		if(getState() != null)
			popState().exit();
		
		stateStack.push(newState);
		
		//If not going into a null state
		if(getState() != null){
			getState().setAttachedGameObject(this);
			getState().enter();
		}
	}
	
	/**
	 * Gets this gameObjects current state
	 * @return The state currently attached to this gameObject
	 */
	public ObjState getState(){
		if(stateStack.size() > 0)
			return stateStack.peek();
		return null;
	}
	
	/**
	 * Pushes a state to the top of state stack to set as current state
	 * If there is already a state on the stack, it's exit method is called.
	 * If the new state being pushed is not null, its enter method is called
	 * 
	 * If the new state is not null, and this object isn't already running, running is set to true
	 * IF the new state is null and this object is running, running is set to false
	 * 
	 * @param stateToPush The new current state
	 */
	public void pushState(ObjState stateToPush){
		if(getState() != null) getState().exit();
		stateStack.push(stateToPush);
		if(getState() != null){
			getState().setAttachedGameObject(this);
			getState().enter();
			
		}
	}
	
	/**
	 * Removes the current state from the state stack and returns it
	 * If there is a non-null state being removed it's exit method is called
	 * If there is an underlying non-null state it's enter method is called
	 * 
	 * If there is an underlying non-null state and this object is not running, it is set to running
	 * If there is not an underlying non-null state and this object is already running, it is set to not running
	 * 
	 * @return The (now) former state
	 */
	public ObjState popState(){
		if(getState() != null) getState().exit();
		ObjState returnState = stateStack.pop();
		
		if(getState() != null){
			getState().enter();
		}
		
		return returnState;
	}

	/**
	 * Gets the visibility of this gameobject
	 * @return Whether or not the object is visible
	 */
	public boolean isVisible(){
		return visible;
	}
	
	/**
	 * Sets the visibility of this gameObject
	 * @param isVisible is the object visible
	 */
	public void setVisible(boolean isVisible){
		visible = isVisible;
	}

	/**
	 * Gets whethe or not this gameobject is running.
	 * @return Whether or not the gameobject has a non-null state on top of its statestack.
	 */
	public boolean isRunning(){
		return getState() != null;
	}
	
	/**
	 * Gets whether this gameObject has an active trigger
	 * @return whether this object has an active trigger
	 */
	public boolean isTriggerable(){
		return triggerable;
	}
	
	/**
	 * Sets whether this object is triggerable or not.
	 * IF set to true the arrayList of triggers is initialized.
	 * This will clear the list of triggers.
	 * @param isTriggerable Whether or not this object should be triggerable
	 */
	public void setTriggerable(boolean isTriggerable){
		triggerable = isTriggerable;
		//If this object is triggerable, initialize its list of triggers
		if(triggerable){
			triggers = new ArrayList<Trigger>();
		}
	}
	
	/**
	 * Adds a trigger to this gameObject
	 * @param triggerToAdd The trigger being added
	 */
	public void addTrigger(Trigger triggerToAdd){
		triggers.add(triggerToAdd);
		triggerToAdd.setAttachedObj(this);
	}
	
	/**
	 * Removes a trigger from this gameObject
	 * @param triggerToRemove the trigger being removed
	 */
	public void removeTrigger(Trigger triggerToRemove){
		triggers.remove(triggerToRemove);
	}
	
	/**
	 * Gets the list of triggers attached to this object
	 * @return An arrayList of all triggers attached to this object
	 */
	public ArrayList<Trigger> getTriggers(){
		return new ArrayList<Trigger>(triggers);
	}
	
	/**
	 * Sets whether an object is solid
	 * A solid object cannot be passed through
	 * @return The solidarity of the object
	 */
	public boolean isSolid(){
		return solid;
	}
	
	/**
	 * Sets whether an object is solid.
	 * Solid objects cannot be passed through
	 * @param isSolid Is this gameObject solid?
	 */
	public void setSolid(boolean isSolid){
		solid = isSolid;
	}

	/**
	 * Sets the shape of this gameobject
	 * If the shape is not being set to null, the shape will be updated to fit the
	 * current dimensions and location of this gameObject
	 * @param newShape The new shape this gameObject should have
	 */
	public void setShape(RectangularShape newShape){
		//Set the shape
		shape = newShape;
		//if its not null, update it to my position
		if(shape != null){
			updateShape();
		}
	}
	
	/**
	 * Gets the sprite of this GameObject.
	 */
	public Sprite getSprite(){
		return image;
	}
	
	/**
	 * Set the image of this gameObject
	 * IF both an image and a shape are set, the image will be drawn
	 * @param newImage The new image of this gameObject
	 */
	public void setImage(Sprite newImage){
		image = newImage;
	}

	/**
	 * Sets the color of the GameObject
	 * @param newColor The new color
	 */
	public void setColor(Color newColor){
		color = newColor;
	}

	/**
	 * Sets the shape and color of the gameobject
	 * @param newShape The new shape
	 * @param color The new color
	 */
	public void setShape(RectangularShape newShape, Color color)
	{
		setShape(newShape);
		setColor(color);
	}

	/**
	 * Updates the current state of the gameObject if this object is running
	 */
	public void update(){
		if(isRunning()){
			getState().update();
		}
		if(isVisible()){
			if(image != null){
				image.update();
			}
		}
	}

	/**
	 * Draws the image (Or shape if image is null) representing this gameObject.
	 * If this object has a running state, it will draw the state as well.
	 * 
	 * If the gameobject is visible AND running, the currentState's draw method will also be called
	 * @param g2d reference to renderer to draw
	 */
	public void draw(Graphics2D g2d){
		if(visible)
		{
			//If they have an image
			if(image != null){
				/*
				g2d.drawImage(image,
						(int)position.getComponent(0), (int)position.getComponent(1), 
						(int)(position.getComponent(0) + width), (int)(position.getComponent(1) + height), 
						0, 0, image.getWidth(), image.getHeight(), null);*/
				image.draw(g2d, (int)position.getComponent(0), (int)position.getComponent(1), (int)width, (int)height);
				//System.out.println(width + " " + height);
			}
			else{
				//Set the color
				g2d.setColor(color);
				//Fill the shape
				g2d.fill(shape);
			}
			//IF this object is running
			if(isRunning()){
				//Draw the currentState as well
				getState().draw(g2d);
			}
		}
	}

	/**
	 * Updates the shape to the current object position,
	 * And the current object dimensions.
	 */
	public void updateShape(){
		if(shape != null)
			shape.setFrame(position.getComponent(0), position.getComponent(1), width, height);
	}

	/**
	 * Checks if the bounding box of this obj is intersecting the bounding box of another obj
	 * If they are, also queries state's isColliding method.
	 * If there is not a current state and objects are colliding, returns true.
	 * If there is a current state and objects are colliding, returns the result of the current states isColliding method
	 * If objects are not colliding returns false
	 * @param obj Object to test for collisions with
	 * @return False if objects are not colliding , true if objects are colliding & state says objects are colliding.
	 */
	public boolean isColliding(GameObject obj){

		//If the left side of this is to the left  right side of obj and the right side of this is to the right of the left side of obj
		if(position.getComponent(0) < obj.position.getComponent(0) + obj.width && this.position.getComponent(0) + this.width > obj.position.getComponent(0)){

			//IF the top of this is higher than the bottom of obj and the bottom of this is further down than the top of obj
			if(position.getComponent(1) < obj.position.getComponent(1) + obj.height && this.position.getComponent(1) + this.height > obj.position.getComponent(1)){
				return isRunning() ? getState().isColliding(obj) : true;
			}	
		}
		return false;
	}

	/**
	 * Checks if the bounding box of this obj is intersecting the bounding box of another obj
	 * @param obj GameObject to check with
	 * @return Whether this gameobject is intersecting with obj
	 */
	public boolean contains(double xx, double yy){
		return 
				xx < position.getComponent(0) + width && 
				xx > position.getComponent(0) && 
				yy < position.getComponent(1) + height && 
				yy > position.getComponent(1); 
	}

}
