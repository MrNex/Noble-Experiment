package loaders;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;

/**
 * Defines an object  which loads objects of a generic type T from a filePath.
 * @author Nex
 *
 * @param <T>
 */
public abstract class Loader<T> {

	//Attributes
	protected String path;
	protected String ext;
	private File dir;

	/**
	 * Constructs a loader
	 * @param loadPath Filepath to load from
	 * @param fileType File extension to load
	 */
	public Loader(String loadPath, String fileType) {
		path = loadPath;
		ext = fileType;

		dir = new File(path);
	}


	/**
	 * Gets the current directory
	 * @return File object instantiated at loadPath
	 */
	public File getDirectory(){
		return dir;
	}

	/**
	 * Returns an array of files in the loaded filePath that end with the specified extension
	 * which this loader is designed to load
	 * @return An array of files in the loader's given directory which end with the given extension
	 */
	public File[] getValidFiles(){
		return getDirectory().listFiles(
				new FilenameFilter(){

					@Override
					public boolean accept(File arg0, String arg1) {
						// TODO Auto-generated method stub
						return arg1.endsWith(ext);
					}

				});
	}

	/**
	 * Loads all files and returns a hashmap of loaded objects keyed by their filename
	 * @return A hashmap of all objects loaded from files keyed by their  filenames
	 */
	abstract public HashMap<String, T> loadAll();


}
