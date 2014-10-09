import Engine.*;
import Loaders.*;

public class Program {

	public static void main(String[] args) {
		
		
		//Initialize loader
		Loader imageLoader = new ImageLoader();
		
		//Get images
		Directory.imageLibrary = imageLoader.loadAll();
		
		//Create engine
		Directory.engine = new Engine();
		//Create Collision manager
		Directory.collisionManager = new CollisionManager();
		//Create input manager
		Directory.inputManager = new InputManager();
		//Create screen manager
		Directory.screenManager = new ScreenManager();
		//Create player profile
		Directory.profile = new ProfileManager();			//Constructs player
		
		//Start the engine
		Directory.engine.start();

	}

}
