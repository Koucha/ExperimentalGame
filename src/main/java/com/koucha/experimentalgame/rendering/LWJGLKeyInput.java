package com.koucha.experimentalgame.rendering;

import com.koucha.experimentalgame.input.InputBridge;
import com.koucha.experimentalgame.input.InputEvent;
import com.koucha.experimentalgame.input.KeyEventType;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Key listener that forwards key events to an {@link InputBridge}
 */
public class LWJGLKeyInput extends KeyAdapter
{
	private InputBridge inputBridge;

	/**
	 * Constructor
	 *
	 * @param inputBridge {@link InputBridge} the key input should be forwarded to
	 */
	public LWJGLKeyInput()
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
