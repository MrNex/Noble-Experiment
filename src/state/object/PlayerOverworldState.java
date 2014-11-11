package state.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import engine.Directory;
import mathematics.Vector;
import objects.Entity;
import objects.MovableGameObject;
import sprites.Sprite;
import state.object.movable.MovableObjectState;

/**
 * Defines behavior which will allow the player to move in a top-down manner
 * using wasd
 * @author Nex
 *
 */
public class PlayerOverworldState extends MovableObjectState{

	//Attributes
	private double movementSpeed;
	private double previousTime;
	
	/**
	 * Constructs a playerOverworldState
	 * 
	 * Sets the movementSpeed.
	 */
	public PlayerOverworldState() {
		super();
		
		
	}
	
	/**
	 * Initializes all internal member variables
	 */
	@Override
	protected void init() {
		//Set movementSpeed in px/s
		movementSpeed = 100;
		//Set previousTime to start at the 
	}

	/**
	 * Is called upon an object entering PlayerOverworldState
	 * 
	 * Sets overworld dimensions to 20, 20
	 * Sets shape as a green ellipse and updates it
	 * Sets test animation as image (until real animation is put in)
	 * Queues up walking animation to repeat
	 * removes image from object
	 */
	@Override
	public void enter() {
		//Update dimensions
		attachedTo.setWidth(100);
		attachedTo.setHeight(100);
		
		//set image of player to overworld image
		attachedTo.setShape(new Ellipse2D.Double(), Color.green);
		attachedTo.updateShape();
		
		
		//Set the sprite to testspritesheet (until image for overworld state is made)
		//Now sets the sprite to a test spritesheet
		attachedTo.setSprite(Directory.spriteLibrary.get("SpriteSheetTest"));
		
		//Queue up an animation of row 0 in spritesheet to repeat
		attachedTo.getSprite().queueAnimation(0, true);
		
		//Start timing movement by getting previous time
		previousTime = System.currentTimeMillis();
	}

	/**
	 * Updates the player's overworld state
	 * Calculates elapsed time since last update and scales movement speed by time passed
	 * If a button (WASD) is being pressed, creates a proper translation vector using scaled movement speed
	 * Calls MovableGameObject's move method, sending it the translationVector
	 * 		This call sets position, previous position, and updates the shape.
	 */
	@Override
	public void update() {

		//Get current time
		double currentTime = System.currentTimeMillis();
		double movement = ((currentTime - previousTime) * movementSpeed) / 1000.0;
		
		//Create a translation vector
		Vector translation = new Vector(2);

		
		//Determine which keys are pressed
		if(Directory.inputManager.isKeyPressed('w')){
			//Set translation Vector to move up
			translation.setComponent(1, translation.getComponent(1)-movement);
		}
		if(Directory.inputManager.isKeyPressed('s')){
			//Set translation vector to move down
			translation.setComponent(1, translation.getComponent(1) + movement);
		}
		if(Directory.inputManager.isKeyPressed('a')){
			//Set translation Vector to move left
			translation.setComponent(0, translation.getComponent(0)-movement);
		}
		if(Directory.inputManager.isKeyPressed('d')){
			//Set translation Vector to move right
			translation.setComponent(0, translation.getComponent(0)+movement);
		}

		
		//Move this gameObject
		getAttachedMObj().move(translation);
		
		//Update previous time
		previousTime = currentTime;
			
	}

	/**
	 * Exits an object from PlayerOverworldState
	 */
	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * No drawing effects occur specifically for PlayerOverworldState
	 */
	@Override
	public void draw(Graphics2D g2d) {
		
	}

}
