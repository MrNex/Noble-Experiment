package Objects.ObjStates;

import java.awt.Graphics2D;

import Engine.Directory;
import Equations.Equation;
import Objects.Entity;
import Objects.EquationObject;
import Objects.GameObject;

/**
 * Obj battleState describes an object state which keeps track of a equation object
 * @author Nex
 *
 */
public abstract class ObjBattleState extends ObjState {

	//Attributes
	private EquationObject equationObj;
	private boolean isEquation;

	//Accessors
	/**
	 * Determines if this state is holding an equation
	 * or just a numerical value.
	 * @return
	 */
	public boolean holdsEquation()
	{
		return isEquation;
	}

	/**
	 * Gets this object's equationObject
	 * @return
	 */
	public EquationObject getEquationObj(){
		return equationObj;
	}

	/**
	 * Constructs an object battle state.
	 * Makes the equationobject
	 * @param isEq
	 */
	public ObjBattleState(boolean isEq) {
		isEquation = isEq;
		
	}

	@Override
	public void enter() {
		//Create equation object
		equationObj = new EquationObject((Entity)attachedTo);

	}

	/**
	 * Updates the equation object on this state.
	 */
	@Override
	public void update(){
		if(equationObj.isRunning())
			equationObj.update();
	}

	@Override
	public void draw(Graphics2D g2d) {
		if(equationObj != null)
			equationObj.draw(g2d);
	}

	//Submits an equation representing the answer to this entities equation.
	//If the answer is correct, power is removed from the entities health and a new equation is generated if the entity is not dead
	//If the answer is incorrect, nothing really happens.
	/**
	 * Submits an equation representing answer to this state's equation.
	 * If the answer is correct, power is removed from the entities health and a new
	 * equation is generated if the entity is not dead.
	 * If the answer is incorrect, nothing happens.
	 * @param answer Answer to submit
	 * @param pow Damage to deal to this entity if correct
	 * @return True if answered correctly, false if answered incorrectly.
	 */
	public boolean submitAnswer(Equation answer, int pow)
	{
		//Send answer to current states equation

		//If correct
		if(equationObj.attemptSolution(answer.getSolution()))
		{
			//Cast attached obj as entity
			Entity e = (Entity)attachedTo;
			//Decrement health by power
			e.decrementCurrentHealth(pow);

			//Check if dead
			if(e.getCurrentHealth() <= 0){
				//Set running to false
				e.setRunning(false);
				//Set visible to false
				e.setVisible(false);

				//SEt state as null so any timers associated with this destructable are stopped
				e.setState(null);

				//Remove from current state
				Directory.engine.getCurrentState().removeObj(attachedTo);
			}

			//Return true for a correct answer
			return true;
		}


		return false;
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
