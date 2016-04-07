package com.koucha.experimentalgame.entitySystem;

import java.util.EnumMap;
import java.util.Map;

/**
 * Entity containing all its components
 */
public class Entity
{
	private FastBitSet componentMask = new FastBitSet();
	private Map< ComponentFlag, Component > componentMap = new EnumMap<>( ComponentFlag.class );
	private EntityManager manager;

	/**
	 * Add a Component to the Entity and update the Entity getMask
	 *
	 * @param component Component to add
	 * @return this Entity (for chaining)
	 */
	public Entity add( Component component )
	{
		componentMap.put( component.getFlag(), component );
		componentMask.add( component.getMask() );

		if( manager != null )
			manager.updateAddition( this );

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
			componentMask.remove( flag.getMask() );

			if( manager != null )
				manager.updateDeletion( this );

			return true;
		}
		return false;
	}

	/**
	 * Set the manager of this Entity
	 * <p>
	 * Should not need to be called, because the EntityManager does this automatically when this Entity is added
	 *
	 * @param manager EntityManager this Entity should be bound to
	 *
	 * @see EntityManager#add(Entity)
	 */
	public void setManager( EntityManager manager )
	{
		this.manager = manager;
	}

	/**
	 * Get a specific Component of this Entity
	 *
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
	public boolean accept( FastBitSet mask )
	{
		return componentMask.contains( mask );
	}

	/**
	 * Test if this Entity has the desired Component
	 *
	 * @param flag ComponentFlag of the desired Component
	 * @return {@code true} if this Entity contains all desired Components, {@code false} otherwise
	 */
	public boolean accept( ComponentFlag flag )
	{
		return accept( flag.getMask() );
	}
}
