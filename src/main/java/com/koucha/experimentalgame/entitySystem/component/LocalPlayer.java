package com.koucha.experimentalgame.entitySystem.component;

import com.koucha.experimentalgame.entitySystem.Component;
import com.koucha.experimentalgame.entitySystem.ComponentFlag;

// TODO: 10.04.2016 comment
public class LocalPlayer implements Component
{
	@Override
	public ComponentFlag getFlag()
	{
		return ComponentFlag.LocalPlayer;
	}
}
