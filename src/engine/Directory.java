package engine;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import engine.manager.CollisionManager;
import engine.manager.InputManager;
import engine.manager.ProfileManager;
import engine.manager.ScreenManager;
import engine.manager.SpriteManager;
import sprites.Sprite;

/**
 * This class defines the main messenging system for the engine.
 * @author Nex
 *
 */
public class Directory {
	//Attributes
	public static Engine engine;
	public static ProfileManager profile;
	public static InputManager inputManager;
	public static ScreenManager screenManager;
	public static CollisionManager collisionManager;
	public static SpriteManager spriteManager;
	
	//public static HashMap<String, BufferedImage> imageLibrary;
	public static HashMap<String, Sprite> spriteLibrary;	
}
