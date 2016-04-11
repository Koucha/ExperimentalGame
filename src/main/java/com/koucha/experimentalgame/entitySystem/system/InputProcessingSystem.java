package com.koucha.experimentalgame.entitySystem.system;

import com.koucha.experimentalgame.entitySystem.ComponentFlag;
import com.koucha.experimentalgame.entitySystem.Entity;
import com.koucha.experimentalgame.entitySystem.SystemFlag;
import com.koucha.experimentalgame.entitySystem.component.Input;
import com.koucha.experimentalgame.entitySystem.component.Velocity;

// TODO: 10.04.2016 comment
public class InputProcessingSystem extends AbstractSystem
{
	public InputProcessingSystem()
	{
		super( new LinkedEntityList<>( new InputElement() ) );
	}

	@Override
	public SystemFlag getFlag()
	{
		return SystemFlag.InputProcessingSystem;
	}

	@Override
	protected void processEntities()
	{
		InputElement element;
		for( EntityList.Element el : entityList )
		{
			element = (InputElement) el;

			calculateVelocity( element.input, element.velocity );
		}
	}

	private void calculateVelocity( Input input, Velocity velocity )
	{
		velocity.velocity.set( 0, 0, 0 );

		if( InputSubSystem.check( input, InputSubSystem.Selector.up ) )
		{
			velocity.velocity.z = -1;
		} else if( InputSubSystem.check( input, InputSubSystem.Selector.down ) )
		{
			velocity.velocity.z = 1;
		}
		if( InputSubSystem.check( input, InputSubSystem.Selector.right ) )
		{
			velocity.velocity.x = 1;
		} else if( InputSubSystem.check( input, InputSubSystem.Selector.left ) )
		{
			velocity.velocity.x = -1;
		}

		if( velocity.velocity.x != 0 || velocity.velocity.z != 0 )
		{
			velocity.velocity.normalize().mul( 0.001f );
		}

	}

	@Override
	protected boolean acceptEntity( Entity entity )
	{
		return entity.accept( ComponentFlag.Input ) && entity.accept( ComponentFlag.Velocity );
	}

	private static class InputElement extends LinkedEntityList.LinkElement
	{
		Velocity velocity;
		Input input;

		private InputElement()
		{
		}

		private InputElement( Entity entity )
		{
			super( entity );
			velocity = (Velocity) entity.get( ComponentFlag.Velocity );
			input = (Input) entity.get( ComponentFlag.Input );
		}

		@Override
		public EntityList.Element makeFrom( Entity entity )
		{
			return new InputElement( entity );
		}
	}
}
