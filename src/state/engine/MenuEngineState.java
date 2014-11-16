package state.engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import engine.Directory;
import objects.GameObject;
import state.object.PopStateButtonState;

/**
 * Defines a state of the engine made up of buttons
 * 
 * Each time this state updates it simply updates it's components
 * and clears the keyboard input (because it is unused in this state)
 * It also adds and removes any queued objects to/from the state
 * 
 * @author Nex
 *
 */
public class MenuEngineState extends EngineState {

	/**
	 * Constructs a menu engie state
	 */
	public MenuEngineState() {
		super();
	}
	
	@Override
	public void init(){
		super.init();		
		
		//Create exit button
		GameObject exitButton = new GameObject(
				Directory.screenManager.getPercentageWidth(85.0), Directory.screenManager.getPercentageHeight(5.0),
				Directory.screenManager.getPercentageWidth(10.0), Directory.screenManager.getPercentageHeight(5.0));

		exitButton.setShape(new Rectangle2D.Double(), Color.gray);
		exitButton.setVisible(true);

		exitButton.setState(new PopStateButtonState("Quit"));

		//Add exit button
		addObj(exitButton);
	}

	/**
	 * No special instructions needed to entering menustate
	 */
	@Override
	public void enter() {
		// TODO Auto-generated method stub

	}

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

	/**
	 * Draws every gameObject in the list of objects
	 */
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
	 * No special instructions needed to exiting menus
	 */
	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
