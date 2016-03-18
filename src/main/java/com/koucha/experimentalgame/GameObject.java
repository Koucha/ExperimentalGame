package com.koucha.experimentalgame;

import java.awt.*;

/**
 * GameObjects can be added to a {@link GameObjectList} and will then be processed once per iteration of the game loop
 */
public interface GameObject
{
	/**
	 * Is called with a set frequency from the game loop
	 */
	void update();

	/**
	 * Is called whenever the Screen content is calculated anew
	 * <p>
	 * The rendering of the GameObject happens in this method
	 *
	 * @param g {@link Graphics} object to be painted on
	 */
	void render( Graphics g );

	/**
	 * Sets the Handler of the GameObject. Usually that's the list containing it.
	 *
	 * @param list {@link GameObjectList} containing this GameObject
	 */
	void setHandler( GameObjectList list );
}
