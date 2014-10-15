package Objects;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Engine.Directory;
import Equations.Equation;
import Equations.Expression;
import Equations.OperatorExpression;

//Class defines a "living" named game object which has stats
public class Entity extends MovableGameObject{

	//Attributes
	protected String name;
	protected int totalHealth, currentHealth, power, defense;

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
		
	public int getCurrentHealth(){
		return currentHealth;
	}

	public int getPower(){
		return power;
	}
	
	public int getDefense(){
		return defense;
	}
	
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
			setRunning(false);
			//SEt the state of the entity to null
			setState(null);							//Causes exit conditions to run on current state (Could potentially be important)
			//Remove entity from current engine state
			Directory.engine.getCurrentState().removeObj(this);
		}
	}

}
