package state.engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;

import objects.GameObject;
import state.object.PopStateButtonState;
import state.object.TextDisplayState;
import engine.Directory;

/**
 * Defines a state which displays to the user
 * how to play the game.
 * @author Nex
 *
 */
public class InstructionsMenuState extends MenuEngineState {

	/**
	 * Constructs an instructionsMenuState
	 */
	public InstructionsMenuState() {
		super();
	}

	/**
	 * Initializes all member variables
	 */
	@Override
	public void init(){
		super.init();		

		//Add instruction image
		GameObject battleInstructions = new GameObject(
				Directory.screenManager.getPercentageWidth(10.0),
				Directory.screenManager.getPercentageHeight(15.0) - Directory.screenManager.getPercentageHeight(2.5),
				Directory.screenManager.getPercentageWidth(75.0),
				Directory.screenManager.getPercentageHeight(75.0)
				);
		battleInstructions.setSprite(Directory.spriteLibrary.get("Instructions"));
		battleInstructions.setVisible(true);
		addObj(battleInstructions);
		
	}

	/**
	 * No specialinstructions needed when entering instruction
	 * menu state
	 */
	@Override
	public void enter() {
		// TODO Auto-generated method stub

	}

	/**
	 * Updates every object in this state,
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
	 * No special instructions for exiting this state needed
	 */
	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
