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
	
	public abstract void update();
	public abstract void draw(Graphics2D g2d);
	
	
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
