package state.engine;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;

import engine.Directory;
import objects.Entity;
import objects.GameObject;
import state.object.BattleResultDisplayState;
import state.object.HealthBarState;
import state.object.OperatorDisplayState;
import state.object.PlayerBattleState;
import state.object.PopStateButtonState;
import state.object.TargetEquationDisplayState;

//TODO: Clean up to keep track of two entities and a list of projectile gameObjects.
//TODO: Have all state-Ending and cleanup logic in here instead of PlayerBattleState and EnemyBattleState!
/**
 * A class which defines the engine state which runs during a battle
 * @author Nex
 *
 */
public class BattleState extends EngineState{

	//Attributes
	public static int difficulty = 1;
	private Entity competitor1;
	private Entity competitor2;
	private ArrayList<Entity> targetables;
	private boolean isBattleOver;

	//Accessors / Modifiers
	/**
	 * GEts the list of targetables in this state
	 * Does not include competitors
	 * @return A list of entities with targetable states
	 */
	public ArrayList<Entity> getTargetables(){
		return targetables;
	}
	
	/**
	 * Get the first entity partaking in this battle
	 * @return Entity representing the player
	 */
	public Entity getCompetitor1(){
		return competitor1;
	}
	
	/**
	 * Get the second entity partaking in this battle
	 * @return Entity representing the player's opponent
	 */
	public Entity getCompetitor2(){
		return competitor2;
	}

	/**
	 * Constructs battle state
	 * Calls super constructor -> Makes call to init()
	 */
	public BattleState(Entity triggeredBy, Entity attachedTo) {
		super();

		//Assign member variables
		competitor1 = triggeredBy;
		competitor2 = attachedTo;

		//Add the competitors to the list of objects
		//Doing this here avoids them from being put in the list of targetables
		//As these will be targeted directly
		objects.add(competitor1);
		objects.add(competitor2);
		
		isBattleOver = false;
		
		
	}

	/**
	 * Initializes member variables
	 */
	@Override
	protected void init() {
		//Initialize super
		super.init();

		//Initialize array list of entities
		targetables = new ArrayList<Entity>();

		
	}
	
	/**
	 * Uponentering this state the hud must be setup
	 */
	@Override
	public void enter() {
		//Set hud up
		initHud();
	}

