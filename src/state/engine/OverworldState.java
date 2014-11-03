package state.engine;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Engine.Directory;
import Objects.GameObject;

public class OverworldState extends EngineState {

	public OverworldState() {
		super();
		
	}
	
	@Override
	public void init(){
		super.init();
	}

	/**
	 * Updates every object in this state,
	 * Checks for collsions
	 * removes objects from the state which must be removed
	 * Adds objects to the state that must be added
	 * And clears the input manager
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
		}
		toRemove.removeAll(copyList);

		//Check for collisions
		Directory.collisionManager.update();
		
		//Get copyList
		copyList = new ArrayList<GameObject>(toAdd);
		//add every game object in toAdd
		for(GameObject obj : copyList)
		{
			objects.add(obj);
		}
		toAdd.removeAll(copyList);

		//Clear the queue of keypresses (Since it is not being used in this state)
		Directory.inputManager.clear();

	}

	@Override
	public void draw(Graphics2D g2d) {
		ArrayList<GameObject> drawList = getObjListCopy();

		//For every game object in objects
		for(GameObject obj : drawList)
		{
			obj.draw(g2d);
		}

	}

	/**
	 * This state does not need to do anything upon entering
	 */
	@Override
	public void enter() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This state does nothing upon exit
	 */
	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

}
