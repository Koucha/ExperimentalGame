package com.koucha.experimentalgame.entitySystem.system;


import com.koucha.experimentalgame.entitySystem.ComponentFlag;
import com.koucha.experimentalgame.entitySystem.Entity;
import com.koucha.experimentalgame.entitySystem.QuadTree;
import com.koucha.experimentalgame.entitySystem.SystemFlag;

// TODO: 10.04.2016 comment
public class PhysicsSystem extends AbstractSystem
{
	public PhysicsSystem( QuadTree entityList )
	{
		super( entityList );
		entityList.addAcceptor( this::acceptEntity );
	}

	@Override
	public SystemFlag getFlag()
	{
		return SystemFlag.PhysicsSystem;
	}

	@Override
	public void processEntities()
	{
		//todo
	}

	@Override
	protected boolean acceptEntity( Entity entity )
	{
		return (entity.accept( ComponentFlag.AABB ) &&
				entity.accept( ComponentFlag.Position ) &&
				entity.accept( ComponentFlag.Velocity ));
	}

	@Override
	protected boolean rejectEntity( Entity entity )
	{
		return ((QuadTree) entityList).rejectEntity( entity );
	}
}
