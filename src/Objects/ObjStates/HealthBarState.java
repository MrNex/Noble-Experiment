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
	Entity tracking;
	Rectangle2D healthOverlay;
	Color healthOverlayColor;
	double width;
	
	/**
	 * Constructs a health bar
	 * @param toTrack Entity who's health this bar should track
	 */
	public HealthBarState(Entity toTrack) {
		tracking = toTrack;
		healthOverlay = new Rectangle2D.Double();
		width = 0;
		healthOverlayColor = Color.green;
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
		healthOverlay.setFrame(attachedTo.getXPos(), attachedTo.getYPos(), width, attachedTo.getHeight());
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
