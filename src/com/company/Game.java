package com.company;

import com.company.input.InputBridge;
import com.company.input.KeyInput;
import com.company.input.MouseInput;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Main class
 * <p>
 * backbone of the game, contains the game loop
 */
public class Game extends Canvas implements Runnable
{
	public static final int WIDTH = 1200, HEIGHT = WIDTH * 9 / 12;

	private Thread thread;
	private boolean running = false;
	private boolean inMenu = false;

	private GameObjectList list;
	private HUD hud;
	private InputBridge inputBridge;

	public Game()
	{
		createWindow( WIDTH, HEIGHT, "Tha Game" );

		inputBridge = new InputBridge();

		list = new GameObjectList();

		PlayerController pc = new PlayerController( inputBridge.getInputMap() );
		Character player = new Character( pc );
		list.add( player );

		hud = new HUD( player );

		addKeyListener( new KeyInput( inputBridge ) );
		addMouseListener( new MouseInput( inputBridge ) );

		start();
	}

	/**
	 * Opens a Window with Game as its canvas
	 *
	 * @param width  width of the window content
	 * @param height height of the window content
	 * @param title  title of the window
	 */
	public void createWindow( int width, int height, String title )
	{
		JFrame frame = new JFrame( title );
		frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );

		frame.setResizable( false );
		frame.getContentPane().setPreferredSize( new Dimension( width, height ) );
		frame.pack();

		frame.setLocationRelativeTo( null );
		frame.add( this );
		frame.setVisible( true );
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
		new Game();
	}

	/**
	 * Clamps a variable to an interval
	 *
	 * @param var variable to be clamped
	 * @param min lower interval border
	 * @param max upper interval border
	 * @return the clamped value of var
	 */
	@SuppressWarnings( "Duplicates" )
	public static int clampInt( int var, int min, int max )
	{
		if( var < min )
		{
			return min;
		} else if( var > max )
		{
			return max;
		} else
		{
			return var;
		}
	}

	/**
	 * Clamps a variable to an interval
	 *
	 * @param var variable to be clamped
	 * @param min lower interval border
	 * @param max upper interval border
	 * @return the clamped value of var
	 */
	@SuppressWarnings( "Duplicates" )
	public static float clamp( float var, float min, float max )
	{
		if( var < min )
		{
			return min;
		} else if( var > max )
		{
			return max;
		} else
		{
			return var;
		}
	}

	@Override
	public void run()
	{
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000 / amountOfTicks;
		double delta = 0;
		long timer = lastTime;
		int frames = 0;
		long now;

		while( running )
		{
			now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while( delta >= 1 )
			{
				tick();
				delta--;
			}
			if( running )
				render();
			frames++;

			if( now - timer > 1000000000 )
			{
				timer += 1000000000;
				// DEBUG
				System.out.println( "FPS: " + frames );
				frames = 0;
			}
		}

		stop();
	}

	/**
	 * Does all the calculations inside the game loop
	 */
	private void tick()
	{
		list.tick();
		// hud.tick(); // not really needed
	}

	/**
	 * Does all the rendering inside the game loop
	 */
	private void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if( bs == null )
		{
			this.createBufferStrategy( 3 );
			return;
		}

		Graphics g = bs.getDrawGraphics();
		((Graphics2D) g).setRenderingHints( new RenderingHints( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON ) );

		g.setColor( Color.BLACK );
		g.fillRect( 0, 0, WIDTH, HEIGHT );

		list.render( g );

		hud.render( g );

		g.dispose();
		bs.show();
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
