package state.engine;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Objects.GameObject;
import state.State;

/**
 * Defines a state which will be used in the Engine's finite state machine.
 * These states must keep track of a list of gameObjects to update, draw, add, and remove each
 * update cycle.
 * @author Nex
 *
 */
public abstract class EngineState extends State {

	//Attributes
	protected ArrayList<GameObject> objects;			//Current list of objs in gameState
	protected ArrayList<GameObject> toRemove;			//Current list of objs being removed this update loop
	protected ArrayList<GameObject> toAdd;				//Current list of objs being added this update loop
	
	public EngineState() {
		super();
	}

	@Override
	protected void init() {
		
		objects = new ArrayList<GameObject>();
		//Initialize array lists
		toRemove = new ArrayList<GameObject>();
		toAdd = new ArrayList<GameObject>();
	}

	
	
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
	
	@Override
	abstract public void enter();

	@Override
	abstract public void update();

	@Override
	abstract public void draw(Graphics2D g2d);
	
	@Override
	abstract public void exit();

}
