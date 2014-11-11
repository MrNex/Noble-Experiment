package state.object;

import engine.Directory;

/**
 * A button which upgrades the player's health for a cost of gold when clicked.
 * Upgrades the player's health by 10, cost playerHealth
 * @author Nex
 *
 */
public class HealthUpgradeButtonState extends ButtonState{
	//Attributes
	private int cost;
	
	public HealthUpgradeButtonState() {
		super("Upgrade health: Gold");
		
		cost = Directory.profile.getPlayer().getTotalHealth();
		super.setText("Upgrade health: " + cost + " gold");
	}

	@Override
	protected void onHover() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void action() {
		//If the gold can be successfully removed
		if(Directory.profile.removeGold(cost)){
			//Increment player's total health by 10
			Directory.profile.getPlayer().incrementTotalHealth(10);
			
			cost = Directory.profile.getPlayer().getTotalHealth();
			super.setText("Upgrade health: " + cost + " gold"); 
		}
		
	}

}
