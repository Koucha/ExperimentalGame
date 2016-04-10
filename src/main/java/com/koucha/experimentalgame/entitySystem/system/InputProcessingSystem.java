package com.koucha.experimentalgame.entitySystem.system;

import com.koucha.experimentalgame.entitySystem.ComponentFlag;
import com.koucha.experimentalgame.entitySystem.Entity;
import com.koucha.experimentalgame.entitySystem.SystemFlag;
import com.koucha.experimentalgame.entitySystem.component.Input;
import com.koucha.experimentalgame.entitySystem.component.Velocity;

// TODO: 10.04.2016 comment
public class InputProcessingSystem extends AbstractSystem
{
	@Override
	public SystemFlag getFlag()
	{
		return SystemFlag.InputProcessingSystem;
	}

	@Override
	protected void processEntities()
	{
		for(Entity entity : entityList)
		{
			Velocity velocity = (Velocity) entity.get( ComponentFlag.Velocity );
			Input input = (Input) entity.get( ComponentFlag.Input );

			calculateVelocity(input, velocity);
		}
	}

	private void calculateVelocity( Input input, Velocity velocity )
	{
		velocity.velocity.set( 0,0,0 );

		if( InputSubSystem.check(input, InputSubSystem.Selector.up) )
		{
			velocity.velocity.z = -1;
		} else if( InputSubSystem.check(input, InputSubSystem.Selector.down) )
		{
			velocity.velocity.z = 1;
		}
		if( InputSubSystem.check(input, InputSubSystem.Selector.right) )
		{
			velocity.velocity.x = 1;
		} else if( InputSubSystem.check(input, InputSubSystem.Selector.left) )
		{
			velocity.velocity.x = -1;
		}

		if( velocity.velocity.x != 0 || velocity.velocity.z != 0)
		{
			velocity.velocity.normalize().mul( 0.001f );
		}

	}

	@Override
	protected boolean acceptEntity( Entity entity )
	{
		return entity.accept( ComponentFlag.Input ) && entity.accept( ComponentFlag.Velocity );
	}
}
