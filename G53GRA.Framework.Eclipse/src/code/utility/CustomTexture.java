package code.utility;

import processing.core.PImage;

/**
 * Wrapper to the texture object.
 * 
 * @author harrymt
 *
 */
public class CustomTexture {
	public PImage texture;
	
	// Setup texture mapping properties
	public int min;
	public int max;

	/**
	 * Constructor. Load the texture and check if its loaded.
	 * @param url
	 */
	public CustomTexture(PImage t) {
		texture = t;
		min = 0;
		max = 1;

		if(!isLoaded()) {
			System.out.println("Texture '" + t.toString() + "' failed to load.");
		}
	}

	/**
	 * Has the texture been loaded or not.
	 * @return True if loaded, false if not.
	 */
	public boolean isLoaded() {
		return texture != null;
	}
}
