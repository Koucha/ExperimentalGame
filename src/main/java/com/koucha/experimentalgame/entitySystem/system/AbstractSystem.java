package com.koucha.experimentalgame.entitySystem.system;


import com.koucha.experimentalgame.entitySystem.ChangeType;
import com.koucha.experimentalgame.entitySystem.Entity;
import com.koucha.experimentalgame.entitySystem.EntityManager;
import com.koucha.experimentalgame.entitySystem.System;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractSystem implements System
{
	protected EntityManager manager;
	protected List< Entity > entityList = new LinkedList<>();

	@Override
	public void setManager( EntityManager manager )
	{
		this.manager = manager;

		if( !manager.isSystemLinked( this ) )
			manager.link( this );
	}

	public void updateEntityList()
	{
		manager.checkChangedEntities( getFlag(), this::processEntityChange );
	}

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

	protected abstract boolean acceptEntity( Entity entity );
}
