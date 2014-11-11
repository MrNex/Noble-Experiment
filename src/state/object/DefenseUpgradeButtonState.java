package state.object;

import Engine.Directory;

/**
 * A button which upgrades the player's defense for a cost of gold when clicked.
 * Upgrades the player's defese by 1, cost player's Defense * 10
 * @author Nex
 *
 */
public class DefenseUpgradeButtonState extends ButtonState {
	//Attributes
	private int cost;
	
	public DefenseUpgradeButtonState() {
		super("Upgrade defense: Gold");

		cost = Directory.profile.getPlayer().getDefense() * 10;
		super.setText("Upgrade defense: " + cost + " gold");
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
			Directory.profile.getPlayer().incrementDefense(1);
			
			cost = Directory.profile.getPlayer().getDefense() * 10;
			super.setText("Upgrade health: " + cost + " gold"); 
		}
	}
}
