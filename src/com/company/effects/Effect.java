package com.company.effects;

import com.company.GameObject;
import com.company.GameObjectList;

/**
 * Represents a visual effect, that is to be added to a {@link GameObjectList}
 * <p>
 * They should remove themselves from this list via {@link #endEffect()} method after they expire
 */
public abstract class Effect implements GameObject
{
	private GameObjectList handler;

	@Override
	public void setHandler( GameObjectList list )
	{
		handler = list;
	}

	/**
	 * Removes the Effect from the List
	 * <p>
	 * Should be called by subclasses as soon as their visual effect is finished
	 */
	protected void endEffect()
	{
		handler.remove( this );
	}
}
