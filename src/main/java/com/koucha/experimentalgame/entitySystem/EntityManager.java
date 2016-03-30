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
	private long registeredSystems = 0;

	private List< Change > changeList = new LinkedList<>();


	public EntityManager()
	{
	}

	public void link( System sys )
	{
		registeredSystems |= sys.getMask();
		sys.setManager( this );
	}

	public boolean isSystemLinked( System sys )
	{
		return (registeredSystems & sys.getMask()) != 0;
	}

	public void add( Entity entity )
	{
		entity.setManager( this );
		changeList.add( new Change( entity, ChangeType.Add ) );
	}

	public void remove( Entity entity )
	{
		changeList.add( new Change( entity, ChangeType.Remove ) );
	}

	public void updateAddition( Entity entity )
	{
		changeList.add( new Change( entity, ChangeType.Addition ) );
	}

	public void updateDeletion( Entity entity )
	{
		changeList.add( new Change( entity, ChangeType.Deletion ) );
	}

	public void clean()
	{
		changeList.removeIf( ( Change change ) -> registeredSystems <= change.systemMask );
	}

	public void checkChangedEntities( SystemFlag flag, ChangeProcessor processor )
	{
		long mask = flag.getMask();

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
		public ChangeType type;
		public Entity entity;
		public long systemMask;

		public Change( Entity entity, ChangeType type )
		{
			this.type = type;
			this.entity = entity;
			this.systemMask = 0;
		}

		public boolean isNew( long mask )
		{
			if( (systemMask & mask) != 0 )
				return false;

			systemMask |= mask;
			return true;
		}
	}
}
