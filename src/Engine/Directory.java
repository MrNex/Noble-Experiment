package Engine;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import Engine.Manager.CollisionManager;
import Engine.Manager.InputManager;
import Engine.Manager.ProfileManager;
import Engine.Manager.ScreenManager;

//Main messenging class
public class Directory {
	//Attributes
	public static Engine engine;
	public static ProfileManager profile;
	public static InputManager inputManager;
	public static ScreenManager screenManager;
	public static CollisionManager collisionManager;
	
	public static HashMap<String, BufferedImage> imageLibrary;
	
}
