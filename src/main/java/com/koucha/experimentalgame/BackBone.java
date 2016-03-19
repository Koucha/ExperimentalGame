package com.koucha.experimentalgame;

import com.koucha.experimentalgame.input.InputBridge;
import com.koucha.experimentalgame.rendering.LWJGLRenderer;
import com.koucha.experimentalgame.rendering.Renderer;

/**
 * Main class
 * <p>
 * backbone of the game, contains the game loop
 */
public class BackBone implements Runnable
{
	public static final int INITIAL_WIDTH = 1200, INITIAL_HEIGHT = INITIAL_WIDTH * 9 / 12;

	public static final int INITIAL_UPDATES_PER_SECOND = 120;

	private Thread thread;
	private boolean running = false;
	private boolean inMenu = false;

	private Renderer renderer;
	private GameObjectList list;
	private HUD hud;
	private InputBridge inputBridge;
	private long limitedNanosecondsPerFrame = 1000000000 / 60;
	private boolean limitFPS = false;
	private int framesPerSecond;

	public BackBone()
	{
		renderer = new LWJGLRenderer();

		renderer.createWindow( INITIAL_WIDTH, INITIAL_HEIGHT, "Tha Game" );

		inputBridge = new InputBridge();

		list = new GameObjectList();

		PlayerController pc = new PlayerController( inputBridge.getInputMap() );
		Character player = new Character( pc );
		list.add( player );

		hud = new HUD( player );

		renderer.setInputBridge( inputBridge );

		start();
	}

	/**
	 * Starts the thread with the game loop
	 */
	public synchronized void start()
	{
		thread = new Thread( this );
		thread.start();
		running = true;
	}

	public static void main( String[] args )
	{
		new BackBone();
	}

	@Override
	public void run()
	{
		long lastTimeNS = System.nanoTime();
		double nanosecondsPerUpdate = 1000000000 / INITIAL_UPDATES_PER_SECOND;

		/**
		 * This many updates should be done in the next iteration
		 *
		 * Is floating point! If it's increased by 0.1 in each iteration, update will only be called every 10th iteration.
		 * If dependent on the duration of the Iteration, updates will be done with a fixed frequency
		 */
		double updatesToBeDone = 0;

		long timerNS = lastTimeNS;
		long fpsTimerNS = lastTimeNS;
		long nowTimeNS;

		while( running )
		{
			nowTimeNS = System.nanoTime();
			updatesToBeDone += (nowTimeNS - lastTimeNS) / nanosecondsPerUpdate;        // ex.: 1.4 = 0.5 + 0.9 (0.5: remainder from last iteration, 0.9: from this iteration)
			lastTimeNS = nowTimeNS;

			// update
			while( updatesToBeDone >= 1 )
			{
				update();
				updatesToBeDone--;        // ex.: 0.4 = 1.4--  (0.4: remainder from this iteration)
			}

			// if FPS are limited to a certain value, slow the render loop to match
			while( limitFPS && running && nowTimeNS - fpsTimerNS < limitedNanosecondsPerFrame )
			{
				Thread.yield();
				nowTimeNS = System.nanoTime();
			}

			// render
			if( running )
			{
				fpsTimerNS += limitedNanosecondsPerFrame;
				render();
				framesPerSecond++;
			}

			if( nowTimeNS - timerNS > 1000000000 )
			{
				// todo DEBUG
				System.out.println( "FPS: " + framesPerSecond );

				timerNS += 1000000000;
				framesPerSecond = 0;
			}
		}

		stop();
	}

	/**
	 * Does all the calculations inside the game loop
	 */
	private void update()
	{
		renderer.initializeUpdateIteration();

		list.update();
		// hud.update(); // not really needed
	}

	/**
	 * Does all the rendering inside the game loop
	 */
	private void render()
	{
		renderer.initializeRenderIteration();

		list.render( renderer );

		hud.render( renderer );

		renderer.finishRenderIteration();
	}

	/**
	 * Stops the game loop thread (or tries to?)
	 */
	public synchronized void stop()
	{
		try
		{
			thread.join();
			running = false;
		} catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return true if the game is currently inside the menu
	 */
	public boolean isInMenu()
	{
		return inMenu;
	}
}
