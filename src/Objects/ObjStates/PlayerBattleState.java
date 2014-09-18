package Objects.ObjStates;

import Engine.Directory;
import Engine.States.BattleState;
import Objects.Destructable;
import Objects.Entity;

//State which governs the player during a battle
public class PlayerBattleState extends ObjState{

	//attributes
	private String answerString;
	private Destructable currentTarget;
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
			if(Character.isDigit(ch) || ch == (int)'-'){
				//Add to the answerString
				answerString += ch;
				System.out.println(answerString);
			}
			//Else if ch is a newline or return
			else if((int)ch == (int)'\n' || (int)ch == (int)'\r'){
				//Attempt solution
				//Parse answerString as an integer
				int answer = Integer.parseInt(answerString);

				//send answer to current target
				if(currentTarget.submitAnswer(answer, player.getPower())){
					//Answered correct!
					System.out.println("Right");

					//Clear answerString
					answerString = "";

					//Check if the destructable was killed
					if(currentTarget.getHealth() <= 0){
						//Toggle target
						toggleTarget();
					}
				}
				else{
					//Not answered correct
					System.out.println("Wrong");
					//Clear the answer string
					answerString = "";
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

	private void toggleTarget(){
		//Change targets
		//If the current target isn't null
		if(currentTarget != null){
			//Toggle selected attribute of current target(deselect)
			currentTarget.toggleSelected();
		}

		//Increment target index
		targetIndex++;

		//Get the current battlestate
		BattleState gameState = (BattleState)Directory.engine.getCurrentState();

		//If targetIndex is within the bounds of the 
		//battlestate's array of destructables assign the next index as the current target
		if(targetIndex < gameState.getDestructables().size()){
			currentTarget = gameState.getDestructables().get(targetIndex);
			//Make sure the current target is alive
			if(currentTarget.getHealth() <= 0){
				//If not toggle target again
				toggleTarget();
			}
			else
			{
				//Set target as selected
				currentTarget.toggleSelected();
			}
		}
		else{
			//Are there any destructables
			if(gameState.getDestructables().size() == 0){
				//No destructables in gameState, set targetIndex to -1 and currentTarget to null
				targetIndex = -1;
				currentTarget = null;
			}
			else{
				//Set targetIndex to 0 and set currentTarget
				targetIndex = 0;
				currentTarget = gameState.getDestructables().get(targetIndex);
				//Make sure the current target is alive
				if(currentTarget.getHealth() <= 0){
					//Make sure there is another target to choose from (else the only target on the screen is dead
					if(gameState.getDestructables().size() > 1){
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
					currentTarget.toggleSelected();
				}
			}
		}


	}

	@Override
	public void exit() {

	}

}
