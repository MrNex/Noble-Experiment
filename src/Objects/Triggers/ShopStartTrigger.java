package Objects.Triggers;

import Engine.Directory;
import Engine.States.ShopState;
import Objects.GameObject;
import Objects.Entity;
import Objects.ObjStates.MObjStates.EntityStates.PlayerShopState;
import Objects.ObjStates.MObjStates.EntityStates.TargetableState;
import Objects.Sprites.Sprite;


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

			// Create custom background as gameObject
			GameObject background = new GameObject(0, 0, Directory.screenManager.getPercentageWidth(100.0), Directory.screenManager.getPercentageHeight(100.0));
			// set background values
			background.setVisible(true);
			
			//Create sprite for background out of BackgroundForest 1 image
			//Set sprite of background
			background.setSprite(Directory.spriteLibrary.get("Background_Forest_1"));

			
			Directory.engine.getCurrentState().addObj(background);
			
			//Set state of the attached obj
			//attachedTo.pushState(new EnemyBattleState());
			//attachedTo.removeTrigger(this);
			//attachedTo.setTriggerable(false);
			
			//Set state of player
			triggeredBy.pushState(new PlayerShopState());
			
			//Add this obj to the next state
			Directory.engine.getCurrentState().addObj(attachedTo);
			Directory.engine.getCurrentState().addObj(triggeredBy);
		}
	}

}