package Objects.Triggers;

import Engine.Directory;
import Engine.States.BattleState;
import Objects.GameObject;
import Objects.Entity;
import Objects.ObjStates.MObjStates.EntityStates.EnemyBattleState;
import Objects.ObjStates.MObjStates.EntityStates.PlayerBattleState;
import Objects.ObjStates.MObjStates.EntityStates.TargetableState;
import Objects.Sprites.Sprite;


/**
 * Defines a trigger which pushes a battlestate to the stateStack, adding a background,
 * The entity attached to this trigger, and the entity whom triggered this action to the battleState
 * @author Nex
 *
 */
public class BattleStartTrigger extends Trigger {

	/**
	 * Constructs a battleState trigger
	 */
	public BattleStartTrigger() {
		super();
	}

	/**
	 * If and only if this was triggered by the player Sets the current state to a battlestate, adding the gameobject that
	 * this trigger is attached to, aswell as the gameobject which triggered this trigger
	 * to the state.
	 */
	@Override
	public void action(GameObject triggeredBy) {
		if(triggeredBy == Directory.profile.getPlayer()){
			//Push a battlestate to the stateStack
			Directory.engine.pushState(new BattleState((Entity)triggeredBy, (Entity)attachedTo));

			// Create custom background as gameObject
			GameObject background = new GameObject(0, 0, Directory.screenManager.getPercentageWidth(100.0), Directory.screenManager.getPercentageHeight(100.0));
			// set background values
			background.setVisible(true);
			
			//Create sprite for background out of BackgroundForest 1 image
			//Set sprite of background
			background.setSprite(Directory.spriteLibrary.get("Background_Forest_1"));

			
			Directory.engine.getCurrentState().addObj(background);
			
			//Set stae of the attached obj
			attachedTo.pushState(new EnemyBattleState());
			attachedTo.removeTrigger(this);
			attachedTo.setTriggerable(false);
			
			//Set state of player
			triggeredBy.pushState(new PlayerBattleState());
			
			//Add this obj to the next state
			//Directory.engine.getCurrentState().addObj(attachedTo);
			//Directory.engine.getCurrentState().addObj(triggeredBy);
		}
	}

}