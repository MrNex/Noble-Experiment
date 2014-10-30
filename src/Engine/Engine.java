package Engine;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.util.Stack;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import Engine.Manager.CollisionManager;
import Engine.Manager.InputManager;
import Engine.Manager.ProfileManager;
import Engine.Manager.ScreenManager;
import Engine.Manager.SpriteManager;
import Engine.States.*;
import Loaders.ImageLoader;
import Loaders.Loader;
import Loaders.SpriteLoader;
import MathHelp.Vector;
import Objects.*;
import Objects.ObjStates.MObjStates.PlayerOverworldState;
import Objects.ObjStates.MObjStates.EntityStates.EnemyBurstFireState;
import Objects.ObjStates.MObjStates.EntityStates.EnemyBattleState;
import Objects.ObjStates.MObjStates.EntityStates.PlayerBattleState;
import Objects.Triggers.BattleStartTrigger;
import Objects.Triggers.ShopStartTrigger;

/**
 * Engine which runs on finite state machine.
 * Uses EngineStates
 * @author Nex
 *
 */
public class Engine {

	//Attributes
	private boolean running;
	private Stack<State> stateStack;
	private Timer drawTimer;

	/**
	 * Constructs an engine
	 */
	public Engine() {
		init();
	}

	/**
	 * Initializes engine members
	 */
	public void init()
	{
		//Load content
		loadContent();

		//Initialize the stateStack
		stateStack = new Stack<State>();

		//Set internal variables
		running = false;

		//Push current state on stack
		stateStack.push(new OverworldState());

		//Initialize components
		Directory.collisionManager = new CollisionManager();	//Create Collision manager
		Directory.inputManager = new InputManager();			//Create input manager
		Directory.screenManager = new ScreenManager();			//Create screen manager
		Directory.spriteManager = new SpriteManager();			//Create sprite manager
		Directory.profile = new ProfileManager();				//Create player profile & Constructs player


		//Create draw loop
		drawTimer = new Timer(1000/60, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Directory.screenManager.update();
			}
		});

		drawTimer.setRepeats(true);

	}

	/**
	 * Loads all engine content which needs to be loaded.
	 */
	private void loadContent(){
		//Initialize loader
		//Loader imageLoader = new ImageLoader();
		Loader spriteLoader = new SpriteLoader();
		
		//Get images
		Directory.spriteLibrary = spriteLoader.loadAll();

	}


	/**
	 * Begins running the engine
	 * Loads test world
	 */
	public void start()
	{
		//Set running to true
		running = true;

		//Load the test screen
		loadTestScreen();

		//Start the drawLoop
		drawTimer.start();
		
		//Run
		run();
	}

	/**
	 * Loads a test screen or level.
	 * Temporary for debugging purposes until a proper world-loading pipeline
	 * is created.
	 */
	private void loadTestScreen(){
		//Create enemy as entity
		GameObject enemy = new Entity(Directory.screenManager.getPercentageWidth(85.0), Directory.screenManager.getPercentageHeight(45.0), 20, 20, 10, 1, 1);
		//Set enemy shape and visibility
		enemy.setShape(new Ellipse2D.Double(), Color.RED);
		enemy.setVisible(true);
		//Set the enemy as triggerable
		enemy.setTriggerable(true);
		//Set enemy trigger
		enemy.addTrigger(new BattleStartTrigger(new EnemyBattleState()));

		enemy.setSolid(true);

		//Add enemy to state
		Directory.engine.getCurrentState().addObj(enemy);
		
		
		
		//Create second burstFireEnemy
		GameObject burstEnemy = new Entity(
				Directory.screenManager.getPercentageWidth(40),		//XPos
				Directory.screenManager.getPercentageHeight(90),	//YPos
				20,													//Width
				20,													//Height
				10,													//Health
				2,													//Power
				1													//Defense
				);
		
		burstEnemy.setShape(new Ellipse2D.Double(), Color.magenta);
		burstEnemy.setVisible(true);
		
		burstEnemy.setTriggerable(true);
		burstEnemy.addTrigger(new BattleStartTrigger(new EnemyBurstFireState()));
		
		burstEnemy.setSolid(true);
		
		Directory.engine.getCurrentState().addObj(burstEnemy);
		
		
		//Create shop as a gameObject
		GameObject shop = new GameObject(Directory.screenManager.getPercentageWidth(55.0), Directory.screenManager.getPercentageHeight(20.0), 20, 20);
		//Set shop shape and visibility
		shop.setShape(new Ellipse2D.Double(), Color.BLUE);
		shop.setVisible(true);
		//Set the shop as triggerable
		shop.setTriggerable(true);
		//Set shop trigger
		shop.addTrigger(new ShopStartTrigger());

		shop.setSolid(true);

		//Add shop to state
		Directory.engine.getCurrentState().addObj(shop);



		//Position player
		Vector posVector = new Vector(2);
		posVector.setComponent(0, Directory.screenManager.getPercentageWidth(15.0));
		posVector.setComponent(1, Directory.screenManager.getPercentageHeight(45.0));
		Directory.profile.getPlayer().setPos(new Vector(posVector));

		//Update shape in case position has changed
		Directory.profile.getPlayer().updateShape();

		//Set player state
		Directory.profile.getPlayer().setState(new PlayerOverworldState());
		//Directory.profile.getPlayer().setRunning(true);

		Directory.profile.getPlayer().setSolid(true);

		//Add player
		Directory.engine.getCurrentState().addObj(Directory.profile.getPlayer());
	}

	/**
	 * This is the run loop for the engine.
	 * Constantly updates the current state of the engine.
	 */
	private void run()
	{
		while(running)
		{
			Directory.inputManager.update();
			getCurrentState().update();
			Directory.spriteManager.update();
			Directory.screenManager.updateHud();

		}
	}

	/**
	 * Removes and returns the current state from the stateStack.
	 * The current state will become the next state in the stack.
	 * @return The removed state
	 */
	public State popState(){
		return stateStack.pop();
	}

	/**
	 * Gets the current state
	 * @return The state on top of the state stack.
	 */
	public State getCurrentState()
	{
		return stateStack.peek();
	}

	/**
	 * Pushes a new state to the state stack.
	 * Current state will become this pushed state
	 * @param stateToPush The new current state
	 */
	public void pushState(State stateToPush){
		stateStack.push(stateToPush);
	}

}
