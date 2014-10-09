package Objects.ObjStates;

import java.awt.Graphics2D;

import Objects.GameObject;

//A state attachable to every game object
public abstract class ObjState{

	protected GameObject attachedTo;
	
	public ObjState() {
		
	}
	
	abstract public void enter();
	
	abstract public void update();
	abstract public void draw(Graphics2D g2d);
	
	abstract public void exit();
	
	public void setAttachedGameObject(GameObject attachTo){
		attachedTo = attachTo;
	}
}
