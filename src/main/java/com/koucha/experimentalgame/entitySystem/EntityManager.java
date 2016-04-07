package com.koucha.experimentalgame.entitySystem;


import java.util.LinkedList;
import java.util.List;


/**
 * Handles linking Entities to Systems
 * <p>
 * Every System has a List of Entities thats filled through this manager
 */
public class EntityManager
{
	/**
	 * Accumulation of the flags of all Systems using this manager
	 * <p>
	 * needed to determine if all Systems have processed an entity change
	 */
	private FastBitSet registeredSystems = new FastBitSet();

	/**
	 * List of the entity changes not yet processed by all Systems
	 */
	private List< Change > changeList = new LinkedList<>();

	public EntityManager()
	{
		//empty
	}

	/**
	 * Register that System with this manager and set it's manager to this
	 *
	 * @param sys System to be linked
	 */
	public void link( System sys )
	{
		registeredSystems.add( sys.getMask() );
		sys.setEntityManager( this );
	}

	public boolean isSystemLinked( System sys )
	{
		return registeredSystems.contains( sys.getMask() );
	}

	/**
	 * Adds a Entity to the list and sets the Entitys manager to this
	 *
	 * @param entity Entity to be added
	 */
	public void add( Entity entity )
	{
		entity.setManager( this );
		changeList.add( new Change( entity, ChangeType.Add ) );
	}

	/**
	 * Register that Entity to be removed from all Systems
	 *
	 * @param entity Entity to be removed
	 */
	public void remove( Entity entity )
	{
		changeList.add( new Change( entity, ChangeType.Remove ) );
	}

	/**
	 * Register, that a Component was added to that Entity
	 *
	 * @param entity changed Entity
	 */
	public void updateAddition( Entity entity )
	{
		changeList.add( new Change( entity, ChangeType.Addition ) );
	}

	/**
	 * Register, that a Component was removed to that Entity
	 *
	 * @param entity changed Entity
	 */
	public void updateDeletion( Entity entity )
	{
		changeList.add( new Change( entity, ChangeType.Deletion ) );
	}

	/**
	 * Remove fully processed changes from the change list
	 */
	public void clean()
	{
		changeList.removeIf( change -> change.systemMask.contains( registeredSystems ) );
	}

	/**
	 * Iterate over the change list.
	 * If a change wasen't processed by the specified System, process it with the given processor
	 *
	 * @param flag SystemFlag identifiyng the System
	 * @param processor is used to process the new changes
	 *
	 * @see ChangeProcessor
	 * @see com.koucha.experimentalgame.entitySystem.system.AbstractSystem#updateEntityList()
	 */
	public void checkChangedEntities( SystemFlag flag, ChangeProcessor processor )
	{
		FastBitSet mask = flag.getMask();

		//noinspection Convert2streamapi
		for( Change change : changeList )
		{
			if( change.isNew( mask ) )
			{
				processor.process( change.entity, change.type );
			}
		}
	}

	private class Change
	{
		ChangeType type;
		Entity entity;
		FastBitSet systemMask;

		Change( Entity entity, ChangeType type )
		{
			this.type = type;
			this.entity = entity;
			this.systemMask = new FastBitSet();
		}

		/**
		 * @param mask mask of the System to be tested
		 * @return false if that System already processed this change, true otherwise
		 *
		 * @see #checkChangedEntities(SystemFlag, ChangeProcessor)
		 */
		boolean isNew( FastBitSet mask )
		{
			if( systemMask.contains( mask ) )
				return false;

			systemMask.add( mask );
			return true;
		}
	}

	/**
	 * Light weight dependency injection
	 *
	 * @see #checkChangedEntities(SystemFlag, ChangeProcessor)
	 * @see com.koucha.experimentalgame.entitySystem.system.AbstractSystem#updateEntityList()
	 */
	public interface ChangeProcessor
	{
		void process( Entity entity, ChangeType type );
	}
}
