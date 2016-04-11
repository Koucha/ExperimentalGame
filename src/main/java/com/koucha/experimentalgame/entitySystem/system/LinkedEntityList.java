package com.koucha.experimentalgame.entitySystem.system;

import com.koucha.experimentalgame.entitySystem.Entity;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// TODO: 10.04.2016 comment
public class LinkedEntityList< T extends LinkedEntityList.LinkElement > implements EntityList
{
	private final static ExecutorService pool = Executors.newWorkStealingPool();

	private final T carbon;

	private LinkElement root;
	private LinkElement last;
	private int count = 0;

	public LinkedEntityList( T carbon )
	{
		this.carbon = carbon;
	}

	@Override
	public void add( Entity entity )
	{
		//noinspection unchecked
		add( (LinkElement) carbon.makeFrom( entity ) );
	}

	public void add( LinkElement element )
	{
		if( root == null )
		{
			element.previous = null;
			element.next = null;
			root = last = element;
			count = 1;
		} else
		{
			element.previous = last;
			element.next = null;
			last.next = element;
			last = element;
			count++;
		}
	}

	@Override
	public void remove( Entity entity )
	{
		if( entity == null )
			return;

		LinkElement pos = root;
		while( pos != null )
		{
			if( pos.entity == entity )
			{
				remove( pos );
				return;
			}
			pos = pos.next;
		}
	}

	public void remove( LinkElement element )
	{
		if( element.next == null )
		{
			last = element.previous;
		} else
		{
			element.next.previous = element.previous;
		}
		if( element.previous == null )
		{
			root = element.next;
		} else
		{
			element.previous.next = element.next;
		}
		count--;
	}

	@Override
	public boolean contains( Entity entity )
	{
		if( entity == null )
			return false;
		LinkElement pos = root;
		while( pos != null )
		{
			if( pos.entity == entity )
				return true;
			pos = pos.next;
		}
		return false;
	}

	@Override
	public int size()
	{
		return count;
	}

	@Override
	public void forEach( Processor processor, boolean parallel )
	{
		if( parallel )
		{
			if( !pool.isTerminated() )
			{
				throw new RuntimeException( "EntityLists can not use forEach concurrently!" );
			}

			LinkElement pos = root;
			while( pos != null )
			{
				final LinkElement temp = pos;
				pool.submit( () -> processor.process( temp ) );
				pos = pos.next;
			}

			try
			{
				pool.shutdown();
				pool.awaitTermination( 5, TimeUnit.SECONDS );
			} catch( InterruptedException e )
			{
				System.err.println( "tasks interrupted" );
			} finally
			{
				if( !pool.isTerminated() )
				{
					System.err.println( "cancel non-finished tasks" );
				}
				pool.shutdownNow();
			}

		} else
		{
			LinkElement pos = root;
			while( pos != null )
			{
				processor.process( pos );
				pos = pos.next;
			}
		}
	}

	@Override
	public Iterator< Element > iterator()
	{
		return new EntityIterator( this );
	}

	public static class LinkElement implements Element
	{
		Entity entity;
		LinkElement previous;
		LinkElement next;

		public LinkElement()
		{
		}

		protected LinkElement( Entity entity )
		{
			this.entity = entity;
		}

		@Override
		public Element makeFrom( Entity entity )
		{
			return new LinkElement( entity );
		}
	}

	private class EntityIterator implements Iterator< Element >
	{
		private final LinkedEntityList list;

		LinkElement nextElem;
		LinkElement pos;

		EntityIterator( LinkedEntityList list )
		{
			this.list = list;
			nextElem = list.root;
		}

		@Override
		public boolean hasNext()
		{
			return nextElem != null;
		}

		@Override
		public Element next()
		{
			pos = nextElem;
			nextElem = pos.next;
			return pos;
		}

		@Override
		public void remove()
		{
			list.remove( pos );
		}
	}
}
