import Engine.*;

public class Program {

	public static void main(String[] args) {
		
		//Create player profile
		Directory.profile = new ProfileManager();			//Constructs player
		//Create engine
		Directory.engine = new Engine();
		//Create input manager
		Directory.inputManager = new InputManager();
		//Create screen manager
		Directory.screenManager = new ScreenManager();
		
		//Start the engine
		Directory.engine.start();

	}

}
