package Objects.Triggers;

import Engine.Directory;
import Engine.States.BattleState;
import Objects.GameObject;

public class BattleStartTrigger extends Trigger {

	public BattleStartTrigger() {
		super();
	}

	/**
	 * Sets the current state to a battlestate, adding the gameobject
	 * This trigger is attached to, aswell as the gameobject which triggered the battle
	 * to the state.
	 */
	@Override
	public void action(GameObject triggeredBy) {
		//Push a battlestate to the stateSTack
		Directory.engine.pushState(new BattleState());
		
		//Add this obj to the next state
		Directory.engine.getCurrentState().addObj(attachedTo);
		Directory.engine.getCurrentState().addObj(triggeredBy);
	}

}
