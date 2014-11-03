package state.object;

import java.awt.Graphics2D;


import Engine.Directory;
import MathHelp.Vector;
import Objects.Entity;
import Objects.GameObject;
import Objects.Sprites.Sprite;

/**
 * State which governs the object during the engine's ShopState
 * @author Nex, Robert Schrupp
 *
 */
public class ObjectShopState extends ObjectState{

	//attributes
	private Vector worldPos;
	private double worldWidth;
	private double worldHeight;
	private GameObject object;

	/**
	 * Creates player shop state
	 * 
	 */
	public ObjectShopState() {
		super();
	}
	
	/**
	 * Initializes all internal attributes
	 */
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Has an object enter ObjectShopState
	 * Saves the object's world position and aligns the object on the right side of the screen.
	 * Sets dimensions to shopDimensions, and sets the image to the object's shopImage
	 */
	@Override
	public void enter() {
		//super.enter();

		//Save current position
		worldPos = new Vector(attachedTo.getPos());
		
		//Save current dimensions
		worldWidth = attachedTo.getWidth();
		worldHeight = attachedTo.getHeight();
		
		//downcast attachedTo gameobject to player
		object = attachedTo;

		//Update position
		Vector posVector = new Vector(2);
		posVector.setComponent(0, Directory.screenManager.getPercentageWidth(75.0));
		posVector.setComponent(1, Directory.screenManager.getPercentageHeight(45.0));
		attachedTo.setPos(posVector);

		//Update dimensions
		attachedTo.setWidth(200);
		attachedTo.setHeight(300);
		
		//Set players sprite
		attachedTo.setSprite(Directory.spriteLibrary.get("Fireball1"));
	}

	/**
	 * Updates the player's ShopState
	 * Updates the attached shape
	 */
	@Override
	public void update() {

		//Update shape in case of moving
		attachedTo.updateShape();

	}

	/**
	 * AttachedObject Exits the player's shop state
	 */
	@Override
	public void exit() {
		//Set position back to worldPos
		attachedTo.setPos(worldPos);

		//Set dimensions back to worldDimensions
		attachedTo.setWidth(worldWidth);
		attachedTo.setHeight(worldHeight);
		
		//Set Sprite to null
		attachedTo.setSprite(null);
	}

	@Override
	public void draw(Graphics2D g2d) {
		
	}



}
