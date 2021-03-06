package triggers;

import engine.Directory;
import objects.Entity;
import objects.GameObject;
import sprites.Sprite;
import state.engine.BattleState;
import state.object.EnemyBattleState;
import state.object.PlayerBattleState;
import state.object.TargetableState;
import state.object.movable.entity.EntityState;


/**
 * Defines a trigger which pushes a battlestate to the stateStack, adding a background,
 * The entity attached to this trigger, and the entity whom triggered this action to the battleState
 * @author Nex
 *
 */
public class BattleStartTrigger extends Trigger {

	private TargetableState battleState;
	
	/**
	 * Constructs a battleState trigger
	 * 
	 * @param stateForBattle The state you want the attached gameObject to take upon starting the battle
	 */
	public BattleStartTrigger(TargetableState stateForBattle) {
		super();
		
		battleState = stateForBattle;
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
			attachedTo.pushState(battleState);
			attachedTo.removeTrigger(this);
			attachedTo.setTriggerable(false);
			
			//Set state of player
			triggeredBy.pushState(new PlayerBattleState());
			
			//Add this obj to the next state
			//Not needed anymore since separation of competitors from projectiles (list of targetables) in battlestate
			//Directory.engine.getCurrentState().addObj(attachedTo);
			//Directory.engine.getCurrentState().addObj(triggeredBy);
		}
	}

}