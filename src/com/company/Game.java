package com.company;

import com.company.input.InputBridge;
import com.company.input.KeyInput;
import com.company.input.MouseInput;

import java.awt.*;
import java.awt.image.BufferStrategy;

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
		new Window( WIDTH, HEIGHT, "Tha Game", this );

		inputBridge = new InputBridge();

		list = new GameObjectList();

		PlayerController pc = new PlayerController( inputBridge.getInputMap() );
		Character player = new Character( pc );
		list.add( player );

		hud = new HUD( player );

		inputBridge.getInputMap().initKeyList();

		addKeyListener( new KeyInput( inputBridge ) );
		addMouseListener( new MouseInput( inputBridge ) );

		start();
	}

	public static void main( String[] args )
	{
		new Game();
	}

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

	public synchronized void start()
	{
		thread = new Thread( this );
		thread.start();
		running = true;
	}

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

	private void tick()
	{
		list.tick();
		// hud.tick(); // not really needed
	}

	public boolean isInMenu()
	{
		return inMenu;
	}
}
