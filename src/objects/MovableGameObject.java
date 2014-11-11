package objects;

import mathematics.Vector;

/**
 * Movable game object defines a game object which moves.
 * It implements a move method, which increments the current position by a movement vector and logs the previous position.
 * Movable game objects keep track of the last position they were in.
 * @author Nex
 *
 */
public class MovableGameObject extends GameObject {

	protected Vector previousPosition;
	
	/**
	 * Constructs a movable game object
	 * @param xx X position
	 * @param yy Y Postion
	 * @param w Width
	 * @param h Height
	 */
	public MovableGameObject(double xx, double yy, double w, double h) {
		super(xx, yy, w, h);
		
		previousPosition = new Vector(position);

	}
	
	/**
	 * Moves (increments) this gameObject's position vector by the movementVector
	 * And logs the previous position
	 * @param movementVector Vector to increment current position by
	 */
	public void move(Vector movementVector){
		//Set previous position
		previousPosition.copy(position);
		//Increment current position
		position.add(movementVector);
		
		//Update the shape
		updateShape();
	}
	
	/**
	 * Sets the current position back to the previous position
	 */
	public void revert(){
		position.copy(previousPosition);
		updateShape();
	}
	
	/**
	 * Sets the object's previousPosition to the object's current position
	 */
	public void refresh(){
		previousPosition.copy(position);
	}
	


}
