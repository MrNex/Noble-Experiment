package state.object.movable.entity;

import java.util.ArrayList;
import java.util.LinkedList;

import state.engine.BattleState;
import state.object.movable.PlayerOverworldState;
import Engine.Directory;
import Equations.*;
import MathHelp.Vector;
import Objects.Entity;
import Objects.GameObject;
import Objects.Sprites.Sprite;

/**
 * State which governs the player during the engine's ShopState
 * @author Nex, Robert Schrupp
 *
 */
public class PlayerShopState extends TargetableState{

	//attributes
	private Vector worldPos;
	private double worldWidth;
	private double worldHeight;
	private Sprite worldSprite;
	private Entity player;

	/**
	 * Creates player shop state
	 * 
	 * Sets the currentTarget to null
	 */
	public PlayerShopState() {
		super(true);

	}
	
	@Override
	public void init(){
		super.init();
	}

	/**
	 * Has an object enter PlayerShopState
	 * Saves the object's world position and aligns the object on the left side of the screen.
	 * Calls MovableGameObject's refresh method to set the previousPosition to the currentPosition.
	 * 		This ensures the object won't revert to its old previousPosition when getting hit by a projectile.
	 * Sets dimensions to shopDimensions, and sets the image to the player's shopImage
	 * Calls toggleTarget to target a valid entity
	 */
	@Override
	public void enter() {
		super.enter();

		/*
		//Save current position
		worldPos = new Vector(attachedTo.getPos());
		
		//Save current dimesions
		worldWidth = attachedTo.getWidth();
		worldHeight = attachedTo.getHeight();
		
		//SAve image
		worldSprite = attachedTo.getSprite();
		*/
		//downcast attachedTo gameobject to player
		player = getAttachedEntity();

		//Update position
		Vector posVector = new Vector(2);
		posVector.setComponent(0, Directory.screenManager.getPercentageWidth(5.0));
		posVector.setComponent(1, Directory.screenManager.getPercentageHeight(45.0));
		attachedTo.setPos(posVector);

		//Refresh the movable object's previous position due to engine state change
		getAttachedEntity().refresh();


		//Update dimensions
		attachedTo.setWidth(200);
		attachedTo.setHeight(300);
		
		//SEt players sprite
		player.setSprite(Directory.spriteLibrary.get("PlayerBattleIdle"));
	}

	/**
	 * Updates the player's ShopState
	 * Loops through all keyPresses since last update
	 * 		If a character is an operator or a number, appends it to the answerString
	 * 		If a character is a newLine buffer, calls answerEquation or submitEquation based on current target
	 * 		If a character is whitespace, togglesTarget and clears answerString
	 * 		If a character is backspace, clears answerString
	 * Updates the attached shape
	 */
	@Override
	public void update() {
		super.update();
		//Update shape in case of moving
		attachedTo.updateShape();

	}



	/**
	 * AttachedObject Exits the player's shop state
	 */
	@Override
	public void exit() {
		super.exit();
		
		/*
		//Set position back to worldPos
		attachedTo.setPos(worldPos);
		
		//Set dimensions back to world dimensions
		attachedTo.setWidth(worldWidth);
		attachedTo.setHeight(worldHeight);
		
		attachedTo.setSprite(worldSprite);
		*/
		
		//Set Sprite to null
		//attachedTo.setSprite(null);
	}

}
