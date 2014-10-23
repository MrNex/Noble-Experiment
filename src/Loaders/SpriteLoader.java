package Loaders;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import Objects.Sprites.Sprite;

public class SpriteLoader extends Loader<Sprite> {

	public SpriteLoader() {
		super("./Assets/Animation", ".sbp");
		// TODO Auto-generated constructor stub
	}

	@Override
	public HashMap<String, Sprite> loadAll() {
		//First get a hashmap of all images
		ImageLoader loader = new ImageLoader();
		HashMap<String, BufferedImage> imageMap = loader.loadAll();
		
		//Create a hashmap of sprites
		HashMap<String, Sprite> spriteMap = new HashMap<String, Sprite>();
		
		
		
		return null;
	}

}
