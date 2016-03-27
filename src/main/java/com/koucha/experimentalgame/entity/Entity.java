package com.koucha.experimentalgame.entity;

import com.koucha.experimentalgame.GameObject;
import com.koucha.experimentalgame.GameObjectList;
import com.koucha.experimentalgame.Updatable;
import com.koucha.experimentalgame.rendering.Renderer;

import java.util.LinkedList;
import java.util.List;

/**
 * Entity containing all its components
 */
public class Entity implements GameObject
{
	private List< Component > components = new LinkedList<>();
	private List< Updatable > updatables = new LinkedList<>();

	private Mesh mesh;
	private Position position;

	private GameObjectList handler;

	public Entity( Position position, Mesh mesh )
	{
		this.position = position;
		this.mesh = mesh;
	}

	@Override
	public void update()
	{
		updatables.forEach( Updatable::update );
	}

	@Override
	public void render( Renderer renderer )
	{
		if( mesh != null )
			mesh.render( renderer );
	}

	@Override
	public void setHandler( GameObjectList list )
	{
		handler = list;
	}

	public Entity addComponent( Component component )
	{
		component.setHandler( handler );
		components.add( component );

		if( component instanceof Updatable )
			updatables.add( (Updatable) component );

		return this;
	}

	public Entity removeComponent( Component component )
	{
		components.remove( component );

		if( component instanceof Updatable )
			updatables.remove( component );

		return this;
	}

	/**
	 * get the Component that can be used as instance of the Class cls
	 *
	 * @param cls Class object of the type T
	 * @param <T> (Super-)Class/Interface the component should implement
	 * @return the desired Component, or null if none could be found
	 */
	public < T extends Component > T getComponent( Class< T > cls )
	{
		for( Component component : components )
		{
			if( cls.isInstance( component ) )
			{
				//noinspection unchecked
				return (T) component;
			}
		}
		return null;
	}

	public Mesh getMesh()
	{
		return mesh;
	}

	public Position getPosition()
	{
		return position;
	}
}
