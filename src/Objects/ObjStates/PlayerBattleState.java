package Objects.ObjStates;

import java.util.ArrayList;
import java.util.LinkedList;

import Engine.Directory;
import Engine.States.BattleState;
import Equations.*;
import Objects.Entity;

//State which governs the player during a battle
public class PlayerBattleState extends ObjState{

	//attributes
	private int numAddOp;
	private int numSubOp;
	private int numMultOp;
	private int numDivOp;
	private String answerString;
	private Entity currentTarget;
	private int targetIndex;
	private Entity player;

	public PlayerBattleState() {
		super();

		//Set attributes
		answerString = "";
		currentTarget = null;
		targetIndex = -1;
	}

	@Override
	public void enter() {
		//downcast attachedTo gameobject to player
		if(attachedTo instanceof Entity){
			player = (Entity)attachedTo;
		}

		//Toggle target to select first target
		toggleTarget();

	}

	@Override
	public void update() {
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
				if(currentTarget.holdsEquation()){
					answerEquation();
				}else{
					
					submitEquation();
				}
				
			}
			//Else if ch is whitespace
			else if(Character.isWhitespace(ch)){
				//Toggle current target
				toggleTarget();
			}
			//Else if backspace was pressed (casts to int 8)
			else if((int)ch == 8){
				//Remove the last character from the answer string
				answerString = answerString.substring(0, answerString.length()-1);
			}
		}

		//Update shape in case of moving
		attachedTo.updateShape();

	}

	
	//Changes the current target. If there is already a current target it will set it to a deselected state.
	//If the next target index is still within the bounds of the list, and the next one is alive, it will set it as the current target.
	//If it turns out the next target isn't alive it simply toggles targets again after setting the current target to null so as not to select it
	
	private void toggleTarget(){
		//Change targets
		//If the current target isn't null and it is selected
		if(currentTarget != null && currentTarget.getEquationObj().isSelected()){
			//Toggle selected attribute of current target(deselect)
			currentTarget.getEquationObj().toggleSelected();
		}

		//Increment target index
		targetIndex++;

		//Get the current battlestate
		BattleState gameState = (BattleState)Directory.engine.getCurrentState();

		//If targetIndex is within the bounds of the 
		//battlestate's array of destructables assign the next index as the current target
		if(targetIndex < gameState.getEntities().size()){
			currentTarget = gameState.getEntities().get(targetIndex);
			//Make sure the current target is alive
			if(currentTarget.getCurrentHealth() <= 0){
				//If not toggle target again
				//But first set current target not to null so the state on the new current target does not get selected
				currentTarget = null;
				toggleTarget();
				
			}
			else
			{
				//Set target as selected
				currentTarget.getEquationObj().toggleSelected();
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
					currentTarget.getEquationObj().toggleSelected();
				}
			}
		}


	}
	
	//Takes your current answerString, parses it into an equation, and submits it to the current target
	private void submitEquation(){
		//Create a list of expressions
		ArrayList<Expression> expressionList = new ArrayList<Expression>();
		
		//Create a queue of integers
		LinkedList<Integer> digits = new LinkedList<Integer>();
		//for each character in the answer string
		for(int i = 0; i < answerString.length(); i++){
			if((Character.isDigit(answerString.charAt(i)))){
				String digit = answerString.substring(i, i+1);
				//Add the digit to the queue
				digits.addLast(Integer.parseInt(digit));
				
				//Make sure the next character is a digit, or there is a next character
				if(answerString.length() - 1 == i || !Character.isDigit(answerString.charAt(i+1))){
					//If it's not, get the integer represented by the digits
					int val = ListToInt(digits);
					
					//Add a numerical expression to the expression list
					expressionList.add(new NumericalExpression(val));
					//Clear the list of digits
					digits.clear();
				}
			}
			else if(answerString.charAt(i) == '+'){
				expressionList.add(new AdditionExpression());
			}
			else if(answerString.charAt(i) == '-'){
				expressionList.add(new SubtractionExpression());
			}
			else if(answerString.charAt(i) == '*'){
				expressionList.add(new MultiplicationExpression());
			}
			else if(answerString.charAt(i) == '/'){
				expressionList.add(new DivisionExpression());
			}
		}
		
		//Declare the submission equation
		Equation submission = null;
		
		try{
			//Compile the equation
			submission = new Equation(expressionList);
		}
		catch(InvalidEquationException iEE){
			//Improper equation
			//Give feedback
			System.out.println(iEE.getMessage());
			submission = null;
		}
		
		
		//If the submission is valid
		if(submission!=null){
			//send answer to current target
			if(currentTarget.submitAnswer(submission, player.getPower())){
				Directory.profile.incrementEquationsMade();
				
				//Clear answerString
				answerString = "";

				//Check if the Entity was killed
				if(currentTarget.getCurrentHealth() <= 0){
					//Toggle target
					toggleTarget();
				}
				else{
					currentTarget.getEquationObj().generateNewEquation();
					
				}
			}
			else{
				Directory.profile.incrementWrongAnswers();
			}
		}
		else{
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
		
		//send answer to current target
		if(currentTarget.submitAnswer(new Equation(answer), submitPower)){
			
			//TODO: next if is Probably redundant, current target has equation if this method is called. 
			//But will keep it to prevent misuse for now.
			//IF the current target held an equation
			if(currentTarget.holdsEquation())
			{
				//Get the expressionList
				ArrayList<Expression>expList = currentTarget.getEquationObj().getEquation().getExpressionList();
				//iterate through it
				for(Expression e : expList)
				{
					//If the expression is an operator
					if(e instanceof OperatorExpression)
					{
						//Discern what type of operator, and then add to the players corresponding count of operators
						if(e instanceof AdditionExpression){
							numAddOp++;
							System.out.println("Addition: " + numAddOp);
						}
						else if(e instanceof SubtractionExpression){
							numSubOp++;
							System.out.println("Subtraction: " + numSubOp);
						}
						else if(e instanceof MultiplicationExpression){
							numMultOp++;
							System.out.println("Multiplication: " + numMultOp);
						}
						else if(e instanceof DivisionExpression){
							numDivOp++;
							System.out.println("Division: " + numDivOp);
						}
					}
				}
			}
			
			//Increment profile stats
			Directory.profile.incrementEquationsSolved();
			
			//Clear answerString
			answerString = "";

			//Check if the Entity was killed
			if(currentTarget.getCurrentHealth() <= 0){
				//Toggle target
				toggleTarget();
			}
			else{
				currentTarget.getEquationObj().generateNewEquation();
				
			}
		}
		else{
			//Increment profile stats
			Directory.profile.incrementWrongAnswers();
			
			//Clear the answer string
			answerString = "";
		}
	}

	@Override
	public void exit() {

	}

}
