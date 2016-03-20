package com.koucha.experimentalgame.rendering.lwjgl;

import com.koucha.experimentalgame.input.InputBridge;
import com.koucha.experimentalgame.input.InputEvent;
import com.koucha.experimentalgame.input.KeyEventType;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Key listener that forwards key events to an {@link InputBridge}
 */
class KeyInput extends KeyAdapter
{
	private InputBridge inputBridge;

	public KeyInput()
	{
		inputBridge = null;
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

	public void setInputBridge( InputBridge inputBridge )
	{
		this.inputBridge = inputBridge;
	}
}
