package state.object;

import Engine.Directory;

/**
 * Defines a button state which when pressed
 * Pops the current state of the engine
 * @author Nex
 *
 */
public class PopStateButtonState extends ButtonState{

	/**
	 * Constructs PopStateButtonState
	 */
	public PopStateButtonState(String text) {
		super(text);
	}

	/**
	 * Currently does nothing on hover
	 */
	@Override
	protected void onHover() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * On action, pops current state off of engine
	 */
	@Override
	protected void action() {
		Directory.engine.popState();
		
	}

}
