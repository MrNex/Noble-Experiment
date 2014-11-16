package state.object;

import state.engine.InstructionsMenuState;
import engine.Directory;

/**
 * Defines a button which pushes an InstructionMenuState to the engine
 * When clicked.
 * @author Nex
 *
 */
public class InstructionsButtonState extends ButtonState{

	public InstructionsButtonState() {
		super("Instructions");
		
	}

	@Override
	protected void onHover() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void action() {
		Directory.engine.pushState(new InstructionsMenuState());
		
	}

}
