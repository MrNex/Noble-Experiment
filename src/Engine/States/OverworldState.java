package Engine.States;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Objects.Destructable;
import Objects.GameObject;

public class OverworldState extends State {

	public OverworldState() {
		// TODO Auto-generated constructor stub
	}

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

		//Get copyList
		copyList = new ArrayList<GameObject>(toAdd);
		//add every game object in toAdd
		for(GameObject obj : copyList)
		{
			objects.add(obj);
		}
		toAdd.removeAll(copyList);


	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub

	}

}
