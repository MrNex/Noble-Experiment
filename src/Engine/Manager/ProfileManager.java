package Engine.Manager;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

import Engine.Directory;
import Objects.Entity;

/**
 * Defines a component of the engine
 * which keeps track of stats and instances of the player
 * @author Nex
 *
 */
public class ProfileManager extends Manager{

	//Attributes
	private Entity player;
	private int equationsSolved;
	private int equationsMade;
	private int wrongAnswers;
	private int gold;
	private int level;
	private int currentExp, expNeeded;
	
	

	/**
	 * Constructs a profile manager
	 */
	public ProfileManager() {
		super();
	}
	
	/**
	 * Initializes all member variables
	 */
	@Override
	public void init() {
		//Initialize attributes
		equationsSolved = 0;
		equationsMade = 0;
		wrongAnswers = 0;
		gold = 0;
		level = 0;
		currentExp = 0;
		expNeeded = generateNextExpTier();

		//Create the player
		player = new Entity(500, 0, 200, 300, 10, 1, 1);

		//Set the image
		//player.setImage(Directory.imageLibrary.get("PlayerBattleIdle"));
		player.setVisible(true);

	}

	/**
	 * Unused.
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the player
	 * @return A reference to the player's Entity
	 */
	public Entity getPlayer(){
		return player;
	}
	
	/**
	 * Adds gold to the profile
	 * @param goldToAdd The amount of gold to add
	 */
	public void addGold(int goldToAdd){
		gold += goldToAdd;
	}
	
	/**
	 * Attempts to remove an amount of gold from the profile
	 * Returns true if the transaction was a success,
	 * returns false if the profile does not have enough gold
	 * @param goldToRemove The amount of gold to remove
	 * @return Boolean indicating whether or not the transaction was successful
	 */
	public boolean removeGold(int goldToRemove){
		if(gold < goldToRemove) return false;
		gold -= goldToRemove;
		return true;
	}

	/**
	 * Generates the next amount of exp needed to level up
	 * @return The amount of experience needed to level up to the next level
	 */
	private int generateNextExpTier(){
		return (int)(100 + Math.pow(10, level));
	}

	/**
	 * Returns the number of equations solved
	 * @return The number of equations solved
	 */
	public int getEquationsSolved(){
		return equationsSolved;
	}
	
	/**
	 * Increments the players number of equations solved
	 */
	public void incrementEquationsSolved(){
		equationsSolved++;
	}

	/**
	 * Returns the number of wrong answers submitted
	 * @return The number of wrong answers which were submitted
	 */
	public int getWrongAnswers(){
		return wrongAnswers;
	}
	
	/**
	 * Increments the players number of wrong answers
	 */
	public void incrementWrongAnswers(){
		wrongAnswers++;
	}

	/**
	 * Returns the number of correct equations made
	 * @return The number of correct equations made
	 */
	public int getEquationsMade(){
		return equationsSolved;
	}
	
	/**
	 * Increments the players number of equations made
	 */
	public void incrementEquationsMade(){
		equationsMade++;
	}

	/**
	 * Increments the current experience, leveling up if necessary
	 * @param val Amout to increment current experience by
	 */
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
