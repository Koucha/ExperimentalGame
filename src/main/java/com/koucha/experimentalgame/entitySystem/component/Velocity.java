package com.koucha.experimentalgame.entitySystem.component;

import com.koucha.experimentalgame.entitySystem.Component;
import com.koucha.experimentalgame.entitySystem.ComponentFlag;
import org.joml.Vector3f;

/**
 * Contains {@link Vector3f} of the velocity
 */
public class Velocity extends Vector3f implements Component
{
	public Vector3f velocity = new Vector3f();

	public Velocity()
	{
		//empty (like your dreams)
	}

	@Override
	public ComponentFlag getFlag()
	{
		return ComponentFlag.Velocity;
	}
}
