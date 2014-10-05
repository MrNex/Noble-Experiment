package Objects;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Engine.Directory;
import Equations.Equation;
import Equations.Expression;
import Equations.OperatorExpression;

//Class defines a "living" named game object which has stats
public class Entity extends GameObject{

	//Attributes
	protected String name;
	protected int totalHealth, currentHealth, power, defense;
	protected EquationObject currentEq;
	protected boolean isEquation, equationVisibility;

	public Entity(double xx, double yy, double w, double h, int hp, int pow, int def, boolean eq) {
		super(xx, yy, w, h);

		//Set health
		totalHealth = hp;
		currentHealth = totalHealth;

		//Set power
		power = pow;
		
		//Set defense
		defense = def;

		
		//This shows whether this entity has an equation that needs solving (Such as projectiles)
		//Or if this entity has a number that needs an equation made
		isEquation = eq;
		currentEq = new EquationObject(this);

		
		equationVisibility = true;

	}

	
	//Submits an equation representing the answer to this entities equation.
	//If the answer is correct, power is removed from the entities health and a new equation is generated if the entity is not dead
	//If the answer is incorrect, nothing really happens.
	public boolean submitAnswer(Equation answer, int pow)
	{
		
		//If correct
		if(currentEq.attemptSolution(answer.getSolution()))
		{
			//Decrement health by power
			currentHealth -= pow;
			
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
		
	//True if entities equation is actual equation
	//False if entities equation is just a number
	public boolean holdsEquation()
	{
		return isEquation;
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

	/**
	 * Gets whether the equation object on this entity is currently being draw.
	 * @return the visibility of this entities equation object
	 */
	public boolean getEquationVisibility(){
		return equationVisibility;
	}
	
	/**
	 * Sets the visibility of this entities equation object
	 * @param showEquation Should the equation object attached to this entity be showing
	 */
	public void setEquationVisibility(boolean showEquation){
		equationVisibility = showEquation;
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
			if(equationVisibility){
				currentEq.draw(g2d);
			}
		}
	}

}
