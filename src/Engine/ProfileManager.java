package Engine;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

import Objects.Entity;

//This class keeps track of stats and instances of the player
public class ProfileManager {

	private Entity player;
	private int equationsSolved;
	private int wrongAnswers;
	private int level;
	private int currentExp, expNeeded;
	
	public ProfileManager() {
		//Initialize attributes
		equationsSolved = 0;
		wrongAnswers = 0;
		level = 0;
		currentExp = 0;
		expNeeded = generateNextExpTier();
		
		//Create the player
		player = new Entity(0, 0, 75, 300, 10, 1, 1, true);
		//
		// 
		
		//SEt the shape
		player.setShape(new Ellipse2D.Double(), Color.GREEN);
		player.setVisible(true);
	}
	
	public Entity getPlayer(){
		return player;
	}
	
	//Generates the next amount of exp needed to level up
	private int generateNextExpTier(){
		return (int)(100 + Math.pow(10, level));
	}
	
	//Increments the players number of equations solved
	public void incrementEquationsSolved(){
		equationsSolved++;
	}
	
	//Increments the players number of wrong answers
	public void incrementWrongAnswers(){
		wrongAnswers++;
	}
	
	//Increments the current experience, leveling up if necessary
	public void incrementCurrentExp(int val){
		//Increment the current experience
		currentExp += val;
		//If the current experience is more than needed to level up
		if(currentExp >= expNeeded){
			//Subtract to get remaining
			currentExp -= expNeeded;
			
			//Increment level
			level++;
			
			//Get the next tier of expNeeded
			expNeeded = generateNextExpTier();
			
		}
	}

}
