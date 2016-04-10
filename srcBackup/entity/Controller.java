package com.koucha.experimentalgame.entitySystem.system;

import com.koucha.experimentalgame.Action;
import com.koucha.experimentalgame.GameObjectList;
import com.koucha.experimentalgame.Updatable;
import com.koucha.experimentalgame.entitySystem.component.Component;
import com.koucha.experimentalgame.entitySystem.component.Entity;

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