/**
 * 
 */
package code.objects.skybox;

import code.utility.CustomTexture;
import code.utility.TexturedObject;
import framework.engine.Scene;
import processing.core.PShape;

/**
 * Skybox that represents a night sky.
 * 
 * @author harrymt
 *
 */
public class SkyBox extends TexturedObject {

	PShape skybox;

	/**
	 * Constructor. Load the texture of the skybox.
	 * 
	 * @param parent
	 */
	public SkyBox(Scene parent) {
		super(parent, new CustomTexture(parent.loadImage("./code/objects/skybox/nightsky.png")));
	}

	/**
	 * Display the skybox.
	 * 
	 * @see framework.engine.DisplayableObject#display()
	 * 
	 */
	@Override
	public void display() {
		parent.pushMatrix();
		parent.pushStyle();
		{
			parent.noStroke();
			parent.translate(pos.x, pos.y, pos.z);
			
			// Center
			parent.translate(-(size().x / 2), -(size().y / 2), -(size().z / 2));
			
			parent.noLights();
			drawInsideTexturedCuboid(size().x, size().y);
			parent.lights();
		}
		parent.popStyle();
		parent.popMatrix();
	}
}