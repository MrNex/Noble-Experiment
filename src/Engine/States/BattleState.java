package Engine.States;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import Engine.Directory;
import Objects.Entity;
import Objects.GameObject;

//TODO: Clean up to keep track of two entities and a list of projectile gameObjects.
//TODO: Have all state-Ending and cleanup logic in here instead of PlayerBattleState and EnemyBattleState!
/**
 * A class which defines the engine state which runs during a battle
 * @author Nex
 *
 */
public class BattleState extends State{

	//Attributes
	public static int difficulty = 1;
	private Entity competitor1;
	private Entity competitor2;
	private ArrayList<Entity> entities;


	/**
	 * Constructs battle state
	 * Calls super constructor -> Makes call to init()
	 */
	public BattleState(Entity triggeredBy, Entity attachedTo) {
		super();

		//Assign member variables
		competitor1 = triggeredBy;
		competitor2 = attachedTo;

	}

	/**
	 * Initializes member variables
	 */
	@Override
	protected void init() {
		//Initialize super
		super.init();

		//Initialize array list of Destructables
		entities = new ArrayList<Entity>();

	}


	public ArrayList<Entity> getEntities(){
		return entities;
	}

	/**
	 * Updates this state
	 * The Game will update every game object in the current state
	 * Then it will remove any game objects that need to be removed from the current state
	 * Then it will add any game objects that need to be added to the current state
	 */
	@Override
	public void update() {
		//For every game object in gameObjects
		for(GameObject obj : objects)
		{
			obj.update();
		}

		//Get copyList (Avoid concurrent modification exceptions)
		ArrayList<GameObject>copyList = new ArrayList<GameObject>(toRemove);
		//remove every game object in toRemove
		for(GameObject obj : copyList)
		{
			objects.remove(obj);
			//if obj is a destructable
			if(obj instanceof Entity){
				//Remove from destructables
				entities.remove((Entity)obj);
			}
		}
		toRemove.removeAll(copyList);

		//Get copyList
		copyList = new ArrayList<GameObject>(toAdd);
		//add every game object in toAdd
		for(GameObject obj : copyList)
		{
			objects.add(obj);
			//if obj is a entity
			if(obj instanceof Entity){
				//Add to entities
				entities.add((Entity)obj);
			}
		}
		toAdd.removeAll(copyList);

		//Check if the battle is over
		if(isBattleOver()){
			endBattle();
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
			//System.out.println("Drawing at: " + obj.getPos().toString() + "\nWidtn, Height: " + obj.getWidth() + ", " + obj.getHeight() + "\nVisibility: " + obj.isVisible() + "\nRunning: " + obj.isRunning());
			obj.draw(g2d);
		}
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
	 * Resolves battleState and pops state off stack, moving back to overworld state
	 * The competitor which died will be removed from the underlying state.
	 */
	private void endBattle(){
		//Determine winner and loser
		Entity loser;
		Entity winner;

		if(competitor1.getCurrentHealth() <= 0){
			loser = competitor1;
			winner = competitor2;
		}
		else{
			loser = competitor2;
			winner = competitor1;
		}
		
		//Pop the state of the winner (Revert to whatever state was prior the battle)
		winner.popState();
		
		//Loser is no longer running or visible
		//loser.setRunning(false);
		loser.setState(null);
		loser.setVisible(false);
		
		//Pop state of engine reverting back to whatever was previous
		Directory.engine.popState();
		
		//Remove the loser from the engine's currentState
		Directory.engine.getCurrentState().removeObj(loser);

	}
}