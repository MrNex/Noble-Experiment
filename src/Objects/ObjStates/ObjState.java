package Objects.ObjStates;

import Objects.GameObject;

//A state attachable to every game object
public abstract class ObjState{

	protected GameObject attachedTo;
	
	public ObjState() {
		
	}
	
	abstract public void enter();
	
	abstract public void update();
	
	abstract public void exit();
	
	public void setAttachedGameObject(GameObject attachTo){
		attachedTo = attachTo;
	}
}
