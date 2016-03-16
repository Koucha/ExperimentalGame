package com.company.input;

import java.util.LinkedList;

public class InputMap
{
	private LinkedList< Link > linkList = new LinkedList<>();
	private Key[] keyList = new Key[20];
	private int keyListFill = 0;

	public Iterable< Link > getLinkIterable()
	{
		return () -> linkList.iterator();
	}

	public void addLink( String name, int key, Callback cb )
	{
		addLink( key, new Link( name, cb ) );
	}

	private void addLink( int key, Link link )
	{
		try
		{
			findKey( key ).addLink( link );
		} catch( NoLinkFoundException e )
		{
			addKey( new Key( key ) ).addLink( link );
		}
	}

	private Key addKey( Key key )
	{
		int pos = keyListFill;
		if( keyListFill < keyList.length )
		{
			while( pos > 0 && keyList[pos - 1].getKey() > key.getKey() )
			{
				keyList[pos - 1].putInArrayAt( pos, keyList );
				pos--;
			}

			key.putInArrayAt( pos, keyList );
		} else
		{
			Key[] newKeyList = new Key[keyList.length + 10];

			boolean inserted = false;
			for( int i = 0, j = 0; i < keyListFill; i++, j++ )
			{
				if( !inserted && keyList[i].getKey() > key.getKey() )
				{
					key.putInArrayAt( j, newKeyList );
					j++;
					inserted = true;
				}

				keyList[i].putInArrayAt( j, newKeyList );
			}
		}

		keyListFill++;

		return key;
	}

	private Key findKey( int key ) throws NoLinkFoundException
	{
		if( keyListFill < 1 )
			throw new NoLinkFoundException();

		int start = 0, middle, end = keyListFill;

		while( start < end )
		{
			middle = (start + end) / 2;

			if( keyList[middle].getKey() == key )
			{
				return keyList[middle];
			}

			if( keyList[middle].getKey() > key )
			{
				end = middle;
			} else
			{
				start = middle + 1;
			}
		}

		if( keyList[start].getKey() == key )
		{
			return keyList[start];
		}

		throw new NoLinkFoundException();
	}

	private void sortKeys()
	{
		if( keyListFill < 2 )
			return;

		bufferedMergeSort( keyList.clone(), keyList, 0, keyListFill );
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

		for( int k = start; k < end; k++ )
		{
			if( i < middle && (j >= end || source[i].getKey() < source[j].getKey()) )
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

	private void bubbleKey( Key key, boolean up )
	{
		int i = key.index;
		if( up )
		{
			while( i + 1 < keyListFill && key.getKey() > keyList[i + 1].getKey() )
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

	public void call( InputEvent evt ) throws NoLinkFoundException
	{
		findKey( evt.code ).call( evt );
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

		public Link( String name, Callback cb )
		{
			this.name = name;
			this.cb = cb;
			this.key = null;
		}

		public String getName()
		{
			return name;
		}

		public void setName( String name )
		{
			this.name = name;
		}

		public int getKey()
		{
			return key.getKey();
		}

		void setKey( Key key )
		{
			this.key = key;
		}

		public void call( InputEvent evt )
		{
			cb.call( evt );
		}
	}

	protected class Key
	{
		private int key;
		private Linker linker;
		private int index;

		public Key( int key )
		{
			this.key = key;
			this.linker = null;
		}

		public void addLink( Link link )
		{
			link.setKey( this );

			if( linker == null )
			{
				linker = new Linker( link );
			}

			Linker lk = linker;
			while( lk.next != null )
			{
				lk = lk.next;
			}
			lk.next = new Linker( link );
		}

		public int getKey()
		{
			return key;
		}

		public void setKey( int key, Link linkToBeChanged )
		{
			if( linker.next == null )
			{
				boolean up = key > this.key;
				this.key = key;
				bubbleKey( this, up );
			} else
			{
				if( linker.link == linkToBeChanged )
				{
					linker = linker.next;
				} else
				{
					Linker tempLinker = linker;
					while( tempLinker.next.link != linkToBeChanged )
					{
						tempLinker = tempLinker.next;
					}
					tempLinker.next = tempLinker.next.next;
				}

				InputMap.this.addLink( key, linkToBeChanged );
			}
		}

		public void call( InputEvent evt )
		{
			Linker lk = linker;
			while( lk != null )
			{
				lk.link.call( evt );
				lk = lk.next;
			}
		}

		public void putInArrayAt( int i, Key[] array )
		{
			array[i] = this;
			index = i;
		}

		private class Linker
		{
			public Link link;
			public Linker next;

			public Linker( Link link )
			{
				this.link = link;
				next = null;
			}
		}
	}

	public class NoLinkFoundException extends Exception
	{
	}
}
