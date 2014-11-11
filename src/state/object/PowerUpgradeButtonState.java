package state.object;

import engine.Directory;
import state.object.ButtonState;

/**
 * A button which upgrades the player's power for a cost of gold when clicked.
 * Upgrades the player's health by 1, cost player's power * 10
 * @author Nex
 *
 */
public class PowerUpgradeButtonState extends ButtonState {
	//Attributes
	private int cost;
	
	public PowerUpgradeButtonState() {
		super("Upgrade power: Gold");
		
		cost = Directory.profile.getPlayer().getPower() * 10;
		super.setText("Upgrade power: " + cost + " gold");
	}

	@Override
	protected void onHover() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void action() {
		//If the gold can be successfully removed
		if(Directory.profile.removeGold(cost)){
			//Increment player's power by 1
			Directory.profile.getPlayer().incrementPower(1);
			
			cost = Directory.profile.getPlayer().getPower() * 10;
			super.setText("Upgrade Power: " + cost + " gold"); 
		}
	}

}
