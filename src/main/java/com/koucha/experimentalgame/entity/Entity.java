package com.koucha.experimentalgame.entity;

import java.util.EnumMap;
import java.util.Map;

/**
 * Entity containing all its components
 */
public class Entity
{
	private long componentMask = 0;
	private Map< ComponentFlag, Component > componentMap = new EnumMap<>( ComponentFlag.class );

	/**
	 * Add a Component to the Entity and update the Entity getMask
	 *
	 * @param component Component to add
	 * @return this Entity (for chaining)
	 */
	public Entity add( Component component )
	{
		componentMap.put( component.getFlag(), component );
		componentMask |= component.getMask();
		return this;
	}

	/**
	 * Remove a Component from this Entity
	 *
	 * @param flag identification of the Component
	 * @return {@code true} if the Component was removed, {@code false} if ther was no such Component
	 */
	public boolean remove( ComponentFlag flag )
	{
		if( componentMap.remove( flag ) != null )
		{
			componentMask &= ~(flag.getMask());
			return true;
		}
		return false;
	}

	/**
	 * @param flag identification of the desired Component
	 * @return the requested Component, or {@code null} if no such Component was found
	 */
	public Component get( ComponentFlag flag )
	{
		return componentMap.get( flag );
	}

	/**
	 * Test if this Entity has all desired Components
	 *
	 * @param mask a combination of the flag bits of all desired Components
	 * @return {@code true} if this Entity contains all desired Components, {@code false} otherwise
	 */
	public boolean accept( int mask )
	{
		return (mask & componentMask) == mask;
	}
}
