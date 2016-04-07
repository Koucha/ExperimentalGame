package com.koucha.experimentalgame.entitySystem.system;


import com.koucha.experimentalgame.entitySystem.ComponentFlag;
import com.koucha.experimentalgame.entitySystem.Entity;
import com.koucha.experimentalgame.entitySystem.QuadTree;
import com.koucha.experimentalgame.entitySystem.SystemFlag;


public class PhysicsSystem extends AbstractSystem
{
	@Override
	public SystemFlag getFlag()
	{
		return SystemFlag.PhysicsSystem;
	}

	public PhysicsSystem( QuadTree entityList )
	{
		super( entityList );
		entityList.addAcceptor( this::acceptEntity );
	}

	@Override
	protected boolean rejectEntity( Entity entity )
	{
		return ((QuadTree)entityList).rejectEntity( entity );
	}

	@Override
	protected boolean acceptEntity( Entity entity )
	{
		return ( entity.accept( ComponentFlag.AABB ) &&
				 entity.accept( ComponentFlag.Position ) &&
				 entity.accept( ComponentFlag.Velocity ) );
	}
}
