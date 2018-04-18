package code;

import processing.core.PApplet;

/**
 * The main class to start the Scene.
 */
public class Main {

	/**
	 * Main method called when first running the Framework.
	 * Creates an instance of {@link MyScene}.
	 * @see MyScene
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		String[] a = {"MAIN"};
        PApplet.runSketch(a, new MyScene());
	}
}
