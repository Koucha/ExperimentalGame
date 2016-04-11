package com.koucha.experimentalgame;

import com.koucha.experimentalgame.entitySystem.EntityManager;
import com.koucha.experimentalgame.entitySystem.SystemManager;
import com.koucha.experimentalgame.entitySystem.component.Guard;
import com.koucha.experimentalgame.entitySystem.system.InputProcessingSystem;
import com.koucha.experimentalgame.entitySystem.system.LocalPlayerInputSystem;
import com.koucha.experimentalgame.entitySystem.system.PhysicsSystem;
import com.koucha.experimentalgame.entitySystem.system.RenderSystem;
import com.koucha.experimentalgame.input.InputBridge;
import com.koucha.experimentalgame.input.InputEvent;
import com.koucha.experimentalgame.lwjgl.GLFWGraphicsHub;
import com.koucha.experimentalgame.rendering.Renderer;

import java.util.ResourceBundle;

/**
 * Contains the game loop
 */
public class BackBone
{
	// Specify which graphics implementation should be used
	public static final GraphicsHub GRAPHICS_HUB = new GLFWGraphicsHub();

	public static final float AVG_UPDATES_PER_SECOND = 100;

	public static final float SECOND_IN_NANOS = 1000000000;

	public PropertiesLoader propertiesLoader;

	private boolean running = false;

	private Renderer renderer;
	private InputBridge inputBridge;
	private EntityManager entityManager;
	private SystemManager systemManager;

	private int framesPerSecond;
	private int updatesPerSecond;

	/**
	 * Sets up all the basics needed for the {@link #loop()} to run
	 */
	private BackBone()
	{
		renderer = GRAPHICS_HUB.createRenderer();

		propertiesLoader = new PropertiesLoader();
		ResourceBundle bundle = propertiesLoader.loadBundle( "basicUI.mainWindow" );

		renderer.init();

		renderer.createFullscreenWindow( bundle.getString( "title" ) );

		inputBridge = new InputBridge();
		inputBridge.getInputMap().addLink( "Quit", 0x100, ( InputEvent evt ) -> running = false );

		//todo set up player etc.
		entityManager = new EntityManager();
		systemManager = new SystemManager( entityManager );

		systemManager.setRenderSystem( new RenderSystem( renderer ) );
		systemManager.add( new PhysicsSystem() );
		systemManager.add( new LocalPlayerInputSystem( inputBridge ) );
		systemManager.add( new InputProcessingSystem() );

		entityManager.add( EntityFactory.makePlayer() );

		renderer.setInputBridge( inputBridge );

		// todo debug
//		limitFPS = false;
	}

	public static void main( String[] args )
	{
		BackBone backBone = new BackBone();
		try
		{
			backBone.loop();
		} finally
		{
			backBone.cleanUp();
		}
	}

	/**
	 * Main loop, all the timing is done in here
	 */
	private void loop()
	{
		running = true;

		renderer.preLoopInitialization();

		final float deltaUpdateTimeNS = SECOND_IN_NANOS / AVG_UPDATES_PER_SECOND;

		long nowTimeNS = java.lang.System.nanoTime();
		long lastUpdateTimeNS = nowTimeNS;

		float frameTimeNS;
		float accumulatorNS = 2 * deltaUpdateTimeNS;
		float alpha;

		int frameCount = 0;
		int updateCount = 0;
		long secondTimerNS = nowTimeNS;

		while( running && !renderer.windowShouldClose() )
		{
			frameTimeNS = nowTimeNS - lastUpdateTimeNS;
			if( frameTimeNS > SECOND_IN_NANOS )
				frameTimeNS = SECOND_IN_NANOS;
			accumulatorNS += frameTimeNS;
			lastUpdateTimeNS = nowTimeNS;

			// update
			while( accumulatorNS >= deltaUpdateTimeNS )
			{
				Guard.switchBuffer();
				update();
				updateCount++;
				accumulatorNS -= deltaUpdateTimeNS;
			}

			nowTimeNS = java.lang.System.nanoTime();

			alpha = accumulatorNS / deltaUpdateTimeNS;
			render( alpha );
			frameCount++;

			if( nowTimeNS - secondTimerNS > SECOND_IN_NANOS )
			{
				secondTimerNS += SECOND_IN_NANOS;
				framesPerSecond = frameCount;
				frameCount = 0;
				updatesPerSecond = updateCount;
				updateCount = 0;

				java.lang.System.out.println( "FPS: " + framesPerSecond + ", UPS: " + updatesPerSecond );
			}
		}
	}

	/**
	 * Perform any cleanup that has to be done before the program ends
	 */
	private void cleanUp()
	{
		renderer.cleanUp();
	}

	/**
	 * Does all the calculations inside the game loop
	 */
	private void update()
	{
		renderer.initializeUpdateIteration();

		systemManager.update();
	}

	/**
	 * Does all the rendering inside the game loop
	 *
	 * @param alpha
	 */
	private void render( float alpha )
	{
		renderer.initializeRenderIteration();

		systemManager.render( alpha );

		renderer.initializeGUIRenderIteration();

		//todo hud.render( renderer );

		renderer.finishRenderIteration();
	}
}
