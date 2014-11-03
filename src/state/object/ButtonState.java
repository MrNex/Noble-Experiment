package state.object;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import Engine.Directory;
import MathHelp.Vector;

abstract public class ButtonState extends ObjectState {

	//Static attributes
	private static int defaultFontSize = 24;
	private static Font defaultFont = new Font("Serif", Font.PLAIN, ButtonState.defaultFontSize);
	private static Color defaultFontColor = Color.black;

	//Attributes
	private int fontSize;
	private Font font;
	private Color fontColor;
	private String buttonText;
	private int textWidth, textHeight;


	/**
	 * Constructs a button state
	 * Calls init()
	 * Assigns text to display on button
	 * 
	 * @param text Text to display on button
	 */
	public ButtonState(String text) {
		super();
		
		//Set text
		buttonText = text;
		measureString();
	}

	/**
	 * Set values to default class values
	 */
	@Override
	public void init(){
		//Set variables to default class values
		fontSize = ButtonState.defaultFontSize;
		font = ButtonState.defaultFont;
		fontColor = ButtonState.defaultFontColor;
	}

	/**
	 * Called upon attaching this state to a gameObject
	 * No preparations should need to be made here.
	 */
	@Override
	public void enter() {


	}

	/**
	 * Measures the length of the current displayString and sets the textWidth
	 * and textHeight properties to reflect this
	 */
	private void measureString(){
		//Get font metrics
		FontMetrics fMetrics = Directory.screenManager.getGraphics().getFontMetrics(font);
		textWidth = fMetrics.stringWidth(buttonText);
	}

	/**
	 * Updates the state of this button
	 * Checks if the mouse is contained within the attached gameObject
	 * 	If true:
	 * 		Checks if mouse is being clicked:
	 * 		if true:
	 * 			Calls action method
	 * 		If false:
	 * 			Calls onHover method
	 */
	@Override
	public void update() {
		//Get mouse position
		Vector mousePos = Directory.inputManager.getMousePosition();
		//If the button contains the mouse
		if(attachedTo.contains(mousePos.getComponent(0), mousePos.getComponent(1))){
			System.out.println("Contains: " + mousePos.toString());
			//If the LMB is being pressed
			if(Directory.inputManager.isMouseButtonPressed(MouseEvent.BUTTON1)){
				action();
			}
			else{
				onHover();
			}

		}
	}

	/**
	 * Defines the action to take when the mouse hovers over this button
	 */
	abstract protected void onHover();

	/**
	 * Defines the action to take when the mouse clicks this button
	 */
	abstract protected void action();


	/**
	 * Draws the display string centered on attached object
	 * 
	 * Store g2d's current font
	 * Set font to targetEquationDisplayState's font
	 * Set color to fontColor
	 * Draw string
	 * Restore g2d's font
	 */
	@Override
	public void draw(Graphics2D g2d) {
		Font save = g2d.getFont();
		g2d.setFont(font);
		g2d.setColor(fontColor);
		g2d.drawString(buttonText, 
				(int)(attachedTo.getXPos() + attachedTo.getWidth() / 2.0 - textWidth / 2.0), 
				(int)(attachedTo.getYPos() + attachedTo.getHeight() / 2.0));

		g2d.setFont(save);
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}