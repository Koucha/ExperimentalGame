package com.koucha.experimentalgame.entitySystem.component;

import com.koucha.experimentalgame.entitySystem.Component;
import com.koucha.experimentalgame.entitySystem.ComponentFlag;
import org.joml.Vector3f;

/**
 * Contains {@link Vector3f} of the position
 */
public class Position implements Component
{
	public Vector3f position = new Vector3f();

	public Position()
	{
		//empty (like your life)
	}

	@Override
	public ComponentFlag getFlag()
	{
		return ComponentFlag.Position;
	}
}
