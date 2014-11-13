package state.object;


import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import engine.Directory;
import engine.manager.ProfileManager.Abilities;
import equations.*;
import mathematics.Vector;
import objects.Entity;
import state.engine.BattleState;

/**
 * State which governs the player during the engine's BattleState
 * @author Nex
 *
 */
public class PlayerBattleState extends TargetableState{

	//attributes
	private int numAddOp;
	private int numSubOp;
	private int numMultOp;
	private int numDivOp;
	private String answerString;
	private Entity currentTarget;
	private int targetIndex;

	//Accessors / Modifiers
	/**
	 * Get the current answer string from the player's battle state
	 * @return The currently stored answer from player
	 */
	public String getAnswerString(){
		return answerString;
	}


	/**
	 * Get the player's current target
	 * @return The current target of the player
	 */
	public Entity getCurrentTarget(){
		return currentTarget;
	}
	
	/**
	 * Gets the number of addition operators the player has
	 * @return the number of addition operators the player has
	 */
	public int getNumAdditionOperators(){
		return numAddOp;
	}
	
	/**
	 * Gets the number of Subtraction operators the player has
	 * @return the number of subtraction operators the player has
	 */
	public int getNumSubtractionOperators(){
		return numSubOp;
	}
	
	/**
	 * Gets the number of multiplication operators the player has
	 * @return the number of multiplication operators the player has
	 */
	public int getNumMultiplicationOperators(){
		return numMultOp;
	}
	
	/**
	 * Gets the number of division operators the player has
	 * @return the number of division operators the player has
	 */
	public int getNumDivisionOperators(){
		return numDivOp;
	}

	/**
	 * Creates player battle state
	 * 
	 */
	public PlayerBattleState() {
		super(true);

	}

	/**
	 * Initializes all internal variables
	 * 
	 * Initializes the answerString, a string that represents the currently typed answer
	 * Sets the currentTarget to null, the entity with a targetable state the player is currently targetting
	 * Sets the targetIndex to -1, the number corresponding to the index in the array of entities inside of battlestate
	 * that the player is currently targetting
	 */
	@Override
	public void init(){
		super.init();

		//Set attributes
		answerString = "";
		currentTarget = null;
		targetIndex = -1;
	}

	/**
	 * Has an object enter PlayerBattleState
	 * Saves the object's world position and aligns the object on the left side of the screen.
	 * Calls MovableGameObject's refresh method to set the previousPosition to the currentPosition.
	 * 		This ensures the object won't revert to its old previousPosition when getting hit by a projectile.
	 * Sets dimensions to battleDimensions, and sets the image to the player's battleImage
	 * Calls toggleTarget to target a valid entity
	 */
	@Override
	public void enter() {
		super.enter();


		
		//Update position
		Vector posVector = new Vector(2);
		posVector.setComponent(0, Directory.screenManager.getPercentageWidth(1.0));
		posVector.setComponent(1, Directory.screenManager.getPercentageHeight(45.0));
		attachedTo.setPos(posVector);

		//Refresh the movable object's previous position due to engine state change
		getAttachedEntity().refresh();


		//Update dimensions
		attachedTo.setWidth(200);
		attachedTo.setHeight(300);

		//SEt players sprite
		getAttachedEntity().setSprite(Directory.spriteLibrary.get("PlayerBattleIdle"));

		//Toggle target to select first target
		toggleTarget();
	}

