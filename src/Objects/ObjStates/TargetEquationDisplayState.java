package Objects.ObjStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import Engine.Directory;
import Objects.GameObject;
import Objects.ObjStates.MObjStates.EntityStates.PlayerBattleState;
import Objects.ObjStates.MObjStates.EntityStates.TargetableState;

/**
 * Defines the state of an object on the HUD which
 * displays the concatenation of the users answer string with the
 * player's target's equation
 * @author Nex
 *
 */
public class TargetEquationDisplayState extends ObjState {

	//Attributes
	private String displayString;
	private int stringWidth, stringHeight;
	private int fontSize;
	private Font font;
	private Color fontColor;

	/**
	 * Constructs a targetEquationDisplayState
	 */
	public TargetEquationDisplayState() {
		super();

		fontSize = 50;
		font = new Font("Serif", Font.BOLD, fontSize);
		fontColor = Color.black;
	}

	@Override
	public void enter() {
		updateDisplayString();
		measureDisplayString();
	}

	@Override
	public void update() {
		updateDisplayString();
		measureDisplayString();

	}

	private void updateDisplayString(){
		PlayerBattleState playerState = null;
		TargetableState targetState = null;
		
		ObjState pState = Directory.profile.getPlayer().getState();
		//Get the player's state
		if(pState != null && pState instanceof PlayerBattleState)
		{
			playerState = (PlayerBattleState) pState;

			ObjState tState = null;
			//Get the player's current target's state
			if(playerState.getCurrentTarget() != null){
				tState = playerState.getCurrentTarget().getState();
			}
				
			if(tState != null && tState instanceof TargetableState){
				targetState = (TargetableState)playerState.getCurrentTarget().getState();

			}
		}


		//IF the target's state is null set the string to nothing
		if(targetState == null){
			if(playerState == null){
				displayString = "";	
			}
			else displayString = playerState.getAnswerString();
		}
		else{
			if(targetState.holdsEquation()){
				//Write the targetstate's equation first them the player's answer string!
				displayString = targetState.getEquation().toString() + " = " + playerState.getAnswerString();
			}
			else{
				//Wrte the player's answer string followed by the equation of the target
				displayString = playerState.getAnswerString() + " = " + targetState.getEquation().toString();
			}
		}
	}

	/**
	 * Measures the length of the current displayString and sets the stringWidth
	 * and stringHeight properties to reflect this
	 */
	private void measureDisplayString(){
		//Get font metrics
		FontMetrics fMetrics = Directory.screenManager.getGraphics().getFontMetrics(font);
		stringWidth = fMetrics.stringWidth(displayString);
		stringHeight = fMetrics.getMaxAscent() + fMetrics.getMaxDescent();
	}


	/**
	 * Draws the display string centered on attached object
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
		g2d.drawString(displayString, 
				(int)(attachedTo.getXPos() + attachedTo.getWidth() / 2.0 - stringWidth / 2.0), 
				(int)(attachedTo.getYPos() + attachedTo.getHeight() / 2.0 - stringHeight / 4.0));

		g2d.setFont(save);
	}

	/**
	 * Called upon an object exiting this state
	 * No precautions should need to be taken.
	 */
	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
