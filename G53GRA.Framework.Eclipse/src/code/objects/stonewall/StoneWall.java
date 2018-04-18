package code.objects.stonewall;

import code.utility.CustomTexture;
import code.utility.TexturedObject;
import code.utility.Utility.Direction;
import framework.engine.Scene;
import processing.core.PApplet;
import processing.core.PShape;

/**
 * A wall with a single texutre and a pre-defined thickness.
 * 
 * @author harrymt
 *
 */
public class StoneWall extends TexturedObject {
	// Wall size and location
	float x1; float z1;
	float wallLength;
	Direction direction;
	float height;

	float thickness = 25.f;

	PShape wall;
		
	/**
	 * Constructor. Load the texture of the wall with a direction.
	 * 
	 * @param parent
	 */
	public StoneWall(Scene parent, float startX, float startZ, float length, float h, Direction dir) {
		super(parent, new CustomTexture(parent.loadImage("./code/objects/stonewall/wall.png")));
		x1 = startX; z1 = startZ;
		wallLength = length;
		direction = dir;
		height = h;
	}

	/**
	 * Draw the wall.
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
			
			// Move to first point
			parent.translate(x1, 0, z1);
			
			// Translate so the wall isn't in the ground.
			parent.translate(0, -height, -thickness);
			
			switch(direction) {
				case WEST:
					parent.rotateY(PApplet.radians(180));
				case NORTH:
					parent.rotateY(PApplet.radians(90));
				case SOUTH:
					parent.rotateY(PApplet.radians(-90));
				break;
			default:
				break;
			}
			
			drawTexturedIrregularCuboid(thickness, height, wallLength);
		}
		parent.popStyle();
		parent.popMatrix();
	}
}
