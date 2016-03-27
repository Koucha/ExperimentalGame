package com.koucha.experimentalgame;

import com.koucha.experimentalgame.rendering.Renderable;

/**
 * GameObjects can be added to a {@link GameObjectList} and will then be processed once per iteration of the game loop
 */
public interface GameObject extends Updatable, Renderable
{
	/**
	 * Sets the Handler of the GameObject. Usually that's the list containing it.
	 *
	 * @param list {@link GameObjectList} containing this GameObject
	 */
	void setHandler( GameObjectList list );
}
