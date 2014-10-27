package Objects.ObjStates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import Objects.Entity;

/**
 * Defines a state which tracks an entities health and 
 * overlays the attached object's shape with a rectangle
 * Which consumes the percentage of the attached object's shape
 * representing the percentage of player health left.
 * @author Nex
 *
 */
public class HealthBarState extends ObjState {

	//Attributes
	private Entity tracking;
	private Rectangle2D healthOverlay;
	private Color healthOverlayColor;
	private double width;
	private boolean leftAligned;
	
	/**
	 * Constructs a health bar
	 * @param toTrack Entity who's health this bar should track
	 * @param left Should the bar deplete to the left?
	 */
	public HealthBarState(Entity toTrack, boolean left) {
		tracking = toTrack;
		healthOverlay = new Rectangle2D.Double();
		width = 0;
		healthOverlayColor = Color.green;
		leftAligned = left;
	}

	@Override
	public void enter() {
		
	}

	@Override
	public void update() {
		width = ((double)tracking.getCurrentHealth() / (double)tracking.getTotalHealth()) * attachedTo.getWidth();
		updateShape();

	}
	
	/**
	 * Update the health overlay this state displays
	 */
	private void updateShape(){
		if(leftAligned)
			healthOverlay.setFrame(attachedTo.getXPos(), attachedTo.getYPos(), width, attachedTo.getHeight());
		else
			healthOverlay.setFrame(attachedTo.getXPos() + (attachedTo.getWidth() - width), attachedTo.getYPos(), width, attachedTo.getHeight());
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(healthOverlayColor);
		g2d.fill(healthOverlay);
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
