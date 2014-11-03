package Engine.States;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Objects.Entity;
import Objects.GameObject;


public abstract class State {

	//Attributes
	protected ArrayList<GameObject> objects;			//Current list of objs in gameState
	protected ArrayList<GameObject> toRemove;			//Current list of objs being removed this update loop
	protected ArrayList<GameObject> toAdd;				//Current list of objs being added this update loop
	
	public State() {
		init();
		
	}
	
	protected void init(){
		objects = new ArrayList<GameObject>();
		//Initialize array lists
		toRemove = new ArrayList<GameObject>();
		toAdd = new ArrayList<GameObject>();
	}
	
	/**
	 * This method is called upon this state reaching the top of a stateStack
	 */
	abstract public void enter();
	
	public abstract void update();
	public abstract void draw(Graphics2D g2d);
	
	/**
	 * This method is called when this state is removed from the top of a stateStack, whether pushed down or popped off.
	 */
	abstract public void exit();
	
	
	//Methods
	public ArrayList<GameObject> getObjListCopy ()
	{
		return new ArrayList<GameObject>(objects);
	}
	
	public void addObj(GameObject objToAdd){
		toAdd.add(objToAdd);
	}
	
	public void removeObj(GameObject objToRemove){
		toRemove.add(objToRemove);
	}

}
