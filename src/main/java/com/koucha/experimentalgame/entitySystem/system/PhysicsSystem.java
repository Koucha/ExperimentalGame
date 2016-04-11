package com.koucha.experimentalgame.entitySystem.system;


import com.koucha.experimentalgame.entitySystem.ComponentFlag;
import com.koucha.experimentalgame.entitySystem.Entity;
import com.koucha.experimentalgame.entitySystem.SystemFlag;
import com.koucha.experimentalgame.entitySystem.component.AABB;
import com.koucha.experimentalgame.entitySystem.component.Position;
import com.koucha.experimentalgame.entitySystem.component.Velocity;

// TODO: 10.04.2016 comment
public class PhysicsSystem extends AbstractSystem
{
	public PhysicsSystem()
	{
		super( new LinkedEntityList<>( new PhysicsElement() ) );
	}

	@Override
	public SystemFlag getFlag()
	{
		return SystemFlag.PhysicsSystem;
	}

	@Override
	protected void processEntities()
	{
		PhysicsElement element;
		// TODO: 09.04.2016 make better maybe?
		for( EntityList.Element el : entityList )
		{
			element = (PhysicsElement) el;

			element.position.position.write().set( element.position.position.read() ).add( element.velocity.velocity );
		}
	}

	@Override
	protected boolean acceptEntity( Entity entity )
	{
		return (entity.accept( ComponentFlag.AABB ) &&
				entity.accept( ComponentFlag.Position ) &&
				entity.accept( ComponentFlag.Velocity ));
	}

	private static class PhysicsElement extends LinkedEntityList.LinkElement
	{
		Position position;
		Velocity velocity;
		AABB aabb;

		private PhysicsElement()
		{
		}

		private PhysicsElement( Entity entity )
		{
			super( entity );
			velocity = (Velocity) entity.get( ComponentFlag.Velocity );
			position = (Position) entity.get( ComponentFlag.Position );
			aabb = (AABB) entity.get( ComponentFlag.AABB );
		}

		@Override
		public EntityList.Element makeFrom( Entity entity )
		{
			return new PhysicsElement( entity );
		}
	}
}
