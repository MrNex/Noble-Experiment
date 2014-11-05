package state.object;

import java.awt.Graphics2D;
import java.awt.geom.RectangularShape;

import MathHelp.Vector;
import Objects.Sprites.Sprite;

/**
 * Nested state describes a object state which preserves the
 * data of an object and restore it on exit
 * @author Nex
 *
 */
abstract public class NestedState extends ObjectState {

	//Attributes
	private Sprite preservedSprite;
	private RectangularShape preservedShape;
	private Vector preservedPosition;
	private double preservedWidth;
	private double preservedHeight;
	
	public NestedState() {
		super();
		
	}



	/**
	 * On entering this state get and store
	 * the current attached object's information
	 */
	@Override
	public void enter() {
		//Get the position
		preservedPosition = attachedTo.getPos();
		//Get the dimensions
		preservedWidth = attachedTo.getWidth();
		preservedHeight = attachedTo.getHeight();
		//Get the sprite
		preservedSprite = attachedTo.getSprite();
		//Get the shape
		preservedShape = attachedTo.getShape();
		
	}


	/**
	 * On exiting this state, restore the attached
	 * objects attributes to the preserved attributes
	 */
	@Override
	public void exit() {
		//Restore the position
		attachedTo.setPos(preservedPosition);
		//Restore the dimensions
		attachedTo.setWidth(preservedWidth);
		attachedTo.setHeight(preservedHeight);
		//Restore the sprite
		attachedTo.setSprite(preservedSprite);
		//Restore the shape
		attachedTo.setShape(preservedShape);
		
		attachedTo.updateShape();
	}

}
