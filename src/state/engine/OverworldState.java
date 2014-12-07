package state.engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import mathematics.Vector;
import engine.Directory;
import objects.Entity;
import objects.GameObject;
import state.object.EnemyBattleState;
import state.object.EnemyBurstFireState;
import state.object.EnemyRandomYState;
import state.object.EnemyScatterShotState;
import state.object.PlayerOverworldState;
import triggers.BattleStartTrigger;
import triggers.ShopStartTrigger;

public class OverworldState extends EngineState {
	
	//Static Attributes
	private static int numEnemies = 8;								// Number of enemies in the game

	public OverworldState() {
		super();

	}

	@Override
	public void init(){
		super.init();

		//Create first enemy: enemy
		GameObject enemy = new Entity(
				Directory.screenManager.getPercentageWidth(20),		//XPos
				Directory.screenManager.getPercentageHeight(80),	//YPos
				20,													//Width
				20,													//Height
				10,													//Health
				1,													//Power
				1													//Defense
				);		
		
		//Set enemy shape and visibility
		enemy.setShape(new Ellipse2D.Double(), Color.black);
		enemy.setVisible(true);
		//Set the enemy as triggerable
		enemy.setTriggerable(true);
		//Set enemy trigger
		enemy.addTrigger(new BattleStartTrigger(new EnemyBattleState()));

		enemy.setSolid(true);

		//Add enemy to state
		addObj(enemy);
		
		//Create second enemy: enemy1
		GameObject enemy1 = new Entity(
				Directory.screenManager.getPercentageWidth(40),		//XPos
				Directory.screenManager.getPercentageHeight(80),	//YPos
				20,													//Width
				20,													//Height
				10,													//Health
				2,													//Power
				1													//Defense
				);

		enemy1.setShape(new Ellipse2D.Double(), Color.black);
		enemy1.setVisible(true);

		enemy1.setTriggerable(true);
		enemy1.addTrigger(new BattleStartTrigger(new EnemyBattleState()));

		enemy1.setSolid(true);

		addObj(enemy1);
		
		//Create third enemy: burstFireEnemy
		GameObject burstEnemy = new Entity(
				Directory.screenManager.getPercentageWidth(60),		//XPos
				Directory.screenManager.getPercentageHeight(80),	//YPos
				20,													//Width
				20,													//Height
				10,													//Health
				2,													//Power
				1													//Defense
				);

		burstEnemy.setShape(new Ellipse2D.Double(), Color.lightGray);
		burstEnemy.setVisible(true);

		burstEnemy.setTriggerable(true);
		burstEnemy.addTrigger(new BattleStartTrigger(new EnemyBurstFireState()));

		burstEnemy.setSolid(true);

		addObj(burstEnemy);
		
		//Create fourth enemy: randomYEnemy
		GameObject randomYEnemy = new Entity(
				Directory.screenManager.getPercentageWidth(80),		//XPos
				Directory.screenManager.getPercentageHeight(80),	//YPos
				20,													//Width
				20,													//Height
				20,													//Health
				1,													//Power
				1													//Defense
				);

		randomYEnemy.setShape(new Ellipse2D.Double(), Color.darkGray);
		randomYEnemy.setVisible(true);

		randomYEnemy.setTriggerable(true);
		randomYEnemy.addTrigger(new BattleStartTrigger(new EnemyRandomYState()));

		randomYEnemy.setSolid(true);

		addObj(randomYEnemy);
		
		//Create fifth enemy: enemy2
		GameObject enemy2 = new Entity(
				Directory.screenManager.getPercentageWidth(80),		//XPos
				Directory.screenManager.getPercentageHeight(60),	//YPos
				20,													//Width
				20,													//Height
				30,													//Health
				2,													//Power
				1													//Defense
				);

		enemy2.setShape(new Ellipse2D.Double(), Color.black);
		enemy2.setVisible(true);

		enemy2.setTriggerable(true);
		enemy2.addTrigger(new BattleStartTrigger(new EnemyBattleState()));

		enemy2.setSolid(true);

		addObj(enemy2);
		
		//Create sixth enemy: burstFireEnemy1
		GameObject burstEnemy1 = new Entity(
				Directory.screenManager.getPercentageWidth(80),		//XPos
				Directory.screenManager.getPercentageHeight(40),	//YPos
				20,													//Width
				20,													//Height
				20,													//Health
				2,													//Power
				1													//Defense
				);

		burstEnemy1.setShape(new Ellipse2D.Double(), Color.lightGray);
		burstEnemy1.setVisible(true);

		burstEnemy1.setTriggerable(true);
		burstEnemy1.addTrigger(new BattleStartTrigger(new EnemyBurstFireState()));

		burstEnemy1.setSolid(true);

		addObj(burstEnemy1);
		
		//Create seventh enemy: randomYEnemy1
		GameObject randomYEnemy1 = new Entity(
				Directory.screenManager.getPercentageWidth(80),		//XPos
				Directory.screenManager.getPercentageHeight(20),	//YPos
				20,													//Width
				20,													//Height
				20,													//Health
				2,													//Power
				2													//Defense
				);

		randomYEnemy1.setShape(new Ellipse2D.Double(), Color.darkGray);
		randomYEnemy1.setVisible(true);

		randomYEnemy1.setTriggerable(true);
		randomYEnemy1.addTrigger(new BattleStartTrigger(new EnemyRandomYState()));

		randomYEnemy1.setSolid(true);

		addObj(randomYEnemy1);
		

		//Create shop as a gameObject
		GameObject shop = new GameObject(
				Directory.screenManager.getPercentageWidth(55.0),	//XPos
				Directory.screenManager.getPercentageHeight(20.0),	//YPos
				20,													//Width
				20													//Height
				);
		//Set shop shape and visibility
		shop.setShape(new Ellipse2D.Double(), Color.BLUE);
		shop.setVisible(true);
		//Set the shop as triggerable
		shop.setTriggerable(true);
		//Set shop trigger
		shop.addTrigger(new ShopStartTrigger());

		shop.setSolid(true);

		//Add shop to state
		addObj(shop);



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
		addObj(Directory.profile.getPlayer());
		
		Directory.profile.addGold(3000);//15);
	}

	/**
	 * Updates every object in this state,
	 * Checks for collisions
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
		
		// add in boss once all enemies are defeated
		if(Directory.profile.getEnemiesDefeated() >= numEnemies-1){
			//Create final enemy: scatterShotEnemy
			GameObject scatterEnemy = new Entity(
					Directory.screenManager.getPercentageWidth(80),		//XPos
					Directory.screenManager.getPercentageHeight(20),	//YPos
					20,													//Width
					20,													//Height
					30,													//Health
					1,													//Power
					2													//Defense
					);
	
			scatterEnemy.setShape(new Ellipse2D.Double(), Color.red);
			scatterEnemy.setVisible(true);
	
			scatterEnemy.setTriggerable(true);
			scatterEnemy.addTrigger(new BattleStartTrigger(new EnemyScatterShotState()));
	
			scatterEnemy.setSolid(true);
			
			addObj(scatterEnemy);
		}

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
