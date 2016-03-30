package com.koucha.experimentalgame.entity;

import org.joml.Vector3f;

/**
 * Position Vector. Has the same methods as {@link Vector3f}
 */
public class Position extends Vector3f implements Component
{
	public Position()
	{
		super();
	}

	public Position( float x, float y, float z )
	{
		super( x, y, z );
	}

	public Position( Vector3f pos )
	{
		super( pos );
	}

	@Override
	public ComponentFlag getFlag()
	{
		return ComponentFlag.Position;
	}
}
