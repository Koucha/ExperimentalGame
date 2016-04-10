package com.koucha.experimentalgame.entitySystem;


import com.koucha.experimentalgame.entitySystem.system.RenderSystem;

import java.util.ArrayList;
import java.util.List;

// TODO: 10.04.2016 comment
public class SystemManager
{
	private List< System > systemList;
	private RenderSystem renderSystem;
	private EntityManager entityManager;

	public SystemManager( EntityManager entityManager )
	{
		this( entityManager, 10 );
	}

	public SystemManager( EntityManager entityManager, int i )
	{
		this.entityManager = entityManager;
		this.systemList = new ArrayList<>( i );
	}

	public void setRenderSystem( RenderSystem renderSystem )
	{
		renderSystem.setEntityManager( entityManager );
		this.renderSystem = renderSystem;
	}

	public void add( System system )
	{
		system.setEntityManager( entityManager );
		systemList.add( system );
	}

	public void update()
	{
		//noinspection Convert2streamapi
		for( System sys : systemList )
		{
			sys.processEntities();
		}
	}

	public void render()
	{
		renderSystem.processEntities();
	}
}
