package com.koucha.experimentalgame.entitySystem.component;


import com.koucha.experimentalgame.entitySystem.Component;
import com.koucha.experimentalgame.entitySystem.ComponentFlag;
import org.joml.Vector3f;

// TODO: 10.04.2016 comment
public class AABB implements Component
{
	public Vector3f min = new Vector3f( 0, 0, 0 );
	public Vector3f max = new Vector3f( 1, 1, 1 );

	@Override
	public ComponentFlag getFlag()
	{
		return ComponentFlag.AABB;
	}
}