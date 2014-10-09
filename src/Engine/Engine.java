package Engine;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.util.Stack;

import Engine.States.*;
import MathHelp.Vector;
import Objects.*;
import Objects.ObjStates.EnemyBattleState;
import Objects.ObjStates.PlayerBattleState;
import Objects.ObjStates.PlayerOverworldState;
import Objects.Triggers.BattleStartTrigger;

public class Engine {

	private boolean running;
	private Stack<State> stateStack;
	//private State currentState;

	public Engine() {
		init();
	}

	public void init()
	{
		//Initialize the stateStack
		stateStack = new Stack<State>();
		
		//Push current state on stack
		stateStack.push(new OverworldState());
		
		//Set the currentState
		//currentState = new BattleState();


		//Set internal variables
		running = false;

	}

	public void start()
	{
		//Set running to true
		running = true;
		
		
		
		//Create enemy as entity
		GameObject enemy = new Entity(Directory.screenManager.getPercentageWidth(85.0), Directory.screenManager.getPercentageHeight(45.0), 20, 20, 10, 1, 1);
		//Set enemy shape and visibility
		enemy.setShape(new Ellipse2D.Double(), Color.RED);
		enemy.setVisible(true);
		//Set enemy trigger
		enemy.setTrigger(new BattleStartTrigger());
		//Set the enemy as triggerable
		enemy.setTriggerable(true);
		//Set enemy state
		//enemy.setState(new EnemyBattleState());
		//Set enemy to running
		//enemy.setRunning(true);
		//Set enemies equation as invisible
		//((Entity)enemy).setEquationVisibility(false);
		
		//Add enemy to state
		Directory.engine.getCurrentState().addObj(enemy);
		
		

		//Position player
		Vector posVector = new Vector(2);
		posVector.setComponent(0, Directory.screenManager.getPercentageWidth(15.0));
		posVector.setComponent(1, Directory.screenManager.getPercentageHeight(45.0));
		Directory.profile.getPlayer().setPos(new Vector(posVector));
		
		//Update shape in case position has changed
		Directory.profile.getPlayer().updateShape();
		
		//Set player state
		Directory.profile.getPlayer().setState(new PlayerOverworldState());
		Directory.profile.getPlayer().setRunning(true);

		//Add player
		Directory.engine.getCurrentState().addObj(Directory.profile.getPlayer());

		//Run
		run();
	}

	private void run()
	{
		while(running)
		{
			stateStack.peek().update();
		}
	}

	public State popState(){
		return stateStack.pop();
	}
	
	public State getCurrentState()
	{
		return stateStack.peek();
	}
	
	public void pushState(State stateToPush){
		stateStack.push(stateToPush);
	}

}
