package Objects.ObjStates;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

import Engine.Directory;
import MathHelp.Vector;
import Objects.Entity;
import Objects.MovableGameObject;

/**
 * Defines behavior which will allow the player to move in a top-down manner
 * using wasd
 * @author Nex
 *
 */
public class PlayerOverworldState extends ObjState{

	//Attributes
	double movementSpeed;
	
	public PlayerOverworldState() {
		super();
		
		//Set movementSpeed
		movementSpeed = 0.0001;
	}

	@Override
	public void enter() {
		//Update dimensions
		attachedTo.setWidth(20);
		attachedTo.setHeight(20);
		
		//set image ofplayer to overworld image
		attachedTo.setShape(new Ellipse2D.Double(), Color.green);
		attachedTo.updateShape();
		
		//Set the image to null (until image for overworld state is made)
		attachedTo.setImage(null);
		
		((Entity)attachedTo).setEquationVisibility(false);
		
	}

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
		((MovableGameObject)attachedTo).move(translation);
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

}
