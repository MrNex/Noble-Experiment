package Objects;

import java.awt.Graphics2D;

import Engine.Directory;
import Equations.Equation;

//Class defines a "living" named game object which has stats
public class Entity extends GameObject{

	//Attributes
	protected String name;
	protected int totalHealth, currentHealth, power, defense;
	protected EquationObject currentEq;
	protected boolean isEquation;

	public Entity(double xx, double yy, double w, double h, int hp, int pow, int def, boolean eq) {
		super(xx, yy, w, h);

		//Set health
		totalHealth = hp;
		currentHealth = totalHealth;

		//Set power
		power = pow;
		
		//Set defense
		defense = def;

		
		isEquation = eq;
		//TODO: Split this up into two different subclasses, equationEntity and numericEntity

		currentEq = new EquationObject(this);

	}

	public boolean submitAnswer(Equation answer, int pow)
	{
		//If correct
		if(currentEq.attemptSolution(answer.getSolution()))
		{
			//Decrement health by power
			currentHealth -= power;
			//Check if dead
			if(currentHealth <= 0){
				//Set running to false
				running = false;
				//Set visible to false
				visible = false;
				
				//SEt state as null so any timers associated with this destructable are stopped
				setState(null);
				
				//Remove from state
				Directory.engine.getCurrentState().removeObj(this);
			}
			
			//Return true for a correct answer
			return true;
		}
		
		
		return false;
	}
		
	
	public EquationObject getEquationObj()
	{
		return currentEq;
	}
		
	public int getCurrentHealth(){
		return currentHealth;
	}

	public int getPower(){
		return power;
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
			//SEt the state of the entity to null
			setState(null);							//Causes exit conditions to run on current state (Could potentially be important)
			//Remove entity from current engine state
			Directory.engine.getCurrentState().removeObj(this);
		}
	}
	
	@Override
	public void update(){
		//Update normally
		super.update();
		//Then update the current equationObject
		currentEq.update();
	}
	
	
	@Override
	public void draw(Graphics2D g2d){
		if(isVisible()){
			super.draw(g2d);
			currentEq.draw(g2d);
		}
	}

}
