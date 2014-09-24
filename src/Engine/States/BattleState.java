package Engine.States;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import Engine.Directory;
import Objects.Destructable;
import Objects.Entity;
import Objects.GameObject;


public class BattleState extends State{

	//Attributes
	public static int difficulty = 1;
	private ArrayList<Entity> entities;
	
	

	public BattleState() {
		super();

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
	//TODO: Clean up code to use all original lists, there should be no conflict with adding or removing to or from these lists
	//while their processes are going on.
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
			//if obj is a destructable
			if(obj instanceof Entity){
				//Add to destructables
				entities.add((Entity)obj);
			}
		}
		toAdd.removeAll(copyList);

	}
	
	//Draws this state
	@Override
	public void draw(Graphics2D g2d){
		
		ArrayList<GameObject> drawList = getObjListCopy();
		
		//For every game object in objects
		for(GameObject obj : drawList)
		{
			obj.draw(g2d);
		}
	}
}