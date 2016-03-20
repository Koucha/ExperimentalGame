package com.koucha.experimentalgame.rendering;

import com.koucha.experimentalgame.input.InputBridge;
import com.koucha.experimentalgame.input.InputEvent;
import com.koucha.experimentalgame.input.KeyEventType;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Mouse listener that forwards mouse button events and the mouse position to an {@link InputBridge}
 */
public class LWJGLMouseInput extends MouseAdapter
{
	private InputBridge inputBridge;

	public LWJGLMouseInput()
	{
		inputBridge = null;
	}

	@Override
	public void mouseClicked( MouseEvent e )
	{
		// not used
	}

	@Override
	public void mousePressed( MouseEvent e )
	{
		inputBridge.doKeyEvent( new InputEvent( parseButton( e ), parseButtonName( e ), KeyEventType.pressed ) );
	}

	@Override
	public void mouseReleased( MouseEvent e )
	{
		inputBridge.doKeyEvent( new InputEvent( parseButton( e ), parseButtonName( e ), KeyEventType.released ) );
	}

	@Override
	public void mouseMoved( MouseEvent e )
	{
		inputBridge.doMouseMovement( e.getX(), e.getY() );
	}

	/**
	 * Generates codes for the mouse buttons in harmony with the keyboard key codes
	 *
	 * @param e mouse button event
	 * @return code of the button pressed
	 */
	private int parseButton( MouseEvent e )
	{
		switch( e.getButton() )
		{
			case MouseEvent.BUTTON1:
				return -1;
			case MouseEvent.BUTTON2:
				return -2;
			case MouseEvent.BUTTON3:
				return -3;
		}
		return 0;
	}

	private String parseButtonName( MouseEvent e )
	{
		switch( e.getButton() )
		{
			case MouseEvent.BUTTON1:
				return "Mouse 1";
			case MouseEvent.BUTTON2:
				return "Mouse 2";
			case MouseEvent.BUTTON3:
				return "Mouse 3";
		}
		return "";
	}

	public void setInputBridge( InputBridge inputBridge )
	{
		this.inputBridge = inputBridge;
	}
}
