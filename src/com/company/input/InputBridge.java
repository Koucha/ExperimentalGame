package com.company.input;

public class InputBridge
{
	private int mouseX;
	private int mouseY;
	private InputMap inputMap;

	public InputBridge()
	{
		this.inputMap = new InputMap();
		mouseX = 0;
		mouseY = 0;
	}

	public void doKeyEvent( InputEvent evt )
	{
		try
		{
			inputMap.call( evt );
		} catch( InputMap.NoLinkFoundException e )
		{
			// return without doing anything, because the key is not linked to anything
		}
	}

	public void doMouseMovement( int posx, int posy )
	{
		mouseX = posx;
		mouseY = posy;
	}

	public int getMouseX()
	{
		return mouseX;
	}

	public int getMouseY()
	{
		return mouseY;
	}

	public InputMap getInputMap()
	{
		return inputMap;
	}
}
