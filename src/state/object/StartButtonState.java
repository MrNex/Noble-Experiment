package state.object;

import state.engine.OverworldState;
import engine.Directory;

/**
 * Defines a button which pushes a new overworld state
 * To the engine's state stack when pressed
 * @author Nex
 *
 */
public class StartButtonState extends ButtonState {

	public StartButtonState() {
		super("Start");
	}

	@Override
	protected void onHover() {
		// TODO Auto-generated method stub

	}

	/**
	 * Upon being clicked this button starts the game
	 * Swaps current state with a new OverworldState
	 */
	@Override
	protected void action() {
		Directory.engine.pushState(new OverworldState());
	}

}
