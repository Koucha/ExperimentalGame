package com.koucha.experimentalgame.entitySystem.component;


import com.koucha.experimentalgame.entitySystem.Component;
import com.koucha.experimentalgame.entitySystem.ComponentFlag;

public class Mesh implements Component
{
	@Override
	public ComponentFlag getFlag()
	{
		return ComponentFlag.Mesh;
	}
}
