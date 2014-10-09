package Engine;

import java.util.ArrayList;

import Objects.GameObject;
import Objects.MovableGameObject;
import Objects.Entity;

public class CollisionManager {

	/**
	 * Constructs a collision manager
	 */
	public CollisionManager() { }
	
	/**
	 * Checks for any colliding gameObjects in the current state and resolves them
	 */
	public void checkCollisions(){
		//Get a list of objects in the current state
		ArrayList<GameObject> copyList = Directory.engine.getCurrentState().getObjListCopy();
		
		//For each gameObject in the current state
		for(GameObject obj : copyList){
			//Only check for collisions if the object moves
			if(obj instanceof MovableGameObject){
				//If this object does move, check if it is colliding with any other solid or triggerable game object
				for(GameObject o : copyList){
					//If o is obj, skip this object
					if(o == obj) continue;
					//If o is a trigger
					if(o.isTriggerable()){
						//If there is a collision
						if(o.isColliding(obj) && obj.isColliding(o)){
							//Trigger o's action
							o.getTrigger().action(obj);
						}
					}
					//IF o is solid
					if(o.isSolid()){
						//If there is a collision
						if(o.isColliding(obj) && obj.isColliding(o)){
							//The movableGameObject obj must be moved to its previous position to resolve this collision
							((MovableGameObject)obj).revert();
						}
					}
				}
			}
		}
	}

}
