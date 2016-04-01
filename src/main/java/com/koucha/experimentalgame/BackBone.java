package com.koucha.experimentalgame;

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
	public static final float MAX_FRAMES_PER_SECOND = 60;

	public static final float SECOND_IN_NANOS = 1000000000;

	public PropertiesLoader propertiesLoader;

	private boolean running = false;

	private Renderer renderer;
	private InputBridge inputBridge;
	private boolean limitFPS = false;
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

		renderer.setInputBridge( inputBridge );

		// if v-sync is not done by renderer, the FPS will be limited manually
		limitFPS = !renderer.vSyncEnabled();

		// todo debug
//		limitFPS = false;
	}

	public static void main( String[] args )
	{
		BackBone backBone = new BackBone();
		try
		{
			backBone.loop();
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		} finally
		{
			backBone.cleanUp();
		}
	}

	/**
	 * Main loop, all the timing is done in here
	 */
	private void loop() throws InterruptedException
	{
		running = true;

		renderer.preLoopInitialization();

		final long deltaRenderTimeNS = ( long )( SECOND_IN_NANOS / MAX_FRAMES_PER_SECOND );
		final long deltaRender5TimeNS = 5 * deltaRenderTimeNS;
		final float deltaUpdateTimeNS = SECOND_IN_NANOS / AVG_UPDATES_PER_SECOND;

		long lastRenderTimeNS = java.lang.System.nanoTime();
		long lastUpdateTimeNS = lastRenderTimeNS;
		long nowTimeNS = lastRenderTimeNS;

		float updatesToBeDone = 0;

		int frameCount = 0;
		int updateCount = 0;
		long secondTimerNS = lastRenderTimeNS;

		while( running && !renderer.windowShouldClose() )
		{
			updatesToBeDone += (nowTimeNS - lastUpdateTimeNS) / deltaUpdateTimeNS;        // ex.: 1.4 = 0.5 + 0.9 (0.5: remainder from last iteration, 0.9: from this iteration)
			lastUpdateTimeNS = nowTimeNS;

			// update
			while( updatesToBeDone >= 1 )
			{
				update();
				updateCount++;
				updatesToBeDone--;        // ex.: 0.4 = 1.4--  (0.4: remainder from this iteration)
			}

			if( limitFPS )
			{
				while( nowTimeNS - lastRenderTimeNS < deltaRenderTimeNS )
				{
					Thread.yield();
					nowTimeNS = java.lang.System.nanoTime();
				}
				if( nowTimeNS - lastRenderTimeNS > deltaRender5TimeNS )
				{
					lastRenderTimeNS = nowTimeNS;
				}else
				{
					lastRenderTimeNS += deltaRenderTimeNS;
				}
			}

			render();
			frameCount++;

			nowTimeNS = java.lang.System.nanoTime();
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

		//todo list.update();
		// hud.update(); // not really needed
	}

	/**
	 * Does all the rendering inside the game loop
	 */
	private void render()
	{
		renderer.initializeRenderIteration();

		//todo list.render( renderer );

		renderer.initializeGUIRenderIteration();

		//todo hud.render( renderer );

		renderer.finishRenderIteration();
	}
}
