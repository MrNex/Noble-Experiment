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
import Objects.ObjStates.ButtonState;

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

	/**
	 * Constructs shop state
	 * Calls super constructor -> Makes call to init()
	 */
	public ShopState(Entity triggeredBy, GameObject attachedTo) {
		super();

		//Assign member variables
		player = triggeredBy;
		shop = attachedTo;

	}

	/**
	 * Initializes member variables
	 * 
	 * creates background object and adds it
	 * Creates exit button object and adds it
	 */
	@Override
	protected void init() {
		//Initialize super
		super.init();

		// Create custom background as gameObject
		GameObject background = new GameObject(0, 0, Directory.screenManager.getPercentageWidth(100.0), Directory.screenManager.getPercentageHeight(100.0));
		// set background values
		background.setVisible(true);

		//Create sprite for background out of BackgroundForest 1 image
		//Set sprite of background
		background.setSprite(Directory.spriteLibrary.get("Background_Forest_1"));

		//Add background
		addObj(background);


		//Create exit button
		GameObject exitButton = new GameObject(
				Directory.screenManager.getPercentageWidth(85.0), Directory.screenManager.getPercentageHeight(5.0),
				Directory.screenManager.getPercentageWidth(10.0), Directory.screenManager.getPercentageHeight(5.0));

		//Debug
		System.out.println("Exit button pos: " + exitButton.getPos().toString());
		System.out.println("Percentage 5 height: " + Directory.screenManager.getPercentageHeight(5.0));
		
		exitButton.setShape(new Rectangle2D.Double(), Color.white);
		exitButton.setVisible(true);
		exitButton.setState(new ButtonState("Quit"){

			/**
			 * This button will do nothing on hover
			 */
			@Override
			protected void onHover() {
				// TODO Auto-generated method stub
				System.out.println("Hovering!");
			}

			/**
			 * The action of this button will call leaveShop
			 */
			@Override
			protected void action() {
				ShopState state = (ShopState)Directory.engine.getCurrentState();
				state.leaveShop();

			}

		});

		//Add exit button
		addObj(exitButton);
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
		}
		toRemove.clear();



		//add every game object in toAdd
		for(GameObject obj : toAdd)
		{
			objects.add(obj);
		}
		toAdd.clear();

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
			obj.draw(g2d);
		}
	}

	/**
	 * Resolves shopState and pops state off stack, moving back to overworld state
	 */
	private void leaveShop(){
		player.popState();
		//shop.popState();		Do not pop the state twice, it will cause the game to crash with empty stack exception

		Directory.engine.popState();
	}
}