	/**
	 * Creates the hud for this state
	 */
	private void initHud(){
		//First clear hud
		Directory.screenManager.clearHud();
		
		//create healthbar
		GameObject healthBar = new GameObject(5, 15, 250, 25);
		healthBar.setShape(new Rectangle2D.Double(), Color.red);
		healthBar.setVisible(true);
		healthBar.pushState(new HealthBarState(competitor1, true));
		
		Directory.screenManager.AddObjToHud(healthBar);
		
		//Create opponent healthbar
		GameObject healthBar2 = new GameObject(530, 15, 250, 25);
		healthBar2.setShape(new Rectangle2D.Double(), Color.red);
		healthBar2.setVisible(true);
		healthBar2.pushState(new HealthBarState(competitor2, false));
		
		Directory.screenManager.AddObjToHud(healthBar2);
		
		
		//Create Equation display gameobject
		GameObject equationDisplay = new GameObject(0, Directory.screenManager.getPercentageHeight(20.0),
				Directory.screenManager.getPercentageWidth(100.0), Directory.screenManager.getPercentageHeight(10.0));
		equationDisplay.setShape(new Rectangle2D.Double(), new Color(230, 230, 230, 200));
		equationDisplay.setVisible(true);
		equationDisplay.pushState(new TargetEquationDisplayState());
		
		Directory.screenManager.AddObjToHud(equationDisplay);
		
		
		//Create operator displays
		//Addition
		GameObject additionDisplay = new GameObject(Directory.screenManager.getPercentageWidth(20.0), Directory.screenManager.getPercentageHeight(85.0),
				Directory.screenManager.getPercentageWidth(7.0), Directory.screenManager.getPercentageHeight(15.0));
		additionDisplay.setShape(new Rectangle2D.Double(), new Color(255, 0, 0, 255));
		additionDisplay.setSprite(Directory.spriteLibrary.get("CardAddition"));
		additionDisplay.getSprite().queueAnimation(0, true);
		additionDisplay.setVisible(true);
		additionDisplay.pushState(new OperatorDisplayState("+"){

			@Override
			public Integer invoke() {
				return ((PlayerBattleState)Directory.profile.getPlayer().getState()).getNumAdditionOperators();
			}
			
		});
		
		Directory.screenManager.AddObjToHud(additionDisplay);
		
		
		//Subtraction
		GameObject subtractionDisplay = new GameObject(Directory.screenManager.getPercentageWidth(40.0), Directory.screenManager.getPercentageHeight(85.0),
				Directory.screenManager.getPercentageWidth(7.0), Directory.screenManager.getPercentageHeight(15.0));
		subtractionDisplay.setShape(new Rectangle2D.Double(), new Color(255, 0, 0, 255));
		subtractionDisplay.setSprite(Directory.spriteLibrary.get("CardSubtraction"));
		subtractionDisplay.getSprite().queueAnimation(0, true);
		subtractionDisplay.setVisible(true);
		subtractionDisplay.pushState(new OperatorDisplayState("-"){

			@Override
			public Integer invoke() {
				// TODO Auto-generated method stub
				return ((PlayerBattleState)Directory.profile.getPlayer().getState()).getNumSubtractionOperators();
			}
			
		});
		
		Directory.screenManager.AddObjToHud(subtractionDisplay);
		
		
		//Multiplication
		GameObject multiplicationDisplay = new GameObject(Directory.screenManager.getPercentageWidth(60.0), Directory.screenManager.getPercentageHeight(85.0),
				Directory.screenManager.getPercentageWidth(7.0), Directory.screenManager.getPercentageHeight(15.0));
		multiplicationDisplay.setShape(new Rectangle2D.Double(), new Color(255, 0, 0, 255));
		multiplicationDisplay.setSprite(Directory.spriteLibrary.get("CardMultiplication"));
		multiplicationDisplay.getSprite().queueAnimation(0, true);
		multiplicationDisplay.setVisible(true);
		multiplicationDisplay.pushState(new OperatorDisplayState("*"){

			@Override
			public Integer invoke() {
				// TODO Auto-generated method stub
				return ((PlayerBattleState)Directory.profile.getPlayer().getState()).getNumMultiplicationOperators();
			}
			
		});
		
		Directory.screenManager.AddObjToHud(multiplicationDisplay);
		
		
		//Division
		GameObject divisionDisplay = new GameObject(Directory.screenManager.getPercentageWidth(80.0), Directory.screenManager.getPercentageHeight(85.0),
				Directory.screenManager.getPercentageWidth(7.0), Directory.screenManager.getPercentageHeight(15.0));
		divisionDisplay.setShape(new Rectangle2D.Double(), new Color(255, 0, 0, 255));
		divisionDisplay.setSprite(Directory.spriteLibrary.get("CardDivision"));
		divisionDisplay.getSprite().queueAnimation(0, true);
		divisionDisplay.setVisible(true);
		divisionDisplay.pushState(new OperatorDisplayState("/"){

			@Override
			public Integer invoke() {
				// TODO Auto-generated method stub
				return ((PlayerBattleState)Directory.profile.getPlayer().getState()).getNumDivisionOperators();
			}
			
		});
		
		Directory.screenManager.AddObjToHud(divisionDisplay);
		
		
	}
	

	/**
	 * Updates this state
	 * The Game will update every game object in the current state
	 * Then it will remove any game objects that need to be removed from the current state
	 * Then it will add any game objects that need to be added to the current state
	 */
	@Override
	public void update() {
		if(!isBattleOver){
			//For every game object in gameObjects
			for(GameObject obj : objects)
			{
				obj.update();
			}
			
			//Update competitors
			competitor1.update();
			competitor2.update();

			//Check for collisions
			Directory.collisionManager.update();

			//remove every game object in toRemove
			for(GameObject obj : toRemove){
				objects.remove(obj);
				//if obj is a destructable
				if(obj instanceof Entity){
					//Remove from destructables
					targetables.remove((Entity)obj);
				}
			}
			toRemove.clear();



			//Get copyList
			ArrayList<GameObject> copyList = new ArrayList<GameObject>(toAdd);
			//add every game object in toAdd
			for(GameObject obj : copyList)
			{
				objects.add(obj);
				//if obj is a entity
				if(obj instanceof Entity){
					//Add to entities
					targetables.add((Entity)obj);
				}
			}
			toAdd.removeAll(copyList);

			//Check if the battle is over
			if(isBattleOver()){
				isBattleOver = true;
				
				// if player has won, give gold
				if(isPlayerWinner()){
					//Add gold to the player based on loser's stats
					Directory.profile.addGold(competitor2.getTotalHealth() * competitor2.getPower() * competitor2.getDefense());
				}
				
				displayResults();
			}
		}
	}

