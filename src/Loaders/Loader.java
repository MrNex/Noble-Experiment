package Loaders;

import java.io.File;
import java.util.HashMap;

public abstract class Loader<T> {

	//Attributes
	protected String path;
	protected String ext;
	private File dir;
	
	
	//Constructs a loader
	public Loader(String loadPath, String fileType) {
		path = loadPath;
		ext = fileType;
		
		dir = new File(path);
	}
	
	
	//Gets the current directory
	public File getDirectory(){
		return dir;
	}
	
	//Loads all files and returns a hashmap of loaded objects keyed by their filename
	abstract public HashMap<String, T> loadAll();

	
}
