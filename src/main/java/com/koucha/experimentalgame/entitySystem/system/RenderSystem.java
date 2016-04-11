package com.koucha.experimentalgame.entitySystem.system;


import com.koucha.experimentalgame.entitySystem.ComponentFlag;
import com.koucha.experimentalgame.entitySystem.Entity;
import com.koucha.experimentalgame.entitySystem.SystemFlag;
import com.koucha.experimentalgame.entitySystem.component.AABB;
import com.koucha.experimentalgame.entitySystem.component.Mesh;
import com.koucha.experimentalgame.entitySystem.component.Position;
import com.koucha.experimentalgame.rendering.Renderer;

// TODO: 10.04.2016 comment
public class RenderSystem extends AbstractSystem
{

	private final Renderer renderer;

	public RenderSystem( Renderer renderer )
	{
		super( new LinkedEntityList<>( new RenderElement() ) );

		this.renderer = renderer;
	}

	@Override
	public SystemFlag getFlag()
	{
		return SystemFlag.RenderSystem;
	}

	public void setAlpha( float alpha )
	{
		renderer.setAlpha( alpha );
	}

	@Override
	protected void processEntities()
	{
		RenderElement element;
		// TODO: 09.04.2016 make better maybe?
		for( EntityList.Element el : entityList )
		{
			element = (RenderElement) el;
			element.mesh.position = element.position;
			renderer.render( element.mesh );
		}
	}

	@Override
	protected boolean acceptEntity( Entity entity )
	{
		return (entity.accept( ComponentFlag.AABB ) &&
				entity.accept( ComponentFlag.Mesh ) &&
				entity.accept( ComponentFlag.Position ));
	}

	private static class RenderElement extends LinkedEntityList.LinkElement
	{
		Position position;
		AABB aabb;
		Mesh mesh;

		private RenderElement()
		{
		}

		private RenderElement( Entity entity )
		{
			super( entity );
			mesh = (Mesh) entity.get( ComponentFlag.Mesh );
			position = (Position) entity.get( ComponentFlag.Position );
			aabb = (AABB) entity.get( ComponentFlag.AABB );
		}

		@Override
		public EntityList.Element makeFrom( Entity entity )
		{
			return new RenderElement( entity );
		}
	}
}
