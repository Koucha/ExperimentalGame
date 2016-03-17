package com.company.input;

import java.util.LinkedList;

/**
 * Maps callbacks (implementing {@link Callback}) to key codes
 */
public class InputMap
{
	private LinkedList< Link > linkList = new LinkedList<>();
	private Key[] keyList = new Key[20];
	private int keyListFill = 0;

	/**
	 * Use to iterate over all {@link Link} objects in the map
	 * <p>
	 * Ex.:
	 * for(Link link : getLinkIterable()) { ... }
	 *
	 * @return Iterable for iterating over all Link objects
	 */
	public Iterable< Link > getLinkIterable()
	{
		return () -> linkList.iterator();
	}

	/**
	 * Ads the callback cb and the key to the map and links them
	 *
	 * @param name display name of the callback
	 * @param key  code of the key
	 * @param cb   callback object
	 */
	public void addLink( String name, int key, Callback cb )
	{
		addLink( key, new Link( name, cb ) );
	}

	/**
	 * Ads the callback that's already inside a Link object and its corresponding key to the map and links them
	 *
	 * @param key  code of the key
	 * @param link object containing the callback and its display name
	 */
	private void addLink( int key, Link link )
	{
		try
		{
			findKey( key ).addLink( link );
		} catch( NoLinkFoundException e )
		{
			addKey( new Key( key ) ).addLink( link );
		}

		linkList.add( link );
	}

	/**
	 * Searches the Key array for a key with a given key code
	 *
	 * @param key code to be searched for
	 * @return Key corresponding Key
	 * @throws NoLinkFoundException if no Key in the array has the given code
	 */
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

	/**
	 * Adds a Key to {@link #keyList} and increases the size of the array if necessary
	 *
	 * @param key Key to be added
	 * @return key, for chaining (Ex.: addKey( new Key( key ) ).addLink( link ); )
	 */
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

	/**
	 * Sorts the {@link #keyList}
	 */
	private void sortKeys()
	{
		if( keyListFill < 2 )
			return;

		bufferedMergeSort( keyList.clone(), keyList, 0, keyListFill );
	}

	/**
	 * Implementation of the mergesort algorithm on a Key array
	 *
	 * @param source      contains initial (unsorted) array data, WILL BE USED AS BUFFER!
	 * @param destination after execution contains the sorted array
	 * @param start       starting index of the range of the array to be sorted
	 * @param end         end point of the range of the array to be sorted
	 */
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

	/**
	 * Adjusts the position of the Key inside the sorted array if the code of the Key was altered
	 *
	 * @param key altered Key
	 * @param up  bubble direction (true if new key code &gt; old key code)
	 */
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

	/**
	 * Forwards a {@link InputEvent} to all {@link Callback} corresponding to the key the InputEvent originates from
	 *
	 * @param evt event to be forwarded
	 * @throws NoLinkFoundException if the map has no entry for the corresponding key
	 */
	public void call( InputEvent evt ) throws NoLinkFoundException
	{
		findKey( evt.code ).call( evt );
	}

	/**
	 * Callback interface to be implemented
	 * <p>
	 * for example through lambda-expressions:
	 * {@code (evt) -> { ... }}
	 */
	public interface Callback
	{
		/**
		 * Receiver of the forwarded InputEvent
		 *
		 * @param evt key event
		 */
		void call( InputEvent evt );
	}

	/**
	 * Represents a callback and its display name
	 */
	public class Link
	{
		private String name;
		private Callback cb;
		private Key key;

		/**
		 * Constructor
		 *
		 * @param name display name of the callback
		 * @param cb   callback object
		 */
		public Link( String name, Callback cb )
		{
			this.name = name;
			this.cb = cb;
			this.key = null;
		}

		/**
		 * @return display name of the callback
		 */
		public String getName()
		{
			return name;
		}

		/**
		 * Sets the display name of the callback
		 *
		 * @param name new display name
		 */
		public void setName( String name )
		{
			this.name = name;
		}

		/**
		 * @return code of the key the callback is bound to (int not Key!)
		 */
		public int getKey()
		{
			return key.getKey();
		}

		/**
		 * Sets the Key the callback is linked to
		 *
		 * @param key link callback to this key
		 */
		void setKey( Key key )
		{
			this.key = key;
		}

		/**
		 * Forwards a InputEvent to the callback
		 *
		 * @param evt InputEvent to be forwarded
		 */
		public void call( InputEvent evt )
		{
			cb.call( evt );
		}
	}

	/**
	 * Represents a key and holds a linked list of all callbacks bound to this key
	 */
	protected class Key
	{
		private int key;
		private Linker linker;
		private int index;

		/**
		 * Constructor
		 *
		 * @param key code of the key
		 */
		public Key( int key )
		{
			this.key = key;
			this.linker = null;
		}

		/**
		 * Ads a {@link Link} to the list of callbacks and binds it
		 *
		 * @param link link to be added
		 */
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

		/**
		 * @return code of the key
		 */
		public int getKey()
		{
			return key;
		}

		/**
		 * Links the linkToBeChanged to another key
		 *
		 * @param key             code of the key the link should be bound to
		 * @param linkToBeChanged link that should be bound to another key
		 */
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

		/**
		 * Forwards the InputEvent to all callbacks bound to this key
		 *
		 * @param evt event to be forwarded
		 */
		public void call( InputEvent evt )
		{
			Linker lk = linker;
			while( lk != null )
			{
				lk.link.call( evt );
				lk = lk.next;
			}
		}

		/**
		 * Puts the Key at the position i inside the array. ALWAYS USE THIS instead of assigning Key manually.
		 * <p>
		 * i is kept as member of Key. Key has to be aware of its position inside the array for efficient array operations
		 *
		 * @param i     position inside the array
		 * @param array the Key will be inserted into this array
		 */
		public void putInArrayAt( int i, Key[] array )
		{
			array[i] = this;
			index = i;
		}

		/**
		 * Element of the linked List containing the {@link Link} objects
		 */
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

	/**
	 * Is thrown, if the search for a key didn't lead to a result, because the key is not registered in the map
	 */
	public class NoLinkFoundException extends Exception
	{
	}
}
