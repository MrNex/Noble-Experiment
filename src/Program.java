import engine.*;
import loaders.*;

/**
 * Defines the entry point for the program
 * @author Nex
 *
 */
public class Program {

	/**
	 * Entry point for the program
	 * @param args Expecting no arguments.
	 */
	public static void main(String[] args) {
		//Create engine
		Directory.engine = new Engine();
		
		//Start the engine
		Directory.engine.start();

	}

}
