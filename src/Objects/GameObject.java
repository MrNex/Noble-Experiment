package Objects;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RectangularShape;
import java.awt.image.BufferedImage;

import MathHelp.Vector;
import Objects.ObjStates.ObjState;


public class GameObject {

	//Attributes
	protected Vector position;
	protected double width, height;
	protected boolean visible, running;
	protected boolean triggerable, solid;
	protected RectangularShape shape;
	protected BufferedImage image;
	protected Color color;
	protected ObjState currentState;
	

	public GameObject(double xx, double yy, double w, double h) {
		//Set designated attributes
		position = new Vector(2);
		position.setComponent(0, xx);
		position.setComponent(1, yy);

		width = w;
		height = h;

		//Set default attributes
		visible = false;
		running = false;

		currentState = null;

		shape = null;
		color = Color.black;
		
		image = null;
	}

	//Gets the position vector
	public Vector getPos(){
		return position;
	}

	//sets the position vector
	public void setPos(Vector v){
		position = v;
	}

	//Gets the x comp of positionvector
	public double getXPos(){
		return position.getComponent(0);
	}

	//Gets the y comp of position vector
	public double getYPos(){
		return position.getComponent(1);
	}
	
	/**
	 * Sets the width of this gameobject
	 * @param newWidth Width to set
	 */
	public void setWidth(int newWidth){
		width = newWidth;
	}
	
	/**
	 * Sets the height of this gameObject
	 * @param newHeight Height to set
	 */
	public void setHeight(int newHeight){
		height = newHeight;
	}

	//Set the state of an object (DON'T FORGET TO SET THE OBJECT TO RUNNING!)
	public void setState(ObjState newState){
		//If not leaving a null state
		if(currentState != null)
			currentState.exit();
		currentState = newState;
		
		//If not going into a null state
		if(newState != null){
			newState.setAttachedGameObject(this);
			newState.enter();
		}
	}

	//visible accessors
	public boolean isVisible(){
		return visible;
	}
	public void setVisible(boolean isVisible){
		visible = isVisible;
	}

	//running accessors
	public boolean isRunning(){
		return running;
	}
	public void setRunning(boolean isRunning){
		running = isRunning;
	}
	
	/**
	 * Gets whether this gameObject has an active trigger
	 * @return whether this object has an active trigger
	 */
	public boolean isTriggerable(){
		return triggerable;
	}
	
	/**
	 * Sets whether this gameObject has an active trigger
	 * @param isTriggerable Does this game object have an active trigger
	 */
	public void setTriggerable(boolean isTriggerable){
		triggerable = isTriggerable;
	}
	
	/**
	 * Sets whether an object is solid
	 * A solid object cannot be passed through
	 * @return The solidarity of the object
	 */
	public boolean isSolid(){
		return solid;
	}
	
	/**
	 * Sets whether an object is solid.
	 * Solid objects cannot be passed through
	 * @param isSolid Is this gameObject solid?
	 */
	public void setSolid(boolean isSolid){
		solid = isSolid;
	}

	//Set shape
	public void setShape(RectangularShape newShape){
		//Set the shape
		shape = newShape;
		//if its not null, update it to my position
		if(shape != null){
			updateShape();
		}
	}
	
	public void setImage(BufferedImage newImage){
		image = newImage;
	}

	//Set color
	public void setColor(Color newColor){
		color = newColor;
	}

	//I'm a programmer and math minor, I love to re-use things.
	public void setShape(RectangularShape newShape, Color color)
	{
		setShape(newShape);
		setColor(color);
	}

	//Updates the gameObject
	public void update(){
		if(running){
			currentState.update();
		}
	}

	//Draws the shape representing this gameObject
	public void draw(Graphics2D g2d){
		if(visible)
		{
			//If they have an image
			if(image != null){
				g2d.drawImage(image,
						(int)position.getComponent(0), (int)position.getComponent(1), 
						(int)(position.getComponent(0) + width), (int)(position.getComponent(1) + height), 
						0, 0, image.getWidth(), image.getHeight(), null);
			}
			else{
				//Set the color
				g2d.setColor(color);
				//Fill the shape
				g2d.fill(shape);
			}
		}
	}

	public void updateShape(){
		if(shape != null)
			shape.setFrame(position.getComponent(0), position.getComponent(1), width, height);
	}

	//Checks if the bounding box of this obj is intersecting the bounding box of another obj
	public boolean isColliding(GameObject obj){

		//If the left side of this is to the left  right side of obj and the right side of this is to the right of the left side of obj
		if(position.getComponent(0) < obj.position.getComponent(0) + this.width && this.position.getComponent(0) + this.width > obj.position.getComponent(0)){

			//IF the top of this is higher than the bottom of obj and the bottom of this is further down than the top of obj
			if(position.getComponent(1) < obj.position.getComponent(1) + this.height && this.position.getComponent(1) + this.height > obj.position.getComponent(1)){
				return true;
			}	
		}
		return false;
	}

	//Checks whether or not the bounding box surrounding the shape contains a given point
	public boolean contains(double xx, double yy){
		return 
				xx < position.getComponent(0) + width && 
				xx > position.getComponent(0) && 
				yy < position.getComponent(1) + height && 
				yy > position.getComponent(1); 
	}

}
