package com.company.input;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class InputMap
{
	private LinkedList< Link > linkList = new LinkedList<>();
	private Key[] keyList;

	public Iterable< Link > getLinkIterable()
	{
		return () -> linkList.iterator();
	}

	public void addLink( String name, int key, Callback cb )
	{
		linkList.add( new Link( name, key, cb ) );
	}

	public void initKeyList()
	{
		keyList = new Key[linkList.size()];

		int i = 0;
		for( Link link : linkList )
		{
			link.key.putInArrayAt( i, keyList );
			i++;
		}

		sortKeys();
	}

	public void sortKeys()
	{
		if( keyList.length < 2 )
			return;

		bufferedMergeSort( keyList.clone(), keyList, 0, keyList.length );
	}

	private void bufferedMergeSort( Key[] source, Key[] destination, final int start, final int end )
	{
		final int middle = (start + end) / 2;

		if( end - start > 2 )
		{
			bufferedMergeSort( destination, source, start, middle );
			bufferedMergeSort( destination, source, middle, end );
		}

		int i = start, j = middle;
		// && ( i < middle || j < end )
		for( int k = start; k < end; k++ )
		{
			if( i < middle && ( j >= end || source[i].getKey() < source[j].getKey() ) )
			{
				source[i].putInArrayAt( k, destination );
				i++;
			} else
			{
				source[j].putInArrayAt( k, destination );
				j++;
			}
		}
	}

	public void bubbleKey( Key key, boolean up )
	{
		int i = key.index;
		if( up )
		{
			while( i + 1 < keyList.length && key.getKey() > keyList[i + 1].getKey() )
			{
				keyList[i + 1].putInArrayAt( i, keyList );
				i = i + 1;
			}
		} else
		{
			while( i - 1 > 0 && key.getKey() < keyList[i - 1].getKey() )
			{
				keyList[i - 1].putInArrayAt( i, keyList );
				i = i - 1;
			}
		}
		key.putInArrayAt( i, keyList );
	}

	public Link findLink( int key ) throws NoLinkFoundException
	{
		int start = 0, middle, end = keyList.length;

		while( start < end )
		{
			middle = (start + end) / 2;

			if( keyList[middle].getKey() == key )
				return keyList[middle].getLink();

			if( keyList[middle].getKey() > key )
			{
				end = middle;
			} else
			{
				start = middle + 1;
			}
		}

		if( keyList[start].getKey() == key )
			return keyList[start].getLink();

		throw new NoLinkFoundException();
	}

	public interface Callback
	{
		void call( InputEvent evt );
	}

	public class Link
	{
		private String name;
		private Callback cb;
		private Key key;

		public Link( String name, int key, Callback cb )
		{
			this.name = name;
			this.cb = cb;
			this.key = new Key( key, this );
		}

		public String getName()
		{
			return name;
		}

		public void setName( String name )
		{
			this.name = name;
		}

		public Key getKey()
		{
			return key;
		}

		public void call( InputEvent evt )
		{
			cb.call( evt );
		}
	}

	public class Key
	{
		private int key;
		private Link link;
		private int index;

		public Key( int key, Link link )
		{
			this.key = key;
			this.link = link;
		}

		public int getKey()
		{
			return key;
		}

		public void setKey( int key )
		{
			boolean up = key > this.key;
			this.key = key;
			bubbleKey( this, up );
		}

		public Link getLink()
		{
			return link;
		}

		public void putInArrayAt( int i, Key[] array )
		{
			array[i] = this;
			index = i;
		}
	}

	public class NoLinkFoundException extends Exception {}
}
