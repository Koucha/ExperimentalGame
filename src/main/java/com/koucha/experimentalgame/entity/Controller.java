package com.koucha.experimentalgame.entity;

import com.koucha.experimentalgame.Action;
import com.koucha.experimentalgame.GameObjectList;
import com.koucha.experimentalgame.Updatable;

/**
 * Controller provide instructions to {@link Entity} objects
 */
public interface Controller extends Component, Updatable
{
	/**
	 * @return instruction to be done by character
	 */
	Action getAction();

	@Override
	default void setHandler( GameObjectList handler )
	{
	}
}
