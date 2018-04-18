package code.objects.streetlamp;

import framework.engine.Scene;
import framework.interfaces.Animation;
import framework.interfaces.Lighting;
import processing.core.PApplet;
import processing.core.PShape;
import java.util.Random;

import code.utility.CustomTexture;
import code.utility.TexturedObject;

/**
 * A street light with a base, pole, neck and light.
 * Can be rotated and the light can flicker on and off.
 * 
 * @author harrymt
 *
 */
public class StreetLamp extends TexturedObject implements Lighting, Animation {

	// Setup the lighting
	float strength = 1.0f;
	float colour = 255.f;
	float ambient[]  = new float[] {strength, strength, strength};
	float diffuse[]  = new float[] {colour, colour, colour - 1f};
	float specular[] = new float[] {colour, colour, colour};

	// Animation flickering properties
	private boolean showLight = true;
	private float animationTimer = 0;
	private final float maxFlickerInterval = 100;
	private final float minFlickerInverval = 20;
	private float flickerInterval = 50;
	public static Random RANDOM_NUMBER = new Random(System.nanoTime());
	
	PShape streetLamp;
	float rotation;

	// Make the light flicker on and off randomly.
	private boolean flickerEnabled;
	
	/**
	 * Constructor. Load the texture of the street lamp.
	 * 
	 * @param parent Scene.
	 */
	public StreetLamp(Scene parent, float size) {
		this(parent, size, 0.f, false);
	}
	
	/**
	 * Constructor. Load the texture of the street lamp with a rotation.
	 * 
	 * @param parent Scene.
	 */
	public StreetLamp(Scene parent, float size, float rot, boolean doesFlicker) {
		super(parent, new CustomTexture(parent.loadImage("./code/objects/streetlamp/metal.png")));
		this.size(size);
		rotation = rot;
		flickerEnabled = doesFlicker;
	}

	/**
	 * Animate the flickering light.
	 */
	@Override
	public void update(float dT) {
		if(!flickerEnabled) { return; }
	
		animationTimer++;
		
		if(animationTimer > flickerInterval) {
			showLight = !showLight; // Toggle light
			animationTimer = 0;
			// Generate a random flicker interval
			flickerInterval = minFlickerInverval + RANDOM_NUMBER.nextFloat() * (maxFlickerInterval - minFlickerInverval);
		}
	}
	
	/**
	 * Setup the lighting of the lamp, using a Point Light.
	 * 
	 * @see framework.interfaces.Lighting#setupLighting()
	 */
	@Override
	public void setupLighting() {
		parent.lightSpecular(specular[0],specular[1],specular[2]); 
		parent.ambientLight(ambient[0],ambient[1],ambient[2]);
		
		float x = pos.x + size().x * 2 - (rotation == 180.f ? 0.f : 400.f);
		float y = pos.y + (size().y / 2) + (rotation == 180.f ? 0.f : 200.f);
		float z = pos.z + size().z - (rotation == 180.f ? 0.f : 300.f);
		
		if(showLight) {
			parent.pointLight(diffuse[0],diffuse[1],diffuse[2], x, y, z);
		}
		
		parent.lightSpecular(0,0,0);
	}

	/**
	 * Display the lamp.
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
			
			drawStreetLamp();
		}
		parent.popStyle();
		parent.popMatrix();
	}


	/**
	 * Draw the actual street lamp.
	 */
	private void drawStreetLamp() {
		float baseHeight = this.size().x / 5;
		float poleHeight = this.size().x * 2;
		float poleWidth = this.size().x / 3;
		
		parent.rotateY(PApplet.radians(rotation));			

		parent.pushMatrix();
	        drawBase(100f, baseHeight);
	        
	    	parent.pushMatrix();
	  
	        	drawPole(poleWidth, poleHeight);
	        	
	        	drawNeck(poleWidth, poleHeight / 2);

	        	parent.translate(-poleWidth, (poleHeight / 2) - (poleWidth / 2), poleWidth / 2);
	        	drawLight(20f);

	        parent.popMatrix();
        parent.popMatrix();
	}

	
	/**
	 * Draw the base of the street lamp
	 * @param width Width of base.
	 * @param height Height of base.
	 */
	private void drawBase(float width, float height) {
		parent.translate(0, -height);
		parent.pushMatrix();
		 drawTexturedCuboid(width, height);
    	parent.popMatrix();
	}

	/**
	 * Draw the pole of the street lamp.
	 * @param width Width of pole.
	 * @param height Height of pole.
	 */
	private void drawPole(float width, float height) {
		parent.translate(0, -height, 0);
		drawTexturedCuboid(width, height);
	}

	/**
	 * Draw the neck of the street lamp.
	 * @param width Width of neck.
	 * @param height Height of neck.
	 */
	private void drawNeck(float width, float height) {
		// Move into the right position
    	parent.rotateZ(PApplet.radians(-90.f));   	
    	parent.rotateX(PApplet.radians(45.f));
    	parent.translate(0, 0, -(width / 2));
		drawTexturedCuboid(width, height);
	}

	/**
	 * Draw the actual light to the street lamp.
	 * @param size Size of the ball.
	 */
	private void drawLight(float size) {	
		parent.noLights();
		parent.pushMatrix();
		parent.pushStyle();

			// Style light source to match diffuse color
			if(showLight) {
				parent.fill((int) diffuse[0], (int) diffuse[1], (int) diffuse[2]);
			} else {
				parent.fill(50, 50, 45); // dark white
			}
			parent.noStroke();
			parent.sphere(size);

		parent.popStyle();
		parent.popMatrix();
		parent.lights();
	}
}
