package state.object;

import engine.Directory;
import engine.manager.ProfileManager;
import engine.manager.ProfileManager.Abilities;

/***
 * Defines a button which unlocks a player's ability
 * at a price of gold when clicked.
 * 
 * If the ability is already unlocked, this button will remove itself from the current state.
 * 
 * @author Nex
 *
 */
public class AbilityPurchaseButtonState extends ButtonState {

	//Attributes
	private Abilities ability;
	private int cost;
	
	/**
	 * Creates an ability purchasing button
	 * @param ability The ability this button should purchase
	 * @param name The name of this ability
	 * @param cost The cost of this ability in Gold
	 */
	public AbilityPurchaseButtonState(Abilities ability, String name, int cost) {
		super(name + " ability: " + cost + " Gold");
		
		this.ability = ability;
		this.cost = cost;
	}

	/**
	 * Actions performed when this button is hovered over
	 * With the mouse
	 */
	@Override
	protected void onHover() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void update()
	{
		super.update();
		if(Directory.profile.isAbilityUnlocked(ability)){
			//If the ability this button unlocks is already unlocked,
			//Remove it from the current state
			Directory.engine.getCurrentState().removeObj(attachedTo);
		}
		
	}

	/**
	 * Actions performed when this button is clicked with the mouse
	 * If the player does not yet have the ability
	 * And the appropriate amount of gold is successfully removed from the player
	 * Unlock the specified ability.
	 * 
	 * If the ability is already unlocked, delete the attached game object.
	 */
	@Override
	protected void action() {
		//If the ability is not unlocked
		if(!Directory.profile.isAbilityUnlocked(ability)){
			//Attempt to remove cost from players gold
			if(Directory.profile.removeGold(cost)){
				//If successful, unlock ability for player
				Directory.profile.unlockAbility(ability);
			}
		}


	}

}
