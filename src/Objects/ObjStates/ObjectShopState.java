package Objects.ObjStates;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;

import Engine.Directory;
import Engine.States.BattleState;
import Equations.*;
import MathHelp.Vector;
import Objects.Entity;
import Objects.GameObject;
import Objects.ObjStates.ObjState;
import Objects.ObjStates.MObjStates.PlayerOverworldState;
import Objects.Sprites.Sprite;

/**
 * State which governs the object during the engine's ShopState
 * @author Nex, Robert Schrupp
 *
 */
public class ObjectShopState extends ObjState{

	//attributes
	private Vector worldPos;
	private GameObject object;

	/**
	 * Creates player shop state
	 * 
	 * Sets the currentTarget to null
	 */
	public ObjectShopState() {
		//super(true);

		//Set attributes
	}

	/**
	 * Has an object enter ObjectShopState
	 * Saves the object's world position and aligns the object on the right side of the screen.
	 * Calls MovableGameObject's refresh method to set the previousPosition to the currentPosition.
	 * 		This ensures the object won't revert to its old previousPosition when getting hit by a projectile.
	 * Sets dimensions to shopDimensions, and sets the image to the object's shopImage
	 * Calls toggleTarget to target a valid entity
	 */
	@Override
	public void enter() {
		//super.enter();

		//Save current position
		worldPos = new Vector(attachedTo.getPos());
		
		//downcast attachedTo gameobject to player
		object = attachedTo;

		//Update position
		Vector posVector = new Vector(2);
		posVector.setComponent(0, Directory.screenManager.getPercentageWidth(75.0));
		posVector.setComponent(1, Directory.screenManager.getPercentageHeight(45.0));
		attachedTo.setPos(posVector);

		//Update dimensions
		attachedTo.setWidth(200);
		attachedTo.setHeight(300);
		
		//Set players sprite
		attachedTo.setSprite(Directory.spriteLibrary.get("Fireball1"));
	}

	/**
	 * Updates the player's ShopState
	 * Loops through all keyPresses since last update
	 * 		If a character is an operator or a number, appends it to the answerString
	 * 		If a character is a newLine buffer, calls answerEquation or submitEquation based on current target
	 * 		If a character is whitespace, togglesTarget and clears answerString
	 * 		If a character is backspace, clears answerString
	 * Updates the attached shape
	 */
	@Override
	public void update() {
		//super.update();
/*
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
			}
			//Else if ch is a newline or return
			else if((int)ch == (int)'\n' || (int)ch == (int)'\r'){
				//Cast current target's state as targetable
				TargetableState targetState = (TargetableState)currentTarget.getState();
				//If the target has a targetable state
				if(targetState != null){
					//If the current target has an equation
					if(targetState.holdsEquation()){
						answerEquation();

					}else{
						submitEquation();

					}
				}


			}
			//Else if ch is whitespace
			else if(Character.isWhitespace(ch)){
				//Toggle current target
				toggleTarget();
			}
			//Else if backspace was pressed (casts to int 8)
			else if((int)ch == 8){
				//Clear answerString
			}
		}
*/
		//Update shape in case of moving
		attachedTo.updateShape();

	}


	/**
	 * Changes the current target. If there is already a current target it will set it to a deselected state.
	 * If the next target index is still within the bounds of the list, and the next one is alive, it will set it as the current target.
	 * If it turns out the next target isn't alive it simply toggles targets again after setting the current target to null so as not to select it
	 * 
	 * If there is only 1 entity left in the state (The entity this state is attached to) the targetIndex is set to -1 and
	 * The currentTarget is set to null
	 */
/*	
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
*/


	/**
	 * Takes your current answerString, parses it into an equation, and submits it to the current target
	 * Parses the answerString into an equation
	 * 		If the equation is invalid or there are no operators in the equation the player's amount of wrong answers is incremented.
	 * However if the equation is valid:
	 * 		If the player does not have enough operators nothing more happens and a prompt is printed.
	 * However, if there is enough operators, Decrements operators in the equation from player.
	 * Sends equation to current target.
	 * If it is correct, increments answers correct, else increments answers wrong
	 * IF the target is still alive, queues the target to generate a new equation
	 */
/*
	private void submitEquation(){

		//Declare the submission equation
		Equation submission = null;

		try{
			//Compile the equation
			submission = Equation.parseEquation(answerString);

			//Make sure it contains at least 1 operator
			if(submission.getAdditionOperators() +
					submission.getSubtractionOperators() +
					submission.getMultiplicationOperators() +
					submission.getDivisionOperators() <= 0)
			{
				//If there is not at least one operator, throw an invalid equation exception
				throw new InvalidEquationException();
			}

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

					//If the target wasn't killed, generate a new equation for it
					if(currentTarget.getCurrentHealth() > 0){
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
*/

	/**
	 * Takes your current answerString, parses it to an integer, and submits it to the current target
	 * 
	 * Tries to parse answerString to integer (prompts and exits method if fails)
	 * Sets the power to the attached Entities current power, or to 0 if the entity is targetting itself
	 * Submits the value and power to the currentTarget.
	 * 		If the submission is incorrect, increments the number of incorrect answers
	 * If the submission is correct, adds the operators in the equation to the player's operators
	 * Increments equations solved and if the target is still alive,has it generate a new equation.
	 */
/*
	private void answerEquation(){
		//Attempt solution 
		int answer;
		//Parse answerString as an integer
		try{
			answer = Integer.parseInt(answerString);
		}catch(NumberFormatException nfe){
			System.out.println("Please enter valid value.");
			answer = 0;
			return;
		}

		//If targeting yourself, make the power 0.
		int submitPower = currentTarget == player ? 0 : getAttachedEntity().getPower();

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
*/
	/**
	 * AttachedObject Exits the player's shop state
	 */
	@Override
	public void exit() {
		//Set position back to worldPos
		attachedTo.setPos(worldPos);

		//Set Sprite to null
		attachedTo.setSprite(null);
	}

	@Override
	public void draw(Graphics2D g2d) {
		
	}

}
