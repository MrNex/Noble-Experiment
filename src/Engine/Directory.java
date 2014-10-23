package Engine;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import Engine.Manager.CollisionManager;
import Engine.Manager.InputManager;
import Engine.Manager.ProfileManager;
import Engine.Manager.ScreenManager;
import Engine.Manager.SpriteManager;
import Objects.Sprites.Sprite;

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
