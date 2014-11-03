package Objects.ObjStates;

import Engine.Directory;
import Engine.States.BattleState;

/**
 * Class defines a button which calls the endbattle method of
 * the current state (Cast to a battleState) when pushed.
 * @author Nex
 *
 */
public class EndBattleButtonState extends ButtonState {

	public EndBattleButtonState() {
		super("Continue");

	}

	@Override
	protected void onHover() {
		// TODO Auto-generated method stub

	}

	/**
	 * Ends the battlestate
	 * Gets reference to current state as battle state
	 * Call endBattle method
	 */
	@Override
	protected void action() {
		//((BattleState)Directory.engine.getCurrentState()).endBattle();
	}

}
