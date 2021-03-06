package state.engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;

import engine.Directory;
import engine.manager.ProfileManager;
import engine.manager.ProfileManager.Abilities;
import engine.manager.ScreenManager;
import objects.Entity;
import objects.GameObject;
import state.object.AbilityPurchaseButtonState;
import state.object.ButtonState;
import state.object.DefenseUpgradeButtonState;
import state.object.GoldTextDisplayState;
import state.object.HealthUpgradeButtonState;
import state.object.PopStateButtonState;
import state.object.PowerUpgradeButtonState;
import state.object.TextDisplayState;

//TODO: add button, button selection, as means of leaving

/**
 * A class which defines the engine state which runs when the player enters the shop
 * @author Robert Schrupp
 *
 */
public class ShopState extends EngineState {

	//Attributes
	private GameObject shopKeeper;	

	/**
	 * Constructs shop state
	 * Calls super constructor -> Makes call to init()
	 */
	public ShopState(Entity triggeredBy, GameObject attachedTo) {
		super();

		//Assign member variables
		shopKeeper = attachedTo;

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
		
		//Create label for player's gold
		GameObject playerGold = new GameObject(
				Directory.screenManager.getPercentageWidth(5.0), Directory.screenManager.getPercentageHeight(2.0),
				Directory.screenManager.getPercentageWidth(15.0), Directory.screenManager.getPercentageHeight(5.0)
				);
		playerGold.setShape(new Rectangle2D.Double(), Color.white);
		playerGold.setVisible(true);
		playerGold.setState(new GoldTextDisplayState());
		addObj(playerGold);

		//Create health purchase button
		GameObject healthUpgradeButton = new GameObject(
				Directory.screenManager.getPercentageWidth(15.0), Directory.screenManager.getPercentageHeight(10.0),
				Directory.screenManager.getPercentageWidth(42.0), Directory.screenManager.getPercentageHeight(5.0));

		healthUpgradeButton.setShape(new Rectangle2D.Double(), Color.white);
		healthUpgradeButton.setVisible(true);

		healthUpgradeButton.setState(new HealthUpgradeButtonState());
		//Add health upgrade button
		addObj(healthUpgradeButton);

		//Create power button
		GameObject powerUpgradeButton = new GameObject(
				Directory.screenManager.getPercentageWidth(15.0), Directory.screenManager.getPercentageHeight(20.0),
				Directory.screenManager.getPercentageWidth(42.0), Directory.screenManager.getPercentageHeight(5.0));

		powerUpgradeButton.setShape(new Rectangle2D.Double(), Color.white);
		powerUpgradeButton.setVisible(true);
		powerUpgradeButton.setState(new PowerUpgradeButtonState());
		//Add power upgrade button
		addObj(powerUpgradeButton);

		//Create defense purchase button
		GameObject defenseUpgradeButton = new GameObject(
				Directory.screenManager.getPercentageWidth(15.0), Directory.screenManager.getPercentageHeight(30.0),
				Directory.screenManager.getPercentageWidth(42.0), Directory.screenManager.getPercentageHeight(5.0));

		defenseUpgradeButton.setShape(new Rectangle2D.Double(), Color.white);
		defenseUpgradeButton.setVisible(true);
		defenseUpgradeButton.setState(new DefenseUpgradeButtonState());
		//Add defense upgrade button
		addObj(defenseUpgradeButton);

		//Create ability purchase buttons
		GameObject addativeDamageAbilityButton = new GameObject(
				Directory.screenManager.getPercentageWidth(15.0), Directory.screenManager.getPercentageHeight(40.0),
				Directory.screenManager.getPercentageWidth(42.0), Directory.screenManager.getPercentageHeight(5.0));
		
		addativeDamageAbilityButton.setShape(new Rectangle2D.Double(), Color.white);
		addativeDamageAbilityButton.setVisible(true);
		addativeDamageAbilityButton.setState(new AbilityPurchaseButtonState(ProfileManager.Abilities.ADDATIVE_DAMAGE, "Addative Damage", 10 ));
		//Add addativeDamage button
		addObj(addativeDamageAbilityButton);
		

		//Create exit button
		GameObject exitButton = new GameObject(
				Directory.screenManager.getPercentageWidth(85.0), Directory.screenManager.getPercentageHeight(5.0),
				Directory.screenManager.getPercentageWidth(10.0), Directory.screenManager.getPercentageHeight(5.0));

		exitButton.setShape(new Rectangle2D.Double(), Color.white);
		exitButton.setVisible(true);

		exitButton.setState(new PopStateButtonState("Quit"));

		//Add exit button
		addObj(exitButton);
	}

	/**
	 * On enter this state does not need to do anything
	 */
	@Override
	public void enter() {
		// TODO Auto-generated method stub

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
	 * Resolves shopState, moving back to overworld state
	 * Pops shopKeeper's state and player's state.
	 */
	@Override
	public void exit() {
		System.out.println("Exiting");
		Directory.profile.getPlayer().popState();
		shopKeeper.popState();		
	}
}
