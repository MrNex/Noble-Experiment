package triggers;

import engine.Directory;
import objects.Entity;
import objects.GameObject;
import sprites.Sprite;
import state.engine.ShopState;
import state.object.ObjectShopState;
import state.object.PlayerShopState;
import state.object.TargetableState;


/**
 * Defines a trigger which pushes a shopState to the stateStack, adding a background,
 * The entity attached to this trigger, and the entity whom triggered this action to the shopState
 * @authors Nex, Robert Schrupp
 *
 */
public class ShopStartTrigger extends Trigger {

	/**
	 * Constructs a shopState trigger
	 */
	public ShopStartTrigger() {
		super();
	}

	/**
	 * If and only if this was triggered by the player Sets the current state to a shopState, adding the gameObject that
	 * this trigger is attached to, as well as, the gameObject which triggered this trigger
	 * to the state.
	 */
	@Override
	public void action(GameObject triggeredBy) {
		if(triggeredBy == Directory.profile.getPlayer()){
			//Push a shopState to the stateStack
			Directory.engine.pushState(new ShopState((Entity)triggeredBy, (GameObject)attachedTo));
			
			//Set state of shop
			attachedTo.pushState(new ObjectShopState());
			
			//Set state of player
			triggeredBy.pushState(new PlayerShopState());
			
			//Add this obj to the next state
			Directory.engine.getCurrentState().addObj(attachedTo);
			Directory.engine.getCurrentState().addObj(triggeredBy);
		}
	}

}