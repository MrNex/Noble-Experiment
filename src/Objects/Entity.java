package Objects;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Engine.Directory;
import Equations.Equation;
import Equations.Expression;
import Equations.OperatorExpression;

/**
 * Class defines a gameobject with health, power, and defense
 * @author Nex
 *
 */
public class Entity extends MovableGameObject{

	//Attributes
	protected String name;
	protected int totalHealth, currentHealth, power, defense;

	/**
	 * Constructs an entity
	 * @param xx X Position
	 * @param yy Y Position
	 * @param w Width
	 * @param h Height
	 * @param hp Total health
	 * @param pow Power
	 * @param def Defense
	 */
	public Entity(double xx, double yy, double w, double h, int hp, int pow, int def) {
		super(xx, yy, w, h);

		//Set health
		totalHealth = hp;
		currentHealth = totalHealth;

		//Set power
		power = pow;
		
		//Set defense
		defense = def;

		

	}	
		
	/**
	 * Gets the current health level of the entity
	 * @return The current health
	 */
	public int getCurrentHealth(){
		return currentHealth;
	}

	/**
	 * Gets the power of the entity
	 * @return Power of this entity
	 */
	public int getPower(){
		return power;
	}
	
	/**
	 * Gets the defense of the entity
	 * @return The defense of the entity
	 */
	public int getDefense(){
		return defense;
	}
	
	/**
	 * Increments the current health
	 * If the current health goes over the total health, sets current health to total health
	 * If the entity is dead, sets state to null and removes object from current enginestate
	 * @param val Value to increment health by
	 */
	public void incrementCurrentHealth(int val){
		currentHealth += val;
		//if the currentHealth is over the limit
		if(currentHealth > totalHealth){
			//Set currenthealth to the totalHealth
			currentHealth = totalHealth;
		}

		//If the entity is dead
		else if(currentHealth <= 0){
			//SEt the state of the entity to null
			setState(null);							//Causes exit conditions to run on current state (Could potentially be important)
			//Remove entity from current engine state
			Directory.engine.getCurrentState().removeObj(this);
		}
	}

	/**
	 * Decrements the current health
	 * If the current health goes over the total health, the current health is set to the current health
	 * If the entity dies, its state is set to null, and it is removed from the current state of the engine
	 * @param val Value to decrement 
	 */
	public void decrementCurrentHealth(int val){
		currentHealth -= val;
		System.out.println(currentHealth);
		//if the currentHealth is over the limit
		if(currentHealth > totalHealth){
			//Set currenthealth to the totalHealth
			currentHealth = totalHealth;
		}

		//If the entity is dead
		else if(currentHealth <= 0){
			//Set running to false
			//setRunning(false);
			//SEt the state of the entity to null which sets running to false
			setState(null);							//Causes exit conditions to run on current state (Could potentially be important)
			//Remove entity from current engine state
			Directory.engine.getCurrentState().removeObj(this);
		}
	}

}
