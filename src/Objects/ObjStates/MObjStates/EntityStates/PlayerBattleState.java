package Objects.ObjStates.MObjStates.EntityStates;

import java.util.ArrayList;
import java.util.LinkedList;

import Engine.Directory;
import Engine.States.BattleState;
import Equations.*;
import MathHelp.Vector;
import Objects.Entity;
import Objects.ObjStates.ObjBattleState;
import Objects.ObjStates.MObjStates.PlayerOverworldState;

/**
 * State which governs the player during the engine's BattleState
 * @author Nex
 *
 */
public class PlayerBattleState extends TargetableState{

	//attributes
	private Vector worldPos;
	private int numAddOp;
	private int numSubOp;
	private int numMultOp;
	private int numDivOp;
	private String answerString;
	private Entity currentTarget;
	private int targetIndex;
	private Entity player;

	public PlayerBattleState() {
		super(true);

		//Set attributes
		answerString = "";
		currentTarget = null;
		targetIndex = -1;
	}

	@Override
	public void enter() {
		super.enter();
		
		//Save current position
		worldPos = new Vector(attachedTo.getPos());

		//downcast attachedTo gameobject to player
		player = getAttachedEntity();

		//Toggle target to select first target
		toggleTarget();
		
		//Update position
		Vector posVector = new Vector(2);
		posVector.setComponent(0, Directory.screenManager.getPercentageWidth(15.0));
		posVector.setComponent(1, Directory.screenManager.getPercentageHeight(45.0));
		attachedTo.setPos(posVector);


		//Update dimensions
		attachedTo.setWidth(200);
		attachedTo.setHeight(300);

		//Set the image of player to the battleImage
		player.setImage(Directory.imageLibrary.get("PlayerBattleIdle"));

		//Set the visibility of the equation object
		//player.setEquationVisibility(true);

		//Toggle target to select first target
		toggleTarget();
	}

	@Override
	public void update() {
		super.update();
		
		//Declare character to retrieve keys in the order that they were pressed since last update
		Character ch;
		//While the next character pressed isn't null
		while((ch = Directory.inputManager.getNextKeyPressed()) != null){			//getNextKeyPressed dequeues the next key pressed from a queue of all keypresses
			//If ch is a number or a negative sign
			if(Character.isDigit(ch) || ch == (int)'+' || ch == (int)'-' || ch == (int)'*' || ch == (int)'/'){
				//If you start typing an answer and there isn't a target yet, toggle target
				if(currentTarget == null){
					toggleTarget();
				}

				//Add to the answerString
				answerString += ch;
				System.out.println(answerString);
			}
			//Else if ch is a newline or return
			else if((int)ch == (int)'\n' || (int)ch == (int)'\r'){
				//If the current target has an equation
				if(((TargetableState)currentTarget.getState()).holdsEquation()){
					answerEquation();

				}else{
					submitEquation();
				
				}

				//Clear answerString
				answerString = "";


			}
			//Else if ch is whitespace
			else if(Character.isWhitespace(ch)){
				//Toggle current target
				toggleTarget();
			}
			//Else if backspace was pressed (casts to int 8)
			else if((int)ch == 8){
				//Remove the last character from the answer string
				//answerString = answerString.substring(0, answerString.length()-1);
				//Clear answerString
				answerString = "";
			}
		}

		//Update shape in case of moving
		attachedTo.updateShape();

	}


	//Changes the current target. If there is already a current target it will set it to a deselected state.
	//If the next target index is still within the bounds of the list, and the next one is alive, it will set it as the current target.
	//If it turns out the next target isn't alive it simply toggles targets again after setting the current target to null so as not to select it

	private void toggleTarget(){
		
		//Get the state of the current target
		//ObjBattleState currObjState = null;
		//if(currentTarget != null)
			//currObjState = (ObjBattleState)currentTarget.getState();
		
		TargetableState currObjState = null;
		if(currentTarget != null)
			currObjState = (TargetableState)currentTarget.getState();
		
		//Change targets
		//If the current target isn't null and it is selected
		if(currObjState != null && currObjState.isSelected()){
			//Toggle selected attribute of current target(deselect)
			currObjState.toggleSelected();
		}

		//Increment target index
		targetIndex++;

		//Get the current battlestate
		BattleState gameState = (BattleState)Directory.engine.getCurrentState();

		//If targetIndex is within the bounds of the 
		//battlestate's array of destructables assign the next index as the current target
		if(targetIndex < gameState.getEntities().size()){
			currentTarget = gameState.getEntities().get(targetIndex);
			currObjState = (TargetableState)currentTarget.getState();
			
			//Make sure the current target is alive and targetable
			if(currentTarget.getCurrentHealth() <= 0 && currObjState instanceof TargetableState){
				//If not toggle target again
				//But first set current target not to null so the state on the new current target does not get selected
				currentTarget = null;
				toggleTarget();

			}
			else
			{
				//Set target as selected
				currObjState.toggleSelected();
			}
		}
		else{
			//Are there any destructables
			if(gameState.getEntities().size() <= 1){
				//No destructables in gameState, set targetIndex to -1 and currentTarget to null
				targetIndex = -1;
				currentTarget = null;
			}
			else{
				//Set targetIndex to 0 and set currentTarget
				targetIndex = 0;
				currentTarget = gameState.getEntities().get(targetIndex);
				currObjState = (TargetableState)currentTarget.getState();
				//Make sure the current target is alive
				if(currentTarget.getCurrentHealth() <= 0){
					//Make sure there is another target to choose from (else the only target on the screen is dead
					if(gameState.getEntities().size() > 1){
						//If so toggle target again
						toggleTarget();
					}
					//Else set target to null and set index to -1
					else{
						currentTarget = null;
						targetIndex = -1;
					}

				}
				else{
					//Set target as selected
					currObjState.toggleSelected();
				}
			}
		}
	}
	
	

