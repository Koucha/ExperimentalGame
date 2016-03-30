package com.koucha.experimentalgame;

import com.koucha.experimentalgame.rendering.Renderer;

import java.awt.*;
import java.util.LinkedList;

/**
 * List containing {@link GameObject} objects
 */
public class GameObjectList
{
	private LinkedList< GameObject > list = new LinkedList<>();
	private LinkedList< GameObject > addList = new LinkedList<>();
	private LinkedList< GameObject > removeList = new LinkedList<>();

	private int locked = 0;

	/**
	 * Forwards the update to every {@link GameObject} in the list
	 */
	public void update()
	{
		locked++;
		list.forEach( GameObject::update );
		locked--;
		assert (locked >= 0);
		clean();
	}

	/**
	 * Applies the operation stored in the temp lists on the list
	 */
	private void clean()
	{
		while( addList.size() > 0 )
		{
			list.add( addList.poll() );
		}

		while( removeList.size() > 0 )
		{
			list.remove( removeList.poll() );
		}
	}

	/**
	 * Forwards the render instruction to every {@link GameObject} in the list
	 *
	 * @param renderer {@link Graphics} object to be painted on
	 */
	public void render( Renderer renderer )
	{
		locked++;
		for( GameObject o : list )
		{
			o.render( renderer );
		}
		locked--;
		assert (locked >= 0);
		clean();
	}

	/**
	 * Adds a {@link GameObject} to the List
	 *
	 * @param o GameObject to be added
	 */
	public void add( GameObject o )
	{
		if( locked <= 0 )
			list.add( o );
		else
			addList.add( o );

		o.setHandler( this );
	}

	/**
	 * Removes a {@link GameObject} from the List
	 *
	 * @param o GameObject to be removed
	 */
	public void remove( GameObject o )
	{
		if( locked <= 0 )
			list.remove( o );
		else
			removeList.add( o );
	}

	/**
	 * Empties the list
	 */
	public void clear()
	{
		list.clear();
	}
}
