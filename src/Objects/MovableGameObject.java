package Objects;

import MathHelp.Vector;

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

	}
	
	/**
	 * Moves (increments) this gameObject's position vector by the movementVector
	 * And logs the previous position
	 * @param movementVector Vector to increment current position by
	 */
	public void move(Vector movementVector){
		//Set previous position
		previousPosition = position;
		//Increment current position
		position.add(movementVector);
		
		//Update the shape
		updateShape();
	}
	
	/**
	 * Sets the current position back to the previous position
	 */
	public void revert(){
		position = previousPosition;
	}
	


}