	/**
	 * Draws this state
	 * Calls draw on all gameObjects in the state's list of gameobjects
	 */
	@Override
	public void draw(Graphics2D g2d){

		ArrayList<GameObject> drawList = getObjListCopy();

		//For every game object in objects
		for(GameObject obj : drawList)
		{
			obj.draw(g2d);
		}
		
		//Draw competitors
		competitor1.draw(g2d);
		competitor2.draw(g2d);		
	}

	/**
	 * Determines if the battle is over
	 * @return true if one of the two competitors are dead, else false
	 */
	private boolean isBattleOver(){
		if(competitor1.getCurrentHealth() <=0 || competitor2.getCurrentHealth() <= 0) return true;
		return false;
	}
	
	/**
	 * Determines if player has won yet
	 * @return true if player is winner
	 */
	private boolean isPlayerWinner(){
		// if player's health is <=0, player has not won
		if(competitor1.getCurrentHealth() <= 0){
			return false;
		}
		// else if enemy's health is <=0, player has won
		else if(competitor2.getCurrentHealth() <= 0){
			return true;
		}
		
		// else, player has not won yet
		return false;
	}
	
	/**
	 * Displays the results of the battle on the HUD
	 * And adds an end battle state button to hud
	 */
	private void displayResults(){
		//Create display results object
		GameObject results = new GameObject(
				Directory.screenManager.getPercentageWidth(5), 
				Directory.screenManager.getPercentageHeight(5), 
				Directory.screenManager.getPercentageWidth(90),
				Directory.screenManager.getPercentageHeight(80));
		
		results.setShape(new RoundRectangle2D.Double(0, 0, 0, 0, 15, 15), new Color(255, 180, 0, 200));
		results.setVisible(true);
		results.setState(new BattleResultDisplayState(/*competitor1.getCurrentHealth() > 0*/));
		
		Directory.screenManager.AddObjToHud(results);
		
		//Create end battle button
		GameObject endButton = new GameObject(
				Directory.screenManager.getPercentageWidth(40), 
				Directory.screenManager.getPercentageHeight(90), 
				Directory.screenManager.getPercentageWidth(20), 
				Directory.screenManager.getPercentageHeight(5));
		
		endButton.setShape(new Rectangle2D.Double(), Color.red);
		endButton.setVisible(true);
		endButton.setState(new PopStateButtonState("Continue"));
		
		Directory.screenManager.AddObjToHud(endButton);
	}



	/**
	 * Resolves battleState, moving back to overworld state
	 * The competitor which died will be removed from the current state.
	 * At this point this state has been popped from the stateStack
	 */
	@Override
	public void exit() {
		//Determine winner and loser
		Entity loser;
		Entity winner;
		
		if(isPlayerWinner()){
			loser = competitor2;
			winner = competitor1;
		} else {
			loser = competitor1;
			winner = competitor2;
		}		
		
/*
		if(competitor1.getCurrentHealth() <= 0){
			loser = competitor1;
			winner = competitor2;
		}
		else{
			loser = competitor2;
			winner = competitor1;
		}

		//If the player is the winner
		if(Directory.profile.getPlayer() == winner){
			//Add gold to the player based on losers stats
			Directory.profile.addGold(loser.getTotalHealth() * loser.getPower() * loser.getDefense());
		}
*/
		//Pop the state of the winner (Revert to whatever state was prior the battle)
		winner.popState();

		//Loser is no longer running or visible
		//loser.setRunning(false);
		loser.setState(null);
		loser.setVisible(false);
		
		//Clear hud
		Directory.screenManager.clearHud();

		//Pop state of engine reverting back to whatever was previous
		//Directory.engine.popState();

		//Remove the loser from the engine's currentState
		Directory.engine.getCurrentState().removeObj(loser);
	}
}