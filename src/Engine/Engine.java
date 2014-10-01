package Engine;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;

import Engine.States.*;
import MathHelp.Vector;
import Objects.*;
import Objects.ObjStates.EnemyState;
import Objects.ObjStates.PlayerBattleState;

public class Engine {

	private boolean running;
	private State currentState;

	public Engine() {
		init();
	}

	public void init()
	{
		//Set the currentState
		currentState = new BattleState();


		//Set internal variables
		running = false;

	}

	public void start()
	{
		//Set running to true
		running = true;
		
		
		//Create enemy as entity
		GameObject enemy = new Entity(Directory.screenManager.getPercentageWidth(85.0), Directory.screenManager.getPercentageHeight(45.0), 75, 300, 10, 1, 1, false);
		//Set enemy shape and visibility
		enemy.setShape(new Ellipse2D.Double(), Color.RED);
		enemy.setVisible(true);
		//Set enemy state
		enemy.setState(new EnemyState());
		//Set enemy to running
		enemy.setRunning(true);
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
		Directory.profile.getPlayer().setState(new PlayerBattleState());
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
			currentState.update();

		}
	}

	public State getCurrentState()
	{
		return currentState;
	}

}
