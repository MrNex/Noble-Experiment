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
	private ArrayList<Destructable> destructables;
	
	

	public BattleState() {
		super();

	}

	@Override
	protected void init() {
		//Initialize super
		super.init();
		
		//Initialize array list of Destructables
		destructables = new ArrayList<Destructable>();
		
	}

	
	public ArrayList<Destructable> getDestructables(){
		return destructables;
	}
	
	//Updates this state
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
			if(obj instanceof Destructable){
				//Remove from destructables
				destructables.remove((Destructable)obj);
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
			if(obj instanceof Destructable){
				//Add to destructables
				destructables.add((Destructable)obj);
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