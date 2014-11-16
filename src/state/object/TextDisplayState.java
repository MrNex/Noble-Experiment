package state.object;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import engine.Directory;

/**
 * Defines a class which centers and displays a string
 * on top of it's attached gameObject
 * @author Nex
 *
 */
public class TextDisplayState extends ObjectState {

	//Static attributes
	private static int defaultFontSize = 18;
	private static Font defaultFont = new Font("Serif", Font.PLAIN, TextDisplayState.defaultFontSize);
	
	//Attributes
	private Color textColor;
	private String text;
	private int textWidth, textHeight;
	
	//Accessors / Modifiers
	/**
	 * Sets the text to display
	 * @param newText Text this state should dsplay
	 */
	public void setText(String newText){
		text = newText;
		measureString();
	}
	
	/**
	 * Gets the text being displayed
	 * @return The text this state is displaying
	 */
	public String getText(){
		return text;
	}
	
	/**
	 * Sets the color the text is being displayed in
	 * @param newColor The new color to display text in
	 */
	public void setColor(Color newColor){
		textColor = newColor;
	}
	
	/**
	 * Constructs a text display state
	 * 
	 * @param text Text this state should display
	 */
	public TextDisplayState(String text) {
		super();
		this.text = text;
		this.textColor = Color.black;
		
		measureString();
	}
	
	/**
	 * Constructs a textdisplay state and sets the texts color
	 * @param text Text to display
	 * @param color Color to display text in
	 */
	public TextDisplayState(String text, Color color){
		super();
		this.text = text;
		this.textColor = color;
		
		measureString();
	}

	/**
	 * initializes internal variables
	 */
	@Override
	protected void init() {

	}

	/**
	 * No special instructions needed upon enteringthis state
	 */
	@Override
	public void enter() {
		
	}

	/**
	 * Updates TextDisplayState
	 */
	@Override
	public void update() {

	}

	/**
	 * Draws the displaying text in the specified color
	 * centered over the attached object
	 */
	@Override
	public void draw(Graphics2D g2d) {
		Font save = g2d.getFont();
		g2d.setFont(TextDisplayState.defaultFont);
		g2d.setColor(textColor);
		g2d.drawString(text, 
				(int)(attachedTo.getXPos() + attachedTo.getWidth() / 2.0 - textWidth / 2.0), 
				(int)(attachedTo.getYPos() + attachedTo.getHeight() / 2.0 + textHeight / 2.0));

		g2d.setFont(save);
	}
	
	/**
	 * Measures the length of the current displayString and sets the textWidth
	 * and textHeight properties to reflect this
	 */
	private void measureString(){
		//Get font metrics
		FontMetrics fMetrics = Directory.screenManager.getGraphics().getFontMetrics(TextDisplayState.defaultFont);
		System.out.println(text);
		textWidth = fMetrics.stringWidth(text);
		textHeight = fMetrics.getMaxAscent() - fMetrics.getMaxDescent();
	}

	/**
	 * No special instructions needed on exiting this state
	 */
	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
