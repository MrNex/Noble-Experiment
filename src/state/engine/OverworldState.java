package state.engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
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
		
		//Background object
		GameObject background = new GameObject(
				0, 
				0, 
				Directory.screenManager.getPercentageWidth(100), 
				Directory.screenManager.getPercentageHeight(100)
				);
		
		//Set background image
		background.setSprite(Directory.spriteLibrary.get("Overworld"));
		background.setVisible(true);
		
		//Add background to state
		addObj(background);
		
		
		
		//WALLS SECTION//
		//Outer Walls
		GameObject wallTop = new GameObject(0, 0, Directory.screenManager.getPercentageWidth(100), 50);
		wallTop.setSolid(true);
		wallTop.setShape(new Rectangle2D.Double(), Color.white);
		//wallTop.setVisible(true);
		addObj(wallTop);
		
		GameObject wallLeft = new GameObject(0, 0, 50, Directory.screenManager.getPercentageHeight(100));
		wallLeft.setSolid(true);
		wallLeft.setShape(new Rectangle2D.Double(), Color.white);
		//wallLeft.setVisible(true);
		addObj(wallLeft);
		
		GameObject wallBottom = new GameObject(0, Directory.screenManager.getPercentageHeight(100)-50, Directory.screenManager.getPercentageWidth(100), 50);
		wallBottom.setSolid(true);
		wallBottom.setShape(new Rectangle2D.Double(), Color.white);
		//wallBottom.setVisible(true);
		addObj(wallBottom);
		
		GameObject wallRight = new GameObject(Directory.screenManager.getPercentageWidth(100)-50, 0, 50, Directory.screenManager.getPercentageHeight(100));
		wallRight.setSolid(true);
		wallRight.setShape(new Rectangle2D.Double(), Color.white);
		//wallRight.setVisible(true);
		addObj(wallRight);
		
		//Inner Walls
		GameObject wall1 = new GameObject(0, 85, 204, 71);
		wall1.setSolid(true);
		wall1.setShape(new Rectangle2D.Double(), Color.white);
		//wall1.setVisible(true);
		addObj(wall1);
		
		GameObject wall2 = new GameObject(197, 85, 65, 172);
		wall2.setSolid(true);
		wall2.setShape(new Rectangle2D.Double(), Color.white);
		//wall2.setVisible(true);
		addObj(wall2);
		
		GameObject wall3 = new GameObject(86, 190, 115, 67);
		wall3.setSolid(true);
		wall3.setShape(new Rectangle2D.Double(), Color.white);
		//wall3.setVisible(true);
		addObj(wall3);
		
		
		
		GameObject wall4 = new GameObject(0, 295, 475, 71);
		wall4.setSolid(true);
		wall4.setShape(new Rectangle2D.Double(), Color.white);
		//wall4.setVisible(true);
		addObj(wall4);
		
		GameObject wall5 = new GameObject(145, 345, 65, 125);
		wall5.setSolid(true);
		wall5.setShape(new Rectangle2D.Double(), Color.white);
		//wall5.setVisible(true);
		addObj(wall5);
		
		GameObject wall6 = new GameObject(86, 403, 115, 67);
		wall6.setSolid(true);
		wall6.setShape(new Rectangle2D.Double(), Color.white);
		//wall6.setVisible(true);
		addObj(wall6);
		
		GameObject wall7 = new GameObject(255, 402, 65, 198);
		wall7.setSolid(true);
		wall7.setShape(new Rectangle2D.Double(), Color.white);
		//wall7.setVisible(true);
		addObj(wall7);
		
		GameObject wall8 = new GameObject(410, 345, 65, 120);
		wall8.setSolid(true);
		wall8.setShape(new Rectangle2D.Double(), Color.white);
		//wall8.setVisible(true);
		addObj(wall8);
		
		
		
		GameObject wall9 = new GameObject(304, 0, 65, 257);
		wall9.setSolid(true);
		wall9.setShape(new Rectangle2D.Double(), Color.white);
		//wall9.setVisible(true);
		addObj(wall9);
		
		GameObject wall10 = new GameObject(304, 190, 115, 67);
		wall10.setSolid(true);
		wall10.setShape(new Rectangle2D.Double(), Color.white);
		//wall10.setVisible(true);
		addObj(wall10);
		
		GameObject wall11 = new GameObject(410, 92, 65, 165);
		wall11.setSolid(true);
		wall11.setShape(new Rectangle2D.Double(), Color.white);
		//wall11.setVisible(true);
		addObj(wall11);
		
		
		
		GameObject wall12 = new GameObject(519, 92, 65, 374);
		wall12.setSolid(true);
		wall12.setShape(new Rectangle2D.Double(), Color.white);
		//wall12.setVisible(true);
		addObj(wall12);
		
		GameObject wall13 = new GameObject(519, 92, 115, 67);
		wall13.setSolid(true);
		wall13.setShape(new Rectangle2D.Double(), Color.white);
		//wall13.setVisible(true);
		addObj(wall13);
		
		GameObject wall14 = new GameObject(623, 92, 65, 120);
		wall14.setSolid(true);
		wall14.setShape(new Rectangle2D.Double(), Color.white);
		//wall14.setVisible(true);
		addObj(wall14);
		
		GameObject wall15 = new GameObject(519, 247, 281, 67);
		wall15.setSolid(true);
		wall15.setShape(new Rectangle2D.Double(), Color.white);
		//wall15.setVisible(true);
		addObj(wall15);
		
		
		
		//ENEMY SECTION//
		//Create first enemy: enemy
		GameObject enemy = new Entity(
				Directory.screenManager.getPercentageWidth(34.5),	//XPos
				Directory.screenManager.getPercentageHeight(47),	//YPos
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
				Directory.screenManager.getPercentageWidth(14),		//XPos
				Directory.screenManager.getPercentageHeight(67),	//YPos
				20,													//Width
				20,													//Height
				10,													//Health
				2,													//Power
				1													//Defense
				);

		enemy1.setShape(new Ellipse2D.Double(), Color.blue);
		enemy1.setVisible(true);

		enemy1.setTriggerable(true);
		enemy1.addTrigger(new BattleStartTrigger(new EnemyBattleState()));

		enemy1.setSolid(true);

		addObj(enemy1);
		
		//Create third enemy: burstFireEnemy
		GameObject burstEnemy = new Entity(
				Directory.screenManager.getPercentageWidth(45),		//XPos
				Directory.screenManager.getPercentageHeight(75),	//YPos
				20,													//Width
				20,													//Height
				10,													//Health
				2,													//Power
				1													//Defense
				);

		burstEnemy.setShape(new Ellipse2D.Double(), Color.cyan);
		burstEnemy.setVisible(true);

		burstEnemy.setTriggerable(true);
		burstEnemy.addTrigger(new BattleStartTrigger(new EnemyBurstFireState()));

		burstEnemy.setSolid(true);

		addObj(burstEnemy);
		
		//Create fourth enemy: randomYEnemy
		GameObject randomYEnemy = new Entity(
				Directory.screenManager.getPercentageWidth(62),		//XPos
				Directory.screenManager.getPercentageHeight(84.5),	//YPos
				20,													//Width
				20,													//Height
				20,													//Health
				1,													//Power
				1													//Defense
				);

		randomYEnemy.setShape(new Ellipse2D.Double(), Color.green);
		randomYEnemy.setVisible(true);

		randomYEnemy.setTriggerable(true);
		randomYEnemy.addTrigger(new BattleStartTrigger(new EnemyRandomYState()));

		randomYEnemy.setSolid(true);

		addObj(randomYEnemy);
		
		//Create fifth enemy: enemy2
		GameObject enemy2 = new Entity(
				Directory.screenManager.getPercentageWidth(62),		//XPos
				Directory.screenManager.getPercentageHeight(11.5),	//YPos
				20,													//Width
				20,													//Height
				30,													//Health
				2,													//Power
				1													//Defense
				);

		enemy2.setShape(new Ellipse2D.Double(), Color.magenta);
		enemy2.setVisible(true);

		enemy2.setTriggerable(true);
		enemy2.addTrigger(new BattleStartTrigger(new EnemyBattleState()));

		enemy2.setSolid(true);

		addObj(enemy2);
		
		//Create sixth enemy: burstFireEnemy1
		GameObject burstEnemy1 = new Entity(
				Directory.screenManager.getPercentageWidth(48.3),	//XPos
				Directory.screenManager.getPercentageHeight(28),	//YPos
				20,													//Width
				20,													//Height
				20,													//Health
				2,													//Power
				1													//Defense
				);

		burstEnemy1.setShape(new Ellipse2D.Double(), Color.orange);
		burstEnemy1.setVisible(true);

		burstEnemy1.setTriggerable(true);
		burstEnemy1.addTrigger(new BattleStartTrigger(new EnemyBurstFireState()));

		burstEnemy1.setSolid(true);

		addObj(burstEnemy1);
		
		//Create seventh enemy: randomYEnemy1
		GameObject randomYEnemy1 = new Entity(
				Directory.screenManager.getPercentageWidth(75.6),		//XPos
				Directory.screenManager.getPercentageHeight(30),	//YPos
				20,													//Width
				20,													//Height
				20,													//Health
				2,													//Power
				2													//Defense
				);

		randomYEnemy1.setShape(new Ellipse2D.Double(), Color.pink);
		randomYEnemy1.setVisible(true);

		randomYEnemy1.setTriggerable(true);
		randomYEnemy1.addTrigger(new BattleStartTrigger(new EnemyRandomYState()));

		randomYEnemy1.setSolid(true);

		addObj(randomYEnemy1);
		

		//Create shop as a gameObject
		GameObject shop = new GameObject(
				Directory.screenManager.getPercentageWidth(83),		//XPos
				Directory.screenManager.getPercentageHeight(65),	//YPos
				20,													//Width
				20													//Height
				);
		//Set shop shape and visibility
		shop.setShape(new Ellipse2D.Double(), Color.red);
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
		posVector.setComponent(0, Directory.screenManager.getPercentageWidth(7.5));
		posVector.setComponent(1, Directory.screenManager.getPercentageHeight(10.5));
		Directory.profile.getPlayer().setPos(new Vector(posVector));

		//Update shape in case position has changed
		Directory.profile.getPlayer().updateShape();

		//Set player state
		Directory.profile.getPlayer().setState(new PlayerOverworldState());
		//Directory.profile.getPlayer().setRunning(true);

		Directory.profile.getPlayer().setSolid(true);

		//Add player
		addObj(Directory.profile.getPlayer());
		
		Directory.profile.addGold(15);
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
					Directory.screenManager.getPercentageWidth(21),		//XPos
					Directory.screenManager.getPercentageHeight(29),	//YPos
					20,													//Width
					20,													//Height
					30,													//Health
					1,													//Power
					2													//Defense
					);
	
			scatterEnemy.setShape(new Ellipse2D.Double(), Color.yellow);
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