	//Takes your current answerString, parses it into an equation, and submits it to the current target
	private void submitEquation(){

		//Declare the submission equation
		Equation submission = null;

		try{
			//Compile the equation
			submission = Equation.parseEquation(answerString);
		}
		catch(InvalidEquationException iEE){
			//Improper equation
			//Give feedback
			System.out.println(iEE.getMessage());
			submission = null;
		}


		//If the submission is valid
		if(submission!=null){
			//Make sure the player has enough operators for equation
			if(numAddOp >= submission.getAdditionOperators() &&
					numSubOp >= submission.getSubtractionOperators() &&
					numMultOp >= submission.getMultiplicationOperators() &&
					numDivOp >= submission.getDivisionOperators()){

				//Take the operators away from the player
				numAddOp -= submission.getAdditionOperators();
				numSubOp -= submission.getSubtractionOperators();
				numMultOp -= submission.getMultiplicationOperators();
				numDivOp -= submission.getDivisionOperators();

				//Get current targets battle state
				TargetableState targetState = (TargetableState)currentTarget.getState();
				
				//send answer to current target
				if(targetState.submitAnswer(submission, player.getPower())){
					Directory.profile.incrementEquationsMade();

					//Check if the Entity was killed
					if(currentTarget.getCurrentHealth() <= 0){
						battleEnd();
					}
					else{
						targetState.generateNewEquation();

					}
				}
				else{
					Directory.profile.incrementWrongAnswers();
					System.out.println("Wrong, answer is " + targetState.getEquation().getSolution() + " your answer was " + submission.getSolution());
				}
			}
			else{
				System.out.println("You do not have enough operators!");
			}
		}
		else{
			System.out.println("Invalid Equation");
			
			Directory.profile.incrementWrongAnswers();
		}


	}

	//Takes a linked list of integers representing digits in a number where the first value 
	//has the highest place value decreasing to the lowest in the last spot
	//And turns it into the corresponding integer returning that value
	private int ListToInt(LinkedList<Integer> digits){
		int sum = 0;
		while(digits.size() > 0){
			int increment = digits.pollFirst();
			if(digits.size() > 0)
				increment *= digits.size();
			sum += increment;
		}
		return sum;
	}

	//Takes your current answerString, parses it to an integer, and submits it to the current target
	private void answerEquation(){
		//Attempt solution 
		int answer;
		//Parse answerString as an integer
		try{
			answer = Integer.parseInt(answerString);
		}catch(NumberFormatException nfe){
			answer = 0;
		}

		//If targeting yourself, make the power 0.
		int submitPower = currentTarget == player ? 0 : player.getPower();

		//Get targets battle state
		TargetableState targetState = (TargetableState)currentTarget.getState();
		
		//send answer to current target
		if(targetState.submitAnswer(new Equation(answer), submitPower)){

			System.out.println("Correct");
			
			//Add the operators from solved equation to the player
			numAddOp += targetState.getEquation().getAdditionOperators();
			numSubOp += targetState.getEquation().getSubtractionOperators();
			numMultOp += targetState.getEquation().getMultiplicationOperators();
			numDivOp += targetState.getEquation().getDivisionOperators();

			//Increment profile stats
			Directory.profile.incrementEquationsSolved();

			//Check if the Entity was killed
			if(currentTarget.getCurrentHealth() <= 0){
				//Toggle target
				toggleTarget();
			}
			else{
				targetState.generateNewEquation();

			}
		}
		else{
			System.out.println("Wrong");
			
			//Increment profile stats
			Directory.profile.incrementWrongAnswers();

			//Clear the answer string
			answerString = "";
		}
	}

	@Override
	public void exit() {
		//Set position back to worldPos
		attachedTo.setPos(worldPos);
		
		//Set image to null
		attachedTo.setImage(null);
	}
	
	/**
	 * The player is ending the battle: The player has won
	 */
	private void battleEnd(){
		//Exit battlestate
		Directory.engine.popState();
		//Remove dead entity
		Directory.engine.getCurrentState().removeObj(currentTarget);
		//swap state back to overworld state
		attachedTo.setState(new PlayerOverworldState());
	}

}
