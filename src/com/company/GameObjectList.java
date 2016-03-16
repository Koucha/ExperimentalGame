package com.company;

import java.awt.*;
import java.util.LinkedList;

public class GameObjectList
{
	LinkedList< GameObject > list = new LinkedList<>();
	LinkedList< GameObject > addlist = new LinkedList<>();
	LinkedList< GameObject > removelist = new LinkedList<>();

	private int locked = 0;

	public void tick()
	{
		locked++;
		for( GameObject o : list )
		{
			o.tick();
		}
		locked--;
		assert (locked >= 0);
		clean();
	}

	public void render( Graphics g )
	{
		locked++;
		for( GameObject o : list )
		{
			o.render( g );
		}
		locked--;
		assert (locked >= 0);
		clean();
	}

	public void add( GameObject o )
	{
		if( locked <= 0 )
			list.add( o );
		else
			addlist.add( o );

		o.setHandler( this );
	}

	public void remove( GameObject o )
	{
		if( locked <= 0 )
			list.remove( o );
		else
			removelist.add( o );
	}

	private void clean()
	{
		while( addlist.size() > 0 )
		{
			list.add( addlist.poll() );
		}

		while( removelist.size() > 0 )
		{
			list.remove( removelist.poll() );
		}
	}

	public void clear()
	{
		list.clear();
	}
}
