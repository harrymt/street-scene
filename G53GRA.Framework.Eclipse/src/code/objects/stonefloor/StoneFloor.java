/**
 * 
 */
package code.objects.stonefloor;

import code.utility.CustomTexture;
import code.utility.TexturedObject;
import framework.engine.Scene;
import processing.core.PShape;

/**
 * Floor made of stone.
 * 
 * @author harrymt
 *
 */
public class StoneFloor extends TexturedObject {
	
	PShape floor;

	/**
	 * Constructor. Load the texture of the floor.
	 * 
	 * @param parent
	 */
	public StoneFloor(Scene parent) {
		super(parent, new CustomTexture(parent.loadImage("./code/objects/stonefloor/stone.jpg")));
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
			
			drawTexturedIrregularCuboid(size().y, 10.f, size().x);
		}
		parent.popStyle();
		parent.popMatrix();
	}
}
