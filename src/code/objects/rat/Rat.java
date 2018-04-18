package code.objects.rat;

import code.utility.CustomTexture;
import code.utility.TexturedObject;
import framework.engine.Scene;
import framework.interfaces.Animation;
import processing.core.PApplet;
import processing.core.PShape;

/**
 * Rat that wags it's tail and runs around.
 * 
 * @author harrymt
 */
public class Rat extends TexturedObject implements Animation {
	// Wagging tail animation
	float maxRotationAmount 		 = 30.f;
	float animationRotationAmount    = -maxRotationAmount;
	boolean ascendingTailRotation = true;

    // Animation movement timer.
    private int movementTimer = 0;
    
    // How fast the rat moves.
    protected static float movingSpeed = 2.f;
    
    // The direction the rat is moving
    private boolean ratGoingRight = true;
    
    // The rotation degree amount of the rat
    private int ratRotationAmount = 180;
    
    // The times the rat will change animation
    private int ratMovingSegments[] = {210, 600};
    
    // The time the rat will be rotating for
    private int angleToMoveRat = 180;
    
    // The end time of the rats animation
    private int endTime = ratMovingSegments[1] + angleToMoveRat - 1;
    
    // Is the rat rotating or not
    private boolean isRatTurning = false;
    
	// Size of the rat
	float block_size = 10.f;
	float double_block_size = block_size * 2;
	float half_block_size = block_size / 2;
	
	PShape rat;
	
	/**
	 * Constructor. Load the texture of the rat.
	 * 
	 * @param parent Scene.
	 */
	public Rat(Scene parent) {
		super(parent, new CustomTexture(parent.loadImage("./code/objects/rat/fur.jpg")));
	}

	/**
	 * Animate the rats tail.
	 */
	@Override
	public void update(float dT) {
		handleTailWagging();
		handleRatMovement(dT);
	}
    
	/**
	 * Handles the animation to move the rat along a path.
	 * 
	 * @param dT change in time since previous call
	 */
	private void handleRatMovement(float dT) {
		isRatTurning = false;
		movementTimer++;

		if(movementTimer > endTime) {
			movementTimer = 0;

		} else if(movementTimer > ratMovingSegments[1]
			   && movementTimer < ratMovingSegments[1] + angleToMoveRat) {
			
			// Rotate the rat by 180 degrees
			isRatTurning = true;
			ratRotationAmount--;
			ratGoingRight = true;
			
		} else if (movementTimer > ratMovingSegments[0]
				&& movementTimer < ratMovingSegments[0] + angleToMoveRat) {
			
			// Rotate the rat by 180 degrees
			isRatTurning = true;
			ratRotationAmount++;
			ratGoingRight = false;
		}
		
		if (!isRatTurning) {
			float ratXPos = this.position().x + (ratGoingRight ? movingSpeed : -movingSpeed);

			this.position(ratXPos, this.position().y, this.position().z);
		}
	}

	/**
	 * Handles the animation to wag the rats tail.
	 */
	private void handleTailWagging() {
		
		if (ascendingTailRotation) {
			animationRotationAmount++;
		} else {
			animationRotationAmount--;
		}
		
		if(animationRotationAmount == maxRotationAmount) {
			ascendingTailRotation = false;
		}
		
		if(animationRotationAmount == -maxRotationAmount) {
			ascendingTailRotation = true;
		}
	}
	
	/**
	 * Display the rat.
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
			parent.rotateY(PApplet.radians(ratRotationAmount));
			
			drawRat();
		}
		parent.popStyle();
		parent.popMatrix();
	}


	/**
	 * Draw the actual rat.
	 */
	private void drawRat() {
		parent.pushMatrix();
			parent.translate(0, -double_block_size - half_block_size); // Move above the floor + the feet
			
			drawBody();
			
			parent.pushMatrix();
				drawHead();
				parent.pushMatrix();
					drawEar(true); // left ear
				parent.popMatrix();
				parent.pushMatrix();
					drawEar(false); // right ear
				parent.popMatrix();
			parent.popMatrix();
			
			parent.pushMatrix();
				drawTail();
			parent.popMatrix();
							
			parent.pushMatrix();
				drawFoot(-half_block_size);
			parent.popMatrix();
			
			parent.pushMatrix();
				drawFoot(double_block_size);
			parent.popMatrix();
		
        parent.popMatrix();
	}


	/**
	 * Draws a rat ear on the left or right side.
	 * 
	 * @param isLeft Left or right ear.
	 */
	private void drawEar(boolean isLeft) {
		
		float translation = (isLeft ? (half_block_size - (half_block_size / 6)) : block_size);
		parent.translate(block_size, -half_block_size, translation);
		parent.rotateY(PApplet.radians(90));
		
		drawTexturedIrregularCuboid(half_block_size / 3, half_block_size, half_block_size / 2);
	}

	/**
	 * Draws a rat foot at the location.
	 * 
	 * @param location Where to draw the foot.
	 */
	private void drawFoot(float location) {
		parent.translate(location, double_block_size + half_block_size);
		
		parent.rotateX(PApplet.radians(90));
		drawTexturedIrregularCuboid(half_block_size, double_block_size, block_size);
	}

	/**
	 * Draw the body of the rat.
	 */
	private void drawBody() {
		drawTexturedIrregularCuboid(double_block_size, double_block_size, double_block_size);
	}
	
	/**
	 * Draw rats head.
	 */
	private void drawHead() {
		parent.translate(-double_block_size, half_block_size, half_block_size); // Move to head
		drawTexturedIrregularCuboid(block_size, block_size, block_size);
	}
	
	/**
	 * Draw the tail of the rat.
	 */
	private void drawTail() {
		float centerPoint = (block_size / 4) * 3;
		float tailLength = double_block_size * 2;
		parent.translate(tailLength, centerPoint, centerPoint); // Move to middle, back, of body to draw tail
		parent.rotateY(PApplet.radians(animationRotationAmount));
		drawTexturedIrregularCuboid(half_block_size, half_block_size, tailLength);
	}
}