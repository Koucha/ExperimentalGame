package com.koucha.experimentalgame.entity;

import org.joml.Vector3f;

/**
 * Velocity Vector. Has the same methods as {@link Vector3f}
 */
public class Velocity extends Vector3f implements Component
{
	public Velocity()
	{
		super();
	}

	public Velocity( float x, float y, float z )
	{
		super( x, y, z );
	}

	public Velocity( Vector3f pos )
	{
		super( pos );
	}

	@Override
	public ComponentFlag getFlag()
	{
		return ComponentFlag.Velocity;
	}
}
