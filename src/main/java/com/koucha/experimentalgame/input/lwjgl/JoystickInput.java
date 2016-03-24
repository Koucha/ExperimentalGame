package com.koucha.experimentalgame.input.lwjgl;

import com.koucha.experimentalgame.input.InputBridge;

/**
 * Joystick listener that forwards button events and stick positions to an {@link InputBridge}
 */
public class JoystickInput
{
	private long window;
	private InputBridge inputBridge;

	public JoystickInput( long window, InputBridge inputBridge )
	{

		this.window = window;
		this.inputBridge = inputBridge;
	}

	public void pollJoystick()
	{

	}
}
