package code;

import framework.engine.*;		// import framework classes
import code.objects.arch.Arch;
import code.objects.house.House;
import code.objects.rat.Rat;
import code.objects.skybox.SkyBox;
import code.objects.stonefloor.StoneFloor;
import code.objects.stonewall.StoneWall;
import code.objects.streetlamp.StreetLamp;
import code.utility.Utility.Direction;
import code.camera.CustomCamera;

/**
 * The main start Scene for the project.
 * 
 * Inherits {@code framework.}{@link Scene}.
 * 
 * @author Harry Mumford-Turner
 * @version 1.0.0
 */
public class MyScene extends Scene {
	
	/**
	 * Setup your {@code Scene} in this method. Create any {@code DisplayableObject}s and {@code AnimatedObject}s
	 * and add them to the {@code Scene}.
	 * <p>
	 * Call {@code super.initialise()} to setup the framework's default  {@linkplain framework.utility.Camera Camera} and {@linkplain Scene#projection() projection} settings.
	 * @see DisplayableObject
	 * @see framework.interfaces.Animation Animation
	 * @see framework.utility.Camera Camera
	 * @see framework.interfaces.Input Input
	 */
	@Override
	public void initialise() {		
		setBackgroundColour(0.f,0.f,0.f,1.f); // set clear colour to black
		textureMode(NORMAL);
	
		float base_size = 100.f;
		float floor_width = base_size * 7;
		float offset = floor_width / 3;
		
		StoneFloor floor = new StoneFloor(this);
		// Position the floor so we draw half on 1 side, half on the other
		floor.position(-floor_width, 0, 0);
		floor.size(floor_width, floor_width, 0);
		addObjectToScene(floor);
		
		SkyBox sky = new SkyBox(this);
		sky.size(floor_width * 5); 
		addObjectToScene(sky);


		/** 
		 * --- Draw Right Hand Side Part --- 
		 */
		
		/* - Lighting - */
		float lightGap = 50.f;
		StreetLamp lamp1 = new StreetLamp(this, base_size);
		lamp1.position(lightGap, 0, lightGap);
		addObjectToScene(lamp1, "lamp");
		
		StreetLamp lamp2 = new StreetLamp(this, base_size, 180, !Settings.DEBUG_MODE);		
		lamp2.position(floor_width - lightGap, 0.f, floor_width - lightGap);
		addObjectToScene(lamp2, "lamp2");
		
		/* - Walls - */
		StoneWall wall1 = new StoneWall(this, 
				0, 0, 
				floor_width, 600.f,
				Direction.EAST);
		addObjectToScene(wall1, "wall_top");

		StoneWall wall2 = new StoneWall(this, 
				0, floor_width, 
				floor_width, 250.f, 
				Direction.EAST);
		addObjectToScene(wall2, "wall_bottom");
		
		StoneWall wall3 = new StoneWall(this, 
				floor_width, 0, 
				floor_width, 300.f, 
				Direction.SOUTH);
		addObjectToScene(wall3, "wall_right");
		

		/* - Inner Wall - */
		House house_inside = new House(this, 
				offset, offset * 2, 
				0, offset, 
				250.f,
				Direction.EAST);
		addObjectToScene(house_inside, "house_middle_part");
		
		
		Arch arch = new Arch(this, 
				floor_width / 2, 0, 
				offset, 250.f, 
				Direction.SOUTH);
		addObjectToScene(arch, "top_arch");
		
		Arch arch2 = new Arch(this, 
				-(offset / 2), offset, 
				offset, 250.f, 
				Direction.SOUTH);
		addObjectToScene(arch2, "left_arch");
		
		
		
		/** 
		 * --- Draw Left Hand Side Part --- 
		 */
		House house1 = new House(this, 
				-floor_width + offset, offset, 
				floor_width - offset, offset, 
				500.f,
				Direction.SOUTH);
		addObjectToScene(house1, "house_far_left");

		
		House house2 = new House(this, 
				0, offset, 
				0, offset, 
				300.f,
				Direction.SOUTH);
		addObjectToScene(house2, "house_top");
		
		House house3 = new House(this, 
				0, floor_width + offset, 
				floor_width - (offset * 2), offset, 
				300.f,
				Direction.WEST);
		addObjectToScene(house3, "house_bottom");
		
		
		/**
		 * Draw Rat
		 */
		Rat rat1 = new Rat(this);
		rat1.position(offset / 2, 0.f, (offset / 2) + (offset / 3));
		addObjectToScene(rat1);
		
		Rat rat2 = new Rat(this);
		rat2.position(offset / 3, 0.f, floor_width - (offset / 2));
		addObjectToScene(rat2);
		
		
		// Setup a custom camera to enable upwards and downwards movement.
        int cameraHeight = 100; int cameraXOffset = 80; int cameraZOffset = (int) floor_width - 120; // Don't start in the floor or walls
		camera = new CustomCamera(this, cameraXOffset, cameraZOffset, cameraHeight);
        projection();
	}
	
	
	/**
	 * Override default global lighting.
	 * @see #lights()
	 */
	@Override
	protected void globalLighting(){
		// super.globalLighting();		// DISABLE GLOBAL LIGHTING WHEN IMPLEMENTING OWN
		// ambientLight(255.f,255.f,255.f);
	}
	
	/**
	 * Override default reshape function. Called during every iteration of {@link #draw()}.
	 * Use this method to handle resizing objects based on your window size.
	 * @see #getObject(String)
	 * @see #projection()
	 */
	protected void reshape(){
		super.reshape();
	}
	
	/**
	 * Override default initial window size (600x400). Adjust variables in {@code super} class to change values.
	 */
	@Override
	protected void setInitWindowSize(){
		super.initWidth = 1200;	// must override variables in super class to affect size
		super.initHeight = 700;
	}
	
	/**
	 * Override projection properties here. Remove call to {@code super.projection()} and replace with
	 * perspective mode.
	 * @see #perspective(float, float, float, float)
	 * @see #ortho(float, float, float, float, float, float)
	 * @see #frustum(float, float, float, float, float, float)
	 */
	@Override
	protected void projection(){
		// super.projection();	// calls default projection setup in Scene (orthographic)
		perspective(radians(60.f), (float) width / (float) height, 1.f, 4000.f);
	}
	
}
