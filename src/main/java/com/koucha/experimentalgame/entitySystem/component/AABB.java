package com.koucha.experimentalgame.entitySystem.component;


import com.koucha.experimentalgame.entitySystem.Component;
import com.koucha.experimentalgame.entitySystem.ComponentFlag;
import org.joml.Vector3f;


public class AABB implements Component
{
	public Vector3f min;
	public Vector3f max;

	@Override
	public ComponentFlag getFlag()
	{
		return ComponentFlag.AABB;
	}
}
