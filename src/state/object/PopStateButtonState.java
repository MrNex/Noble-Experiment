package state.object;

import objects.GameObject;
import state.engine.EngineState;
import state.engine.MenuEngineState;
import engine.Directory;

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
		//If the state underneath is a menustate
		EngineState currentState = Directory.engine.getCurrentState();
		if(currentState instanceof MenuEngineState){
			//loop through objects
			for(GameObject obj : currentState.getObjListCopy()){
				ObjectState attachedState = obj.getState();
				//If the object's state is a button
				if(attachedState instanceof ButtonState){
					//Set it as pressed so it doesn't accidently get clicked.
					((ButtonState)attachedState).setPressed();
				}
			}
		}
	}

}
