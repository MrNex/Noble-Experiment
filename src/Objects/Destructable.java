package Objects;
import java.awt.Graphics2D;

import Engine.Directory;

//Class for objects which have solvable equations
public class Destructable extends GameObject{

	protected int health;
	protected int power;
	protected EquationObject currentEq;
	
	public Destructable(double xx, double yy, double w, double h, int hp, int pow) {
		super(xx, yy, w, h);
		//Set attributes
		health = hp;
		power = pow;
		
		//Set current equation
		currentEq = new EquationObject(this);
		
		
	}
	
	//Gets the hitting power of this destructable should it damage something
	public int getPower(){
		return power;
	}
	
	//Gets whether or not the equation attached to this object is selected
	public boolean isSelected(){
		return currentEq.isSelected();
	}
	
	//Toggles the selected state of the equation attached to this destructable
	public void toggleSelected(){
		currentEq.toggleSelected();
	}
	
	public int getHealth(){
		return health;
	}
	
	@Override
	public void update(){
		//Update normally
		super.update();
		//Then update the current equationObject
		currentEq.update();
	}
	
	//Draws normally then draws equation if visible
	@Override
	public void draw(Graphics2D g2d){
		if(isVisible()){
			super.draw(g2d);
			currentEq.draw(g2d);
		}
	}
	
	//Submits an answer, damaging the destructable if correct. Returns whether submitted solution is valid
	public boolean submitAnswer(double solution, int power){
		//If correct
		if(currentEq.attemptSolution(solution)){
			//Decrement health by power
			health -= power;
			//Check if dead
			if(health <= 0){
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
		
		//If not correct, return false
		return false;
	}
}
