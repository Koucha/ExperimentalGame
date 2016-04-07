package com.koucha.experimentalgame.entitySystem.system;


import com.koucha.experimentalgame.entitySystem.ComponentFlag;
import com.koucha.experimentalgame.entitySystem.Entity;
import com.koucha.experimentalgame.entitySystem.QuadTree;
import com.koucha.experimentalgame.entitySystem.SystemFlag;


public class RenderSystem extends AbstractSystem
{

	@Override
	public SystemFlag getFlag()
	{
		return SystemFlag.RenderSystem;
	}

	public RenderSystem( QuadTree entityList )
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
				 entity.accept( ComponentFlag.Mesh ) &&
				 entity.accept( ComponentFlag.Position ) );
	}
}
