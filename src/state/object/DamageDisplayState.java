package state.object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Engine.Directory;

public class DamageDisplayState extends ObjectState {

	//Static attributes
	private static int displayTime = 5;			//Display damage for 5 seconds
	private static double riseSpeed = -35.0;	//Rate at which display damage rises
	
	private static Color fontColor = Color.red;
	private static Font font = new Font("Serif", Font.BOLD, 35);
	
	//Attributes
	private int damageDealt;
	private double previousTime;
	private double elapsedTime;
	
	/**
	 * Constructs a damage display state
	 * 
	 * @param damageDealt amount of damage this state should display
	 */
	public DamageDisplayState(int damageDealt) {
		super();
		
		this.damageDealt = damageDealt;
		
	}
	
	/**
	 * Initializes all internal member variables
	 */
	@Override
	protected void init() {
		// set elapsed time to start at 0
		elapsedTime = 0;
	}

	/**
	 * Called upon a gameobject getting attached to this state
	 * 
	 * Set the attached object as visible
	 * Set the attached object's color to fully transparent
	 * Set the attached object's dimensions to 0, 0
	 * 
	 * Record the previous time to begin timing
	 */
	@Override
	public void enter() {
		
		attachedTo.setColor(new Color(0, 0, 0, 0));
		attachedTo.setWidth(0);
		attachedTo.setHeight(0);

		previousTime = System.currentTimeMillis();
	}

	/**
	 * Updates the damage display state
	 * 
	 * Get the current time
	 * Calculate the elapsed time since spawning
	 * Calculate the difference in time since last frame
	 * Translate the gameObject by the difference * riseSpeed
	 * If the object's elapsedTime since spawning is greater than the display time
	 * 		Or
	 * the gameobject's position + font size is greater than the top of the screen
	 * 		Then remove the object from the hud
	 * 
	 * Set previousTime to currentTime to update
	 */
	@Override
	public void update() {
		
		double currentTime = System.currentTimeMillis();
		double diff = (currentTime - previousTime) / 1000.0;
		elapsedTime += diff;
		
		attachedTo.getPos().incrementComponent(1, DamageDisplayState.riseSpeed * diff);
				
		if(elapsedTime > DamageDisplayState.displayTime || attachedTo.getYPos() + DamageDisplayState.font.getSize() < 0){
			Directory.screenManager.RemoveFromHud(attachedTo);
		}
		
		previousTime = currentTime;
	}

	/**
	 * Draws the damagedisplaystate
	 * 
	 * Saves the current font of the graphics manager
	 * Sets the font and color f graphics renderer to DamageDisplayState.font / DamageDisplayState.fontColor
	 * Draws the string at the attached gameObject's position
	 * Restores font to the savedfont
	 */
	@Override
	public void draw(Graphics2D g2d) {
		Font saved = g2d.getFont();
		
		g2d.setFont(DamageDisplayState.font);
		g2d.setColor(DamageDisplayState.fontColor);
		g2d.drawString("-" + Integer.toString(damageDealt), (int)attachedTo.getXPos(), (int)attachedTo.getYPos());
		
		g2d.setFont(saved);
	}

	/**
	 * Called when an object is exiting this state
	 * 
	 * Objects put into this state should never really leave it, they will just delete themselves.
	 */
	@Override
	public void exit() {

	}



}
