package com.koucha.experimentalgame.entitySystem.component;


import com.koucha.experimentalgame.entitySystem.Component;
import com.koucha.experimentalgame.entitySystem.ComponentFlag;

// TODO: 10.04.2016 comment
public class Mesh implements Component
{
	// TODO: 10.04.2016 maybe not here but in wrapper class?
	public Position position;

	@Override
	public ComponentFlag getFlag()
	{
		return ComponentFlag.Mesh;
	}
}
