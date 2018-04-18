package code.camera;

import framework.engine.Scene;
import framework.utility.Camera;
import processing.core.PVector;
import code.Settings;

/**
 * Enables the camera to move along a fixed path and
 * grants the ability to stop this on keypress.
 * 
 * Setting DEBUG_MODE = true, in project settings, stops this animation
 * enabling the 'q' and 'e' keys to move the camera up and down.
 * 
 * @author harrymt
 * 
 */
public class CustomCamera extends Camera {
    private final static int PRESSED = 1;

    // Position of eye.
    private final float eyeY;
    private final float eyeX;
    private final float eyeZ;
    
    // Add the ability to move up and down using q and e (debug only)
    private int qKey;
    private int eKey;
   
    // Animation movement timer.
    private int movementTimer = 0;
    // How fast the animation goes.
    protected static float movingSpeed = 2.f;
    
    // Toggle key press to pause animation.
    private boolean KEY_PRESS = false;
	
    /**
     * Constructor.
     * 
     * @param parent Scene.
     * @param cameraX Position of camera.
     * @param cameraY Position of camera.
     */
    public CustomCamera(final Scene parent, final float cameraX, final float cameraZ, final float cameraY) {
        super(parent);
        qKey = 0;
        eKey = 0;
        eyeX = cameraX;
        eyeY = -cameraY;
        eyeZ = cameraZ;
        reset();
    }

    /**
     * Moves the camera along a fixed path, unless a key is pressed.
     */
    @Override
    public void update(float dT) {
        super.update(dT);

        if(Settings.DEBUG_MODE) {
            if (qKey == PRESSED) { eye = PVector.add(eye, PVector.mult(up, cameraSpeed)); }
            if (eKey == PRESSED) {  eye = PVector.sub(eye, PVector.mult(up, cameraSpeed)); }
        }

        if(KEY_PRESS) {
        	// Holding a key, pausing the camera movement
        } else {
        	if(!Settings.DEBUG_MODE) { moveCameraAlongPath(); }      
        }

    }

    /**
     * Toggles camera movement on key press.
     * On debug, e and q can move up and down.
     */
    @Override
    public void handleKey(char key, int state, int mX, int mY) {
        KEY_PRESS = !KEY_PRESS;
        
        if(Settings.DEBUG_MODE) {
    	   super.handleKey(key, state, mX, mY);
           
           switch (key) {
               case 'Q':
               case 'q':
                   qKey = state;
                   break;
               case 'E':
               case 'e':
                   eKey = state;
                   break;
           }
        }
    }

    /**
     * Resets the camera positions.
     */
    @Override
    public void reset() {
        super.reset();
        eye.y = eyeY;
        eye.x = eyeX;
        eye.z = eyeZ;
    }
    
    /**
     * Remove the ability to drag the mouse.
     */
    @Override
	public void handleMouseDrag(int mX, int mY) {
    	if(Settings.DEBUG_MODE) {
    		super.handleMouseDrag(mX, mY);
    	}
    }
    
    
    /**
     * Turn camera X axis.
     * 
     * @param mX Amount to move.
     */
    private void turnCameraByX(int mX) {
		float dL, sens = 0.03f;
		
		dL = (mX - pmouseX) * sens;
		view.add(PVector.mult(right, dL));
	
		calculateVectors();
		pmouseX = mX;
    }
    
   /**
    * Animate camera, by moving it along a fixed path.
    */
   private void moveCameraAlongPath() {
	   	movementTimer++;
		
		int movingSegments[] = {180, 400, 630, 830};
		int timeToMove = 56;
		int endTime = movingSegments[3] + timeToMove - 5;
		
		if(movementTimer > endTime) {
			movementTimer = 0;
			reset();
		
		} else if(movementTimer > movingSegments[3]
		    	&& movementTimer < movingSegments[3] + timeToMove - 1) {
					
			turnCameraByX(pmouseX + 1);
		
		} else if(movementTimer > movingSegments[2]
		    	&& movementTimer < movingSegments[2] + timeToMove) {
					
			turnCameraByX(pmouseX + 1);
		
		} else if(movementTimer > movingSegments[1]
		&& movementTimer < movingSegments[1] + timeToMove) {
			
			turnCameraByX(pmouseX + 1);
			
		} else if (movementTimer > movingSegments[0]
				&& movementTimer < movingSegments[0] + timeToMove) {
			
			turnCameraByX(pmouseX + 1);
		}
		
		eye = PVector.add(eye, PVector.mult(forward, movingSpeed));
    }
}