package com.company.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter
{
	private InputBridge inputBridge;

	public KeyInput( InputBridge inputBridge )
	{
		this.inputBridge = inputBridge;
	}

	@Override
	public void keyTyped( KeyEvent e )
	{
		// not used
	}

	@Override
	public void keyPressed( KeyEvent e )
	{
		final int code = e.getKeyCode();

		switch( code )
		{
			case KeyEvent.VK_ESCAPE:
				System.exit( 0 );
		}

		inputBridge.doKeyEvent( new InputEvent( code, KeyEvent.getKeyText( code ), KeyEventType.pressed ) );
	}

	@Override
	public void keyReleased( KeyEvent e )
	{
		final int code = e.getKeyCode();

		/* not (yet) used
		switch( code )
		{

		}
		*/

		inputBridge.doKeyEvent( new InputEvent( code, KeyEvent.getKeyText( code ), KeyEventType.released ) );
	}
}
