package Objects.ObjStates.MObjStates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import Engine.Directory;
import MathHelp.Vector;
import Objects.Entity;
import Objects.MovableGameObject;
import Objects.ObjStates.ObjState;
import Objects.Sprites.Sprite;

/**
 * Defines behavior which will allow the player to move in a top-down manner
 * using wasd
 * @author Nex
 *
 */
public class PlayerOverworldState extends MObjState{

	//Attributes
	double movementSpeed;
	
	/**
	 * Constructs a playerOverworldState
	 * 
	 * Sets the movementSpeed.
	 */
	public PlayerOverworldState() {
		super();
		
		//Set movementSpeed
		movementSpeed = 0.0001;
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
		
		//Create sprite for overworld
		//int[] numColumns = new int[1];
		//numColumns[0] = 5;
		
		//Sprite s = new Sprite(Directory.imageLibrary.get("SpriteSheetTest"), 1, numColumns, 193, 198);
		
		
		
		//Set the sprite to null (until image for overworld state is made)
		//attachedTo.setImage(null);
		//Now sets the sprite to a test spritesheet
		attachedTo.setSprite(Directory.spriteLibrary.get("SpriteSheetTest"));
		
		//Queue up an animation of row 0 in spritesheet to repeat
		attachedTo.getSprite().queueAnimation(0, true);
		
		
	}

	/**
	 * Updates the player's overworld state
	 * If a button (WASD) is being pressed, creates a proper translation vector
	 * Calls MovableGameObject's move method, sending it the translationVector
	 * 		This call sets position, previous position, and updates the shape.
	 */
	@Override
	public void update() {
		//Create a translation vector
		Vector translation = new Vector(2);

		
		//Determine which keys are pressed
		if(Directory.inputManager.isKeyPressed('w')){
			//Set translation Vector to move up
			translation.setComponent(1, translation.getComponent(1)-movementSpeed);
		}
		if(Directory.inputManager.isKeyPressed('s')){
			//Set translation vector to move down
			translation.setComponent(1, translation.getComponent(1) + movementSpeed);
		}
		if(Directory.inputManager.isKeyPressed('a')){
			//Set translation Vector to move left
			translation.setComponent(0, translation.getComponent(0)-movementSpeed);
		}
		if(Directory.inputManager.isKeyPressed('d')){
			//Set translation Vector to move right
			translation.setComponent(0, translation.getComponent(0)+movementSpeed);
		}
		
		//Move this gameObject
		getAttachedMObj().move(translation);
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
