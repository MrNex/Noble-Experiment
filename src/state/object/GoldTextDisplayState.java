package state.object;

import java.awt.Color;

import engine.Directory;

public class GoldTextDisplayState extends TextDisplayState {

	private int playerGold;
	
	public GoldTextDisplayState() {
		super("Gold: " + Directory.profile.getGold());
		
		playerGold = Directory.profile.getGold();

	}

	public GoldTextDisplayState(Color color) {
		super("Gold: " + Directory.profile.getGold(), color);
		
		playerGold = Directory.profile.getGold();

	}
	
	/**
	 * If the amount of gold the player has changed, update the text of the label.
	 */
	@Override 
	public void update(){
		super.update();
		if(playerGold != Directory.profile.getGold()){
			playerGold = Directory.profile.getGold();
			super.setText("Gold: " + playerGold);
		}
	}

	
	
}
