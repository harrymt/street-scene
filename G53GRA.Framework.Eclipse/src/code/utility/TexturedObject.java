package code.utility;

import framework.engine.DisplayableObject;
import framework.engine.Scene;
import processing.core.PApplet;

/**
 * Sits ontop of a displayable object, used for texturing objects
 * with the same texture easily.
 * 
 * @author harrymt
 *
 */
public class TexturedObject extends DisplayableObject {
	
	// The texture.
	protected CustomTexture texture;
	
	// If object requires additional textures
	protected CustomTexture anotherTexture;
	
	/**
	 * Constructor.
	 * 
	 * @param parent Parent object.
	 * @param texture Texture in question.
	 */
	public TexturedObject(Scene parent, CustomTexture text) {
		super(parent);
		texture = text;
		anotherTexture = null;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param parent Parent object.
	 * @param texture Texture in question.
	 * @param texture Texture another texture.
	 */
	public TexturedObject(Scene parent, CustomTexture text, CustomTexture anotherText) {
		super(parent);
		texture = text;
		anotherTexture = anotherText;
	}
	
	/**
	 * Unused.
	 */
	@Override
	public void display() {}

	/**
	 * Draw a texture pyramid.
	 *
	 * @param width width of pyramid
	 * @param height height of pyramid
	 * @param length length of pyramid
	 */
	protected void drawPyramidWithOtherTexture(float width, float height, float length) {
		if(anotherTexture.isLoaded()) {
			parent.noStroke();
			parent.texture(anotherTexture.texture); // bind texture to shape
		}

		parent.beginShape(PApplet.TRIANGLES); if(anotherTexture.isLoaded()) parent.texture(anotherTexture.texture);

			// Front
			parent.vertex(length, -height, width, anotherTexture.max, anotherTexture.max);
			parent.vertex(-length, -height, width, anotherTexture.min, anotherTexture.max);
	        parent.vertex(0, height, 0, anotherTexture.min, anotherTexture.min);
	
	        // Right
	        parent.vertex(length, -height, -width, anotherTexture.max, anotherTexture.max);
	        parent.vertex(length, -height, width, anotherTexture.min, anotherTexture.max);
	        parent.vertex(0, height, 0, anotherTexture.min, anotherTexture.min);
	       
	        // Back
	        parent.vertex(-length, -height, -width, anotherTexture.max, anotherTexture.max);
	        parent.vertex(length, -height, -width, anotherTexture.min, anotherTexture.max);
	        parent.vertex(0, height, 0, anotherTexture.min, anotherTexture.min);
	
	        // Left
	        parent.vertex(-length, -height, width, anotherTexture.max, anotherTexture.max);
	        parent.vertex(-length, -height, -width, anotherTexture.min, anotherTexture.max);
	        parent.vertex(0, height, 0, anotherTexture.min, anotherTexture.min);
			
        parent.endShape();
      
        // Bottom
        parent.beginShape(PApplet.QUAD); if(anotherTexture.isLoaded()) parent.texture(anotherTexture.texture);
	        parent.vertex(length, -height, -width, anotherTexture.min, anotherTexture.min);
	        parent.vertex(-length, -height, -width, anotherTexture.min, anotherTexture.max);
	        parent.vertex(-length, -height, width, anotherTexture.max, anotherTexture.max);
	        parent.vertex(length, -height, width, anotherTexture.max, anotherTexture.min);
        parent.endShape();
	}
	
	
	/**
	 * Draw a textured Cuboid, texture on the outside of shape.
	 *
	 * @param width width of cuboid
	 * @param height height of cuboid
	 * @param length length of cuboid
	 */
	protected void drawTexturedIrregularCuboid(float width, float height, float length) {
		if(texture.isLoaded()) {
			parent.noStroke();
			parent.texture(texture.texture); // bind texture to shape
		}

		// Top
		drawSide(
		   new float[][] {
				  new float[] { 0, height, 0 },
				  new float[] { 0, 0, 0 },
				  new float[] { width + length, 0, 0 },
				  new float[] { width + length, height, 0 }
		   }
	   );

       // Bottom
		drawSide(
		   new float[][] {
				  new float[] { width + length, height, 0 },
				  new float[] { width + length, 0, 0 },
				  new float[] { width + length, 0, width },
				  new float[] { width + length, height, width }
		   }
	   );

       // Far side
		drawSide(
		   new float[][] {
				  new float[] { width + length, height, width },
				  new float[] { width + length, 0, width },
				  new float[] { 0, 0, width },
				  new float[] { 0, height, width }
		   }
	   );

       // Left
		drawSide(
		   new float[][] {
				  new float[] { 0, height, width },
				  new float[] { 0, 0, width },
				  new float[] { 0, 0, 0 },
				  new float[] { 0, height, 0 }
		   }
	   );

       // Right
		drawSide(
		   new float[][] {
				  new float[] { 0, height, width },
				  new float[] { 0, height, 0 },
				  new float[] { width + length, height, 0 },
				  new float[] { width + length, height, width }
		   }
	   );

       // Left
		drawSide(
		   new float[][] {
				  new float[] { 0, 0, 0 },
				  new float[] { 0, 0, width },
				  new float[] { width + length, 0, width },
				  new float[] { width + length, 0, 0 }
		   }
	   );
	}
	
	
	/**
	 * Draw a textured Cuboid, texture on the outside of shape.
	 *
	 * @param width width of cuboid
	 * @param height height of cuboid
	 */
	protected void drawTexturedCuboid(float width, float height) {
		if(texture.isLoaded()) {
			parent.noStroke();
			parent.texture(texture.texture); // bind texture to shape
		}

		// Top
		drawSide(
		   new float[][] {
				  new float[] { 0, height, 0 },
				  new float[] { 0, 0, 0 },
				  new float[] { width, 0, 0 },
				  new float[] { width, height, 0 }
		   }
	   );

       // Bottom
		drawSide(
		   new float[][] {
				  new float[] { width, height, 0 },
				  new float[] { width, 0, 0 },
				  new float[] { width, 0, width },
				  new float[] { width, height, width }
		   }
	   );

       // Near side
		drawSide(
		   new float[][] {
				  new float[] { width, height, width },
				  new float[] { width, 0, width },
				  new float[] { 0, 0, width },
				  new float[] { 0, height, width }
		   }
	   );

       // Far side
		drawSide(
		   new float[][] {
				  new float[] { 0, height, width },
				  new float[] { 0, 0, width },
				  new float[] { 0, 0, 0 },
				  new float[] { 0, height, 0 }
		   }
	   );

       // Right
		drawSide(
		   new float[][] {
				  new float[] { 0, height, width },
				  new float[] { 0, height, 0 },
				  new float[] { width, height, 0 },
				  new float[] { width, height, width }
		   }
	   );

       // Left
		drawSide(
		   new float[][] {
				  new float[] { 0, 0, 0 },
				  new float[] { 0, 0, width },
				  new float[] { width, 0, width },
				  new float[] { width, 0, 0 }
		   }
	   );
	}
	

	/**
	 * Draw a textured Cuboid, texture on the inside of shape.
	 *
	 * @param width width of cuboid
	 * @param height height of cuboid
	 */
	protected void drawInsideTexturedCuboid(float width, float height) {

		if(texture.isLoaded()) {
			parent.noStroke();
			parent.texture(texture.texture); // bind texture to shape
		}

        // Left
		drawSide(
        new float[][] {
           new float[] { width, 0, 0 },
           new float[] { width, 0, width },
           new float[] { 0, 0, width },
           new float[] { 0, 0, 0 }
        }
      );

        // Right
        drawSide(
        new float[][] {
           new float[] { width, height, width },
           new float[] { width, height, 0 },
           new float[] { 0, height, 0 },
           new float[] { 0, height, width }
        }
      );
        

        // Near side
        drawSide(
        new float[][] {
           new float[] { 0, height, 0 },
           new float[] { 0, 0, 0 },
           new float[] { 0, 0, width },
           new float[] { 0, height, width }
        }
      );
        // Far side
        drawSide(
        new float[][] {
           new float[] { 0, height, width },
           new float[] { 0, 0, width },
           new float[] { width, 0, width },
           new float[] { width, height, width }
        }
      );
        // Bottom
        drawSide(
        new float[][] {
           new float[] { width, height, width },
           new float[] { width, 0, width },
           new float[] { width, 0, 0 },
           new float[] { width, height, 0 }
        }
      );

   // Top
        drawSide(
      new float[][] {
         new float[] { width, height, 0 },
         new float[] { width, 0, 0 },
         new float[] { 0, 0, 0 },
         new float[] { 0, height, 0 }
      }
	   );
	}
	
	
	/**
	 * Draw a series of vertex of a side of an object.
	 * 
	 * @param array Vertex array of points.
	 */
	private void drawSide(float[][] array) {
		parent.beginShape(PApplet.QUAD); if(texture.isLoaded()) parent.texture(texture.texture);
	        parent.vertex(array[0][0], array[0][1], array[0][2], texture.min, texture.min);
	        parent.vertex(array[1][0], array[1][1], array[1][2], texture.min, texture.max);
	        parent.vertex(array[2][0], array[2][1], array[2][2], texture.max, texture.max);
	        parent.vertex(array[3][0], array[3][1], array[3][2], texture.max, texture.min);
        parent.endShape();
	}

}
