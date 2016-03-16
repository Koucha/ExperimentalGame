package com.company.effects;

import com.company.GameObject;
import com.company.GameObjectList;

public abstract class Effect implements GameObject
{
	private GameObjectList handler;

	@Override
	public void setHandler( GameObjectList list )
	{
		handler = list;
	}

	protected void endEffect()
	{
		handler.remove( this );
	}
}
