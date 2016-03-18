package com.koucha.experimentalgame.input;

/**
 * Takes Input through {@link #doMouseMovement(int, int)} and {@link #doKeyEvent(InputEvent)} and
 * forwards it according to the {@link InputMap}
 */
public class InputBridge
{
	private int mouseX;
	private int mouseY;

	/**
	 * This {@link InputMap} maps callbacks to key codes and is needed for dynamic forwarding of InputEvents
	 */
	private InputMap inputMap;

	public InputBridge()
	{
		this.inputMap = new InputMap();
		mouseX = 0;
		mouseY = 0;
	}

	/**
	 * Forwards the {@link InputEvent} to the destination linked in {@link #inputMap}
	 * <p>
	 * If nothing is linked, this method will return without doing anything
	 *
	 * @param evt the Event that should be forwarded
	 */
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

	/**
	 * Saves the mouse position
	 * (can be retrieved via {@link #getMouseX()} and {@link #getMouseY()})
	 *
	 * @param posX x coordinate of the mouse position
	 * @param posY y coordinate of the mouse position
	 */
	public void doMouseMovement( int posX, int posY )
	{
		mouseX = posX;
		mouseY = posY;
	}

	/**
	 * @return x coordinate of the mouse position
	 */
	public int getMouseX()
	{
		return mouseX;
	}

	/**
	 * @return y coordinate of the mouse position
	 */
	public int getMouseY()
	{
		return mouseY;
	}

	/**
	 * Gives full access to {@link #inputMap}
	 * <p>
	 * This map of Key codes and callbacks is used for forwarding InputEvents
	 *
	 * @return {@link #inputMap}
	 */
	public InputMap getInputMap()
	{
		return inputMap;
	}
}
