package com.koucha.experimentalgame.entitySystem.system;


import com.koucha.experimentalgame.entitySystem.ComponentFlag;
import com.koucha.experimentalgame.entitySystem.Entity;
import com.koucha.experimentalgame.entitySystem.QuadTree;
import com.koucha.experimentalgame.entitySystem.SystemFlag;
import com.koucha.experimentalgame.entitySystem.component.Mesh;
import com.koucha.experimentalgame.entitySystem.component.Position;
import com.koucha.experimentalgame.rendering.Renderer;

// TODO: 10.04.2016 comment
public class RenderSystem extends AbstractSystem
{

	private final Renderer renderer;

	public RenderSystem( QuadTree entityList, Renderer renderer )
	{
		super( entityList );
		entityList.addAcceptor( this::acceptEntity );

		this.renderer = renderer;
	}

	@Override
	public SystemFlag getFlag()
	{
		return SystemFlag.RenderSystem;
	}

	@Override
	public void processEntities()
	{
		updateEntityList();

		// TODO: 09.04.2016 make better maybe?
		for( Entity entity : entityList )
		{
			Mesh mesh = (Mesh) entity.get( ComponentFlag.Mesh );
			mesh.position = (Position) entity.get( ComponentFlag.Position );
			renderer.render( mesh );
		}
	}

	@Override
	protected boolean acceptEntity( Entity entity )
	{
		return (entity.accept( ComponentFlag.AABB ) &&
				entity.accept( ComponentFlag.Mesh ) &&
				entity.accept( ComponentFlag.Position ));
	}

	@Override
	protected boolean rejectEntity( Entity entity )
	{
		return ((QuadTree) entityList).rejectEntity( entity );
	}
}
