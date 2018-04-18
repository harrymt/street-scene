package code.objects.house;

import code.utility.CustomTexture;
import code.utility.TexturedObject;
import code.utility.Utility.Direction;
import framework.engine.Scene;
import processing.core.PApplet;

/**
 * A house with a roof of a different texture.
 * 
 * @author harrymt
 *
 */
public class House extends TexturedObject {
	// House size and location
	float x1; float z1;
	float wallLength;
	Direction direction;
	float thickness;
	float buildingHeight;
	float roofHeight = 150.f;
	
	/**
	 * Constructor. Load the textures for the house with a size and rotation.
	 * 
	 * @param parent Scene.
	 */
	public House(Scene parent, float startX, float startZ, float length, float t, float height, Direction dir) {
		super(parent,
				new CustomTexture(parent.loadImage("./code/objects/stonewall/wall.png")),
				new CustomTexture(parent.loadImage("./code/objects/house/wood.jpg"))
		);
		
		x1 = startX; z1 = startZ;
		wallLength = length;
		direction = dir;
		thickness = t;
		buildingHeight = height;
	}

	/**
	 * Draw the house.
	 * 
	 * @see framework.engine.DisplayableObject#display()
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
			
			parent.pushMatrix();
				drawBuilding();
				drawRoof();
			parent.popMatrix();
		}
		parent.popStyle();
		parent.popMatrix();
	}

	/**
	 * Draw the building.
	 */
	private void drawBuilding() {
		parent.translate(0, -buildingHeight, -thickness);
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
		drawTexturedIrregularCuboid(thickness, buildingHeight, wallLength);
	}
	
	/**
	 * Draw the roof.
	 */
	private void drawRoof() {
		float padding = 50.f;
		float sizeOfRoof = (thickness / 2) + padding;
		float lengthOfRoof = 0;
		
		// For houses with all equal walls.
		if(wallLength == 0) { 
			lengthOfRoof = sizeOfRoof;
			parent.translate(sizeOfRoof - padding , -roofHeight, sizeOfRoof - padding);
		} else {
			lengthOfRoof = wallLength + (padding * 2);
			
			parent.translate(sizeOfRoof - (padding * 2), -roofHeight, sizeOfRoof - (padding * 2));
		}
		
		parent.rotateX(PApplet.radians(180));
		
		drawPyramidWithOtherTexture(sizeOfRoof, roofHeight, lengthOfRoof);
	}
}
