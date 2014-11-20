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

	public OverworldState() {
		super();

	}

	@Override
	public void init(){
		super.init();

		//Create enemy as entity
		//GameObject enemy = new Entity(Directory.screenManager.getPercentageWidth(85.0), Directory.screenManager.getPercentageHeight(45.0), 20, 20, 10, 1, 1);
		//Create second enemy: burstFireEnemy
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



		//Create second enemy: burstFireEnemy
		GameObject burstEnemy = new Entity(
				Directory.screenManager.getPercentageWidth(40),		//XPos
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



		//Create third enemy: scatterShotEnemy
		GameObject scatterEnemy = new Entity(
				Directory.screenManager.getPercentageWidth(60),		//XPos
				Directory.screenManager.getPercentageHeight(80),	//YPos
				20,													//Width
				20,													//Height
				10,													//Health
				1,													//Power
				2													//Defense
				);

		scatterEnemy.setShape(new Ellipse2D.Double(), Color.red);
		scatterEnemy.setVisible(true);

		scatterEnemy.setTriggerable(true);
		scatterEnemy.addTrigger(new BattleStartTrigger(new EnemyScatterShotState()));

		scatterEnemy.setSolid(true);

		addObj(scatterEnemy);
		
		
		//Create fourth enemy: randomYEnemy
		GameObject randomYEnemy = new Entity(
				Directory.screenManager.getPercentageWidth(80),		//XPos
				Directory.screenManager.getPercentageHeight(80),	//YPos
				20,													//Width
				20,													//Height
				10,													//Health
				1,													//Power
				1													//Defense
				);

		randomYEnemy.setShape(new Ellipse2D.Double(), Color.darkGray);
		randomYEnemy.setVisible(true);

		randomYEnemy.setTriggerable(true);
		randomYEnemy.addTrigger(new BattleStartTrigger(new EnemyRandomYState()));

		randomYEnemy.setSolid(true);

		addObj(randomYEnemy);




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
	}

	/**
	 * Updates every object in this state,
	 * Checks for collsions
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
