package state.engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;

import objects.GameObject;
import state.object.InstructionsButtonState;
import state.object.PopStateButtonState;
import state.object.StartButtonState;
import engine.Directory;

/**
 * Class defines a pre-assembled main menu state.
 * @author Nex
 *
 */
public class MainMenuState extends MenuEngineState {

	/**
	 * Constructs a main menu state
	 */
	public MainMenuState() {
		super();

	}

	@Override
	public void init(){
		super.init();

		//Create buttons here
		//Start button creation
		GameObject startButton = new GameObject(
				Directory.screenManager.getPercentageWidth(50.0) - Directory.screenManager.getPercentageWidth(5.0),
				Directory.screenManager.getPercentageHeight(50.0) - Directory.screenManager.getPercentageHeight(2.5),
				Directory.screenManager.getPercentageWidth(10.0),
				Directory.screenManager.getPercentageHeight(5.0));

		startButton.setShape(new Rectangle2D.Double(), Color.gray);
		startButton.setVisible(true);
		startButton.setState(new StartButtonState());

		addObj(startButton);
		
		//Instructions button creation
		GameObject instructionsButton = new GameObject(
				Directory.screenManager.getPercentageWidth(50.0) - Directory.screenManager.getPercentageWidth(10.0),
				Directory.screenManager.getPercentageHeight(60.0) - Directory.screenManager.getPercentageHeight(2.5),
				Directory.screenManager.getPercentageWidth(20.0),
				Directory.screenManager.getPercentageHeight(5.0)
				);
		
		instructionsButton.setShape(new Rectangle2D.Double(), Color.gray);
		instructionsButton.setVisible(true);
		instructionsButton.setState(new InstructionsButtonState());
		addObj(instructionsButton);
		
	}

	/**
	 * No special instructions for entering this state needed
	 */
	@Override
	public void enter() {
		// TODO Auto-generated method stub

	}




	/**
	 * No special instructions for exiting this state needed
	 */
	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
