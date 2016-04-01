package com.koucha.experimentalgame.entitySystem.system;


import com.koucha.experimentalgame.entitySystem.*;
import com.koucha.experimentalgame.entitySystem.System;

import java.util.LinkedList;
import java.util.List;

/**
 * Basic implementation of a System.
 * Handles the per System Entity list and the binding to an {@link EntityManager}
 * <p>
 * Further implementations have to implement:
 * <ul>
 *    <li>{@link #acceptEntity(Entity)}
 *    <ul style="list-style: none;">
 *        <li>to specify what entities should be added to the list</li>
 *    </ul></li>
 *    <li>{@link #getFlag()}
 *    <ul style="list-style: none;">
 *        <li>to provide a valid SystemFlag (only non abstract implementations should have one)</li>
 *    </ul></li>
 * </ul>
 *
 * @see Entity
 * @see System
 */
public abstract class AbstractSystem implements System
{
	/**
	 * Manager this System is linked to
	 */
	protected EntityManager manager;

	/**
	 * List of all entities this System should be conserned with
	 */
	protected List< Entity > entityList = new LinkedList<>();

	@Override
	public void setEntityManager( EntityManager manager )
	{
		this.manager = manager;

		if( !manager.isSystemLinked( this ) )
			manager.link( this );
	}

	/**
	 * Apply changes from the EntityManager to this Systems Entity list
	 *
	 * @see #processEntityChange(Entity, ChangeType)
	 * @see EntityManager#checkChangedEntities(SystemFlag, ChangeProcessor)
	 * @see ChangeProcessor
	 */
	public void updateEntityList()
	{
		manager.checkChangedEntities( getFlag(), this::processEntityChange );
	}

	/**
	 * Add/Remove entities to/from this Systems list, if they fullfill the requirements
	 *
	 * @param entity Entity to be procesed
	 * @param type type of the change of the given Entity
	 *
	 * @see ChangeProcessor
	 */
	private void processEntityChange( Entity entity, ChangeType type )
	{
		switch( type )
		{
			case Add:
			case Addition:
				if( acceptEntity( entity ) && entityList.indexOf( entity ) == -1 )
					entityList.add( entity );
				break;
			case Deletion:
				if( !acceptEntity( entity ) )
					entityList.remove( entity );
				break;
			case Remove:
				entityList.remove( entity );
		}
	}

	/**
	 * Test if that Entity is fit to be processed by this System
	 *
	 * @param entity Entity to test
	 * @return {@code true} if the Entity can be processed, {@code false} otherwise
	 */
	protected abstract boolean acceptEntity( Entity entity );
}
