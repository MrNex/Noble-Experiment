package Engine.Manager;

import java.util.ArrayList;

import Engine.Directory;
import Objects.GameObject;
import Objects.MovableGameObject;
import Objects.Entity;
import Objects.Triggers.Trigger;


/**
 * CollisionManager will tap into the current list of objects
 * and query any solid movableGameObject if it has intersected with any other solid gameobject.
 * If a collision returns true, it is resolved by reverting the movableGameObject
 * to it's previousPosition before the collision took place.
 * 
 * @author Nex
 *
 */
public class CollisionManager extends Manager{

	/**
	 * Constructs a collision manager
	 */
	public CollisionManager() { }

	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Checks for any colliding gameObjects in the current state and resolves them
	 */
	@Override
	public void update(){
		//Get a list of objects in the current state
		ArrayList<GameObject> copyList = Directory.engine.getCurrentState().getObjListCopy();

		//For each gameObject in the current state
		for(GameObject obj1 : copyList){

			//Only check for collisions if the object moves and is solid
			if(obj1 instanceof MovableGameObject && obj1.isSolid()){

				//If this object does move, check if it is colliding with any other solid or triggerable game object
				for(GameObject obj2 : copyList){

					//If obj2 is obj1, or obj2 is not solid, skip this object!
					if(obj2 == obj1 || !obj2.isSolid()) continue;

					//If there is a collision
					if(obj2.isColliding(obj1) && obj1.isColliding(obj2)){

						//The movableGameObject obj must be moved to its previous position to resolve this collision
						((MovableGameObject)obj1).revert();


						//If o is a trigger
						if(obj2.isTriggerable()){

							//If object 1 is triggerable
							if(obj1.isTriggerable()){
								//For each trigger it has
								for(Trigger t : obj1.getTriggers()){
									t.action(obj2);
								}
							}

							//If object 2 is triggerable
							if(obj2.isTriggerable()){
								//pull each trigger it has
								for(Trigger t : obj2.getTriggers()){
									t.action(obj1);
								}
							}
						}

						

					}//Ends if colliding

				}
			}
		}
	}

}
