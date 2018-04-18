package code.objects.arch;

import code.utility.CustomTexture;
import code.utility.TexturedObject;
import code.utility.Utility.Direction;
import framework.engine.Scene;
import processing.core.PApplet;
import processing.core.PShape;

/**
 * A blocky archway to connect two walls.
 * 
 * @author harrymt
 * 
 */
public class Arch extends TexturedObject {
	
	// Arch size and location
	float x1; float z1;
	float wallLength;
	float archWidth;
	Direction direction;
	float height;

	float thickness = 50.f;

	PShape arch;
		
	/**
	 * Constructor. Load the texture of the arch and direction of archway.
	 * 
	 * @param parent Scene.
	 */
	public Arch(Scene parent, float startX, float startZ, float length, float h, Direction dir) {
		super(parent, new CustomTexture(parent.loadImage("./code/objects/stonewall/wall.png")));
		x1 = startX; z1 = startZ;
		wallLength = length;
		direction = dir;
		height = h;
		archWidth = length / 6; // Number of arches
	}

	/**
	 * Draw the arch.
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
			// Translate so the arch isn't in the ground.
			parent.translate(x1, -height, z1);
			
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
			
			drawArchWay(6);
		}
		parent.popStyle();
		parent.popMatrix();
	}
	
	/**
	 * Draw the actual archway.
	 */
	private void drawArchWay(int length) {
		float size = height / 6;
		
		// Calculate the middle point of the archway, rounded down
		double midpoint = length / 2;
		midpoint = Math.floor(midpoint); // round down
		
		// Number of blocks to draw for the arch horizontal part
		// aka. the height of the current archway block.
		double archAmount = midpoint;
		
		for (int i = 0; i < length; i++) {
			parent.pushMatrix();
				parent.translate(archWidth * i, 0);
				
				// If we have reached the middle part.
				if(i == midpoint) {
					// Have 2 blocks the same, in the middle
				} else if(i > midpoint) {
					archAmount++;
				} else {
					archAmount--;
				}
				
				drawArchOfHeight(size, archAmount);
			parent.popMatrix();
		}
		
	}
	
	/**
	 * Draw a block archway of a certain height.
	 * 
	 * @param size Size of block.
	 * @param numberOfBlocks Number of blocks to draw.
	 */
	private void drawArchOfHeight(float size, double numberOfBlocks) {
		for (int i = 0; i < numberOfBlocks + 1; i++) {
			drawTexturedCuboid(archWidth, size);
			parent.translate(0, size);
		}
		drawTexturedCuboid(archWidth, size);
	}
}
