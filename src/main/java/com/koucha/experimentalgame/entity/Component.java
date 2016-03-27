package com.koucha.experimentalgame.entity;

import com.koucha.experimentalgame.GameObjectList;

/**
 * Component that can be added to an Entity
 */
public interface Component
{
	/**
	 * Sets the handler of the Component if the Component needs one
	 *
	 * @param handler the Component will add generated {@link com.koucha.experimentalgame.GameObject} to this handler
	 */
	void setHandler( GameObjectList handler );
}
