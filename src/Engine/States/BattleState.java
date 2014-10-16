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
	public BattleState() {
		super();

		//competitor1 = e1;
		//competitor2 = e2;
		
	}

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

	//Updates this state
	//The Game will update every game object in the current state
	//Then it will remove any game objects that need to be removed from the current state
	//Then it will add any game objects that need to be added to the current state
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
		//if(isBattleOver()){
			//endBattle();
		//}
		
	}

	//Draws this state
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
	 * @return
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
		//Determine who lost
		Entity loser = competitor1.getCurrentHealth() <= 0 ? competitor1 : competitor2;
		
		
	}
}