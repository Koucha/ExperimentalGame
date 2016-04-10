package com.koucha.experimentalgame.entitySystem.component;

import com.koucha.experimentalgame.entitySystem.Component;
import com.koucha.experimentalgame.entitySystem.ComponentFlag;

// TODO: 10.04.2016 comment
public class Input implements Component
{
	public int bitmask = 0;

	@Override
	public ComponentFlag getFlag()
	{
		return ComponentFlag.Input;
	}
}
