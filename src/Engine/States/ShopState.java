package Engine.States;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;

import Engine.Directory;
import Engine.Manager.ScreenManager;
import Objects.Entity;
import Objects.GameObject;

//TODO: add button, button selection, as means of leaving

/**
 * A class which defines the engine state which runs when the player enters the shop
 * @author Robert Schrupp
 *
 */
public class ShopState extends State {
	
	//Attributes
	private Entity player;
	private GameObject shop;
	private ArrayList<Entity> entities;
	
	//Accessors / Modifiers
	/**
	 * GEts the list of entities in this state
	* @return
	*/
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	
	/**
	 * Constructs shop state
	 * Calls super constructor -> Makes call to init()
	 */
	public ShopState(Entity triggeredBy, GameObject attachedTo) {
		super();
		
		//Assign member variables
		player = triggeredBy;
		shop = attachedTo;
		
		//Setup shop
		initShop();		
	}
	
	/**
	 * Initializes member variables
	 */
	@Override
	protected void init() {
		//Initialize super
		super.init();
		
		//Initialize array list of Destructible
		entities = new ArrayList<Entity>();
	}
	
	/**
	 * Creates the shop for this state
	 */
	private void initShop(){
		//
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

		//Check for collisions
		Directory.collisionManager.update();

		//remove every game object in toRemove
		for(GameObject obj : toRemove){
			objects.remove(obj);
			//if obj is a destructable
			if(obj instanceof Entity){
				//Remove from destructables
				entities.remove((Entity)obj);
			}
		}
		toRemove.clear();



		//Get copyList
		ArrayList<GameObject> copyList = new ArrayList<GameObject>(toAdd);
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

		//Check if leaving shop
		if(isLeaving()){
			leaveShop();
		}

	}

	/**
	 * Draws this state
	 * Calls draw on all gameObjects in the state's list of gameobjects
	 */
	@Override
	public void draw(Graphics2D g2d) {
		ArrayList<GameObject> drawList = getObjListCopy();

		//For every game object in objects
		for(GameObject obj : drawList)
		{
			//System.out.println("Drawing at: " + obj.getPos().toString() + "\nWidtn, Height: " + obj.getWidth() + ", " + obj.getHeight() + "\nVisibility: " + obj.isVisible() + "\nRunning: " + obj.isRunning());
			obj.draw(g2d);
		}
	}
	
	/**
	 * Determines if player is leaving the shop
	 */
	private boolean isLeaving(){
		
		return false;
	}
	
	/**
	 * Resolves shopState and pops state off stack, moving back to overworld state
	 */
	private void leaveShop(){
		player.popState();
		shop.popState();
		
		Directory.engine.popState();
	}
}
