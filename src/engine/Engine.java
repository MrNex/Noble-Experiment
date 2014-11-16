package engine;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.util.EmptyStackException;
import java.util.Stack;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;













import engine.manager.CollisionManager;
import engine.manager.InputManager;
import engine.manager.ProfileManager;
import engine.manager.ScreenManager;
import engine.manager.SpriteManager;
import loaders.ImageLoader;
import loaders.Loader;
import loaders.SpriteLoader;
import mathematics.Vector;
import objects.*;
import state.engine.EngineState;
import state.engine.MainMenuState;
import state.engine.OverworldState;
import state.object.EnemyBattleState;
import state.object.EnemyBurstFireState;
import state.object.EnemyScatterShotState;
import state.object.PlayerBattleState;
import state.object.PlayerOverworldState;
import triggers.BattleStartTrigger;
import triggers.ShopStartTrigger;

/**
 * Engine which runs on finite state machine.
 * Uses EngineStates
 * @author Nex
 *
 */
public class Engine {

	//Attributes
	private Stack<EngineState> stateStack;
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
		stateStack = new Stack<EngineState>();
		

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
		//Push the main menu state
		//Push current state on stack
		stateStack.push(new MainMenuState());	//As long as a state s onthe stackthe engine is running
		
		//Start the drawLoop
		drawTimer.start();
		
		//Run
		run();
	}


	/**
	 * This is the run loop for the engine.
	 * Constantly updates the current state of the engine.
	 */
	private void run()
	{
		while(isRunning())
		{
			Directory.inputManager.update();
			getCurrentState().update();
			Directory.spriteManager.update();
			Directory.screenManager.updateHud();
		}
		
		//When the engine is no longer running, close the application
		System.exit(0);
	}
	
	public boolean isRunning(){
		return getCurrentState() != null;
	}

	/**
	 * Removes and returns the current state from the stateStack.
	 * The current state will become the next state in the stack.
	 * Upon removing the current state from the top of the stack, it's exit method is called
	 * @return The removed state
	 */
	public EngineState popState(){
		EngineState poppedState = stateStack.pop();
		if(poppedState != null){
			poppedState.exit();
		}
		
		return poppedState;
	}

	/**
	 * Gets the current state
	 * @return The state on top of the state stack.
	 */
	public EngineState getCurrentState()
	{
		EngineState returnState;
		try{
			returnState = stateStack.peek();
		}
		catch(EmptyStackException e){
			returnState = null;
		}
		return returnState;
	}

	/**
	 * Pushes a new state to the state stack.
	 * Current state will become this pushed state
	 * The new current state will have it's enter method pushed
	 * @param stateToPush The new current state
	 */
	public void pushState(EngineState stateToPush){
		stateStack.push(stateToPush);
		if(stateStack.peek() != null){
			stateStack.peek().enter();
		}
	}

}