	/**
	 * Updates the player's BattleState
	 * Loops through all keyPresses since last update
	 * 		If a character is an operator or a number, appends it to the answerString
	 * 		If a character is a newLine buffer, calls answerEquation or submitEquation based on current target
	 * 		If a character is whitespace, togglesTarget and clears answerString
	 * 		If a character is backspace, clears answerString
	 * Updates the attached shape
	 */
	@Override
	public void update() {
		super.update();

		//If at any point there is no target or the target is dead, or the target is no longer targetable find a target
		if(currentTarget == null || currentTarget.getCurrentHealth() <= 0 || !(currentTarget.getState() instanceof TargetableState)){
			toggleTarget();
		}

		//Declare KeyEvent  to retrieve keys in the order that they were pressed since last update
		KeyEvent e;
		//While the next character pressed isn't null
		while((e = Directory.inputManager.getNextKeyPressed()) != null){			//getNextKeyPressed dequeues the next key pressed from a queue of all keypresses
			int chCode = e.getKeyCode();
			//Cast characterCode to character
			char ch = (char)((int)chCode);

			System.out.println(ch);
			
			
			//if ch is an operator which requires shift
			if(e.isShiftDown()){
				//If plus sign
				if(e.getKeyCode() == KeyEvent.VK_EQUALS){
					answerString += '+';
				}
				//Else if asterisk
				else if(e.getKeyCode() == KeyEvent.VK_8){
					answerString += '*';
				}
			}
			//If ch is a number (from number bar) or an operator which doesn't require shift (Non-numpad)
			else if(Character.isDigit(ch) || chCode == KeyEvent.VK_MINUS || chCode == KeyEvent.VK_SLASH){
				//If you start typing an answer and there isn't a target yet, toggle target


				//Add to the answerString
				answerString += ch;
			}

			//Else if chCode is a numeric keycode from the number pad
			else if(chCode <= KeyEvent.VK_NUMPAD9 && chCode >= KeyEvent.VK_NUMPAD0){
				//If you start typing an answer and there isn't a target yet, toggle target
				if(currentTarget == null){
					toggleTarget();
				}

				//Convert key code to value between 0 and 9
				int value = chCode - KeyEvent.VK_NUMPAD0;
				//append answerstring
				answerString += value;

			}

			//Else if chCode is a keycode for an operator from the number pad
			else if(chCode == KeyEvent.VK_ADD || chCode == KeyEvent.VK_SUBTRACT || chCode == KeyEvent.VK_MULTIPLY || chCode == KeyEvent.VK_DIVIDE){
				char val = '+';
				switch(chCode){
				case KeyEvent.VK_ADD:
					val = '+';
					break;
				case KeyEvent.VK_SUBTRACT:
					val = '-';
					break;
				case KeyEvent.VK_MULTIPLY:
					val = '*';
					break;
				case KeyEvent.VK_DIVIDE:
					val = '/';
					break;
				}
				answerString += val;
			}

			//Else if ch is a newline or return
			else if(chCode == KeyEvent.VK_ENTER){
				if(currentTarget != null){
					//Cast current taget's state as targetable
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
					else{
						System.out.println("Target does not have a valid targetable state!");
					}
				}
				else{
					System.out.println("No target selected!");
				}

				//Clear answerString
				answerString = "";

			}
			//Else if ch is down arrow
			else if(chCode == KeyEvent.VK_DOWN || chCode == KeyEvent.VK_S){
				//Toggle current target
				toggleTarget();
			}
			//Else if ch is left arrow
			else if(chCode == KeyEvent.VK_LEFT || chCode == KeyEvent.VK_A){
				//Deselect current target
				if(currentTarget != null)
					((TargetableState)currentTarget.getState()).toggleSelected();
				//Target self
				currentTarget = getAttachedEntity();
				targetIndex = -1;

				//Select self
				((TargetableState)currentTarget.getState()).toggleSelected();
			}
			//Else if ch is right arrow
			else if(chCode == KeyEvent.VK_RIGHT || chCode == KeyEvent.VK_D){
				//Deselect current target
				if(currentTarget != null)
					((TargetableState)currentTarget.getState()).toggleSelected();

				//Target opponent
				currentTarget = ((BattleState)Directory.engine.getCurrentState()).getCompetitor2();
				targetIndex = -1;

				//Select opponent
				((TargetableState)currentTarget.getState()).toggleSelected();
			}
			//Else if backspace was pressed (casts to int 8)
			else if(chCode == KeyEvent.VK_BACK_SPACE){
				//Clear answerString
				answerString = "";
			}
		}

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
		if(targetIndex < gameState.getTargetables().size()){
			currentTarget = gameState.getTargetables().get(targetIndex);
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
				//But first check that the target's state is still there as sometimes 
				//In rare cases a state is removed on a target as this is occurring.
				if(currObjState != null)
					currObjState.toggleSelected();
			}
		}
		else{
			//Are there any destructables
			if(gameState.getTargetables().size() <= 1){
				//No destructables in gameState, set targetIndex to -1 and currentTarget to null
				targetIndex = -1;
				currentTarget = null;
			}
			else{
				//Set targetIndex to 0 and set currentTarget
				targetIndex = 0;
				currentTarget = gameState.getTargetables().get(targetIndex);
				currObjState = (TargetableState)currentTarget.getState();
				//Make sure the current target is alive
				if(currentTarget.getCurrentHealth() <= 0){
					//Make sure there is another target to choose from (else the only target on the screen is dead
					if(gameState.getTargetables().size() > 1){
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

				//Calculate power of answer
				//If the player has that ability
				int pow = getAttachedEntity().getPower();
				if(Directory.profile.isAbilityUnlocked(Abilities.ADDATIVE_DAMAGE)){
					//Multiply power by the number of operators used
					pow *= submission.getNumOperators();
				}
								
				//send answer to current target
				if(targetState.submitAnswer(submission, pow)){
					Directory.profile.incrementEquationsMade();

					//If the target wasn't killed, generate a new equation for it
					if(currentTarget.getCurrentHealth() > 0){
						targetState.generateNewEquation();
					}
				}
				//Else, answer is wrong
				else{
					Directory.profile.incrementWrongAnswers();
					System.out.println("Wrong, answer is " + targetState.getEquation().getSolution() + " your answer was " + submission.getSolution());
				}
			}
			//Else, the player does not have the required operators
			else{
				System.out.println("You do not have enough operators!");
			}
		}
		//Else, an invalid equation was submitted
		else{
			System.out.println("Invalid Equation");
			Directory.profile.incrementWrongAnswers();
		}
	}

	/**
	 * No drawing action needed
	 */
	public void draw(Graphics2D g2d)
	{
		super.draw(g2d);
	}

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
		int submitPower = currentTarget == getAttachedEntity() ? 0 : getAttachedEntity().getPower();

		System.out.println("Submit power: " + submitPower);

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

	/**
	 * AttachedObject Exits the player's battle state
	 */
	@Override
	public void exit() {
		super.exit();
	}

}
