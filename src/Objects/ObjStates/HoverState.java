package Objects.ObjStates;

import java.awt.Graphics2D;

import MathHelp.Vector;
import Objects.GameObject;

/**
 * HoverState defines a state which makes a gameObject hover above another gameObject 
 * no matter what it's changes in position are
 * @author Nex
 *
 */
public class HoverState extends ObjState {

	//Attributes
	private static double yOffset = -50;	//Hover height
	private GameObject following;			//Object hovering over

	/**
	 * Creates a hoverstate
	 * @param toFollow The object you want this state to hover over
	 */
	public HoverState(GameObject toFollow) {
		following = toFollow;
	}

	
	@Override
	public void enter() {
		// TODO Auto-generated method stub

	}

	/**
	 * Updates the attached gameObjects position to be above the
	 * gameObject this state is following.
	 * 
	 * And updates the attached shape.
	 */
	@Override
	public void update() {
		Vector newPos = new Vector(following.getPos());
		newPos.setComponent(1, newPos.getComponent(1) + yOffset);
		attachedTo.setPos(newPos);
		

		//Update the shape
		attachedTo.updateShape();

	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
