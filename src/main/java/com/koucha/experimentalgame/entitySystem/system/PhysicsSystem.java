package com.koucha.experimentalgame.entitySystem.system;


import com.koucha.experimentalgame.entitySystem.ComponentFlag;
import com.koucha.experimentalgame.entitySystem.Entity;
import com.koucha.experimentalgame.entitySystem.QuadTree;
import com.koucha.experimentalgame.entitySystem.SystemFlag;
import com.koucha.experimentalgame.entitySystem.component.Position;
import com.koucha.experimentalgame.entitySystem.component.Velocity;

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
	protected void processEntities()
	{
		// TODO: 09.04.2016 make better maybe?
		for( Entity entity : entityList )
		{
			Velocity velocity = (Velocity) entity.get( ComponentFlag.Velocity );
			Position position = (Position) entity.get( ComponentFlag.Position );

			position.position.add( velocity.velocity );
		}
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
