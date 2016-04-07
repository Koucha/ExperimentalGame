package com.koucha.experimentalgame.entitySystem;


import com.koucha.experimentalgame.entitySystem.component.AABB;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;


public class QuadTree implements List<Entity>
{
	private List<Acceptor> accList = new ArrayList<>( 3 );

	private List<Element> list = new LinkedList<>();
	private Node root;

	public QuadTree( float xMin, float yMin, float xMax, float yMax )
	{
		this.root = new Node( xMin, yMin, xMax, yMax, null );
	}

	@Override
	public int size()
	{
		return list.size();
	}

	@Override
	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	@Override
	public boolean contains( Object o )
	{
		if( !( o instanceof Entity ) )
			return false;

		for( Element element : list )
		{
			if( element.entity == o)
				return true;
		}

		return false;
	}

	@Override
	public boolean add( Entity entity )
	{
		if( contains( entity ) )
			return false;

		AABB aabb = (AABB) entity.get( ComponentFlag.AABB );
		Element element = new Element();
		element.entity = entity;

		element.container = root.add( entity, aabb );

		list.add( element );

		return false;
	}

	@Override
	public boolean remove( Object o )
	{
		if( !( o instanceof Entity ) )
			return false;

		Iterator<Element> iter = list.iterator();
		for( Element element; iter.hasNext(); )
		{
			element = iter.next();
			if( element.entity == o )
			{
				iter.remove();
				element.container.remove( element.entity );
				return true;
			}
		}

		return false;
	}

	@Override
	public void clear()
	{
		list.clear();
		root.list.clear();
		root.nodeTL = null;
		root.nodeTR = null;
		root.nodeBL = null;
		root.nodeBR = null;
	}


	/** not implemented*/ @Override public Iterator< Entity > iterator ()
	{
		throw new NotImplementedException();
	}
	/** not implemented*/ @Override public Object[] toArray ()
	{
		throw new NotImplementedException();
	}
	/** not implemented*/ @Override public < T > T[] toArray (T[]a)
	{
		throw new NotImplementedException();
	}
	/** not implemented*/ @Override public boolean containsAll (Collection < ? > c)
	{
		throw new NotImplementedException();
	}
	/** not implemented*/ @Override public boolean addAll (Collection < ?extends Entity > c)
	{
		throw new NotImplementedException();
	}
	/** not implemented*/ @Override public boolean addAll ( int index, Collection<?extends Entity > c)
	{
		throw new NotImplementedException();
	}
	/** not implemented*/ @Override public boolean removeAll (Collection < ? > c)
	{
		throw new NotImplementedException();
	}
	/** not implemented*/ @Override public boolean retainAll (Collection < ? > c)
	{
		throw new NotImplementedException();
	}
	/** not implemented*/ @Override public Entity get ( int index)
	{
		throw new NotImplementedException();
	}
	/** not implemented*/ @Override public Entity set ( int index, Entity element)
	{
		throw new NotImplementedException();
	}
	/** not implemented*/ @Override public void add ( int index, Entity element)
	{
		throw new NotImplementedException();
	}
	/** not implemented*/ @Override public Entity remove ( int index)
	{
		throw new NotImplementedException();
	}
	/** not implemented*/ @Override public int indexOf (Object o)
	{
		throw new NotImplementedException();
	}
	/** not implemented*/ @Override public int lastIndexOf (Object o)
	{
		throw new NotImplementedException();
	}
	/** not implemented*/ @Override public ListIterator< Entity > listIterator ()
	{
		throw new NotImplementedException();
	}
	/** not implemented*/ @Override public ListIterator< Entity > listIterator ( int index)
	{
		throw new NotImplementedException();
	}
	/** not implemented*/ @Override public List< Entity > subList ( int fromIndex, int toIndex) {
		throw new NotImplementedException();
	}

	/**
	 * test if an Entity should be rejected
	 *
	 * @param entity Entity to be tested
	 * @return true if no acceptor accepts the entity, false otherwise
	 */
	public boolean rejectEntity( Entity entity )
	{
		for( Acceptor acc : accList )
		{
			if( acc.accept( entity ) )
				return false;
		}
		return true;
	}

	/**
	 * Add an Acceptor to this QuadTree
	 * <p>
	 * The QuadTree reject Entities that aren't accepted by any of its Acceptors
	 *
	 * @param acc Acceptor to be added
	 *
	 * @see #rejectEntity(Entity)
	 */
	public void addAcceptor( Acceptor acc )
	{
		accList.add( acc );
	}

	/**
	 * Light weight dependency injection
	 */
	public interface Acceptor
	{
		boolean accept(Entity entity);
	}

	private class Element
	{

		Entity entity;
		Node container;
	}

	private class Node
	{
		float xMax;
		float yMax;
		float xMin;
		float yMin;

		Node nodeTL;
		Node nodeTR;
		Node nodeBL;
		Node nodeBR;

		Node parent;

		List<Entity> list = new LinkedList<>();

		Node( float xMin, float yMin, float xMax, float yMax, Node parent )
		{
			this.xMax = xMax;
			this.yMax = yMax;
			this.xMin = xMin;
			this.yMin = yMin;
			this.parent = parent;
		}

		Node add( Entity entity, AABB aabb)
		{
			if( nodeTL == null )
			{
				float xMid = ( xMin + xMax )/2f;
				float yMid = ( yMin + yMax )/2f;
				nodeTL = new Node( xMin, yMid, xMid, yMax, this);
				nodeTR = new Node( xMid, yMid, xMax, yMax, this);
				nodeBL = new Node( xMin, yMin, xMid, yMid, this);
				nodeBR = new Node( xMid, yMin, xMax, yMid, this);
			}

			if( nodeTL.hullOf(aabb) )
				return nodeTL.add( entity, aabb );
			if( nodeTR.hullOf(aabb) )
				return nodeTR.add( entity, aabb );
			if( nodeBL.hullOf(aabb) )
				return nodeBL.add( entity, aabb );
			if( nodeBR.hullOf(aabb) )
				return nodeBR.add( entity, aabb );

			list.add( entity );
			return this;
		}

		boolean hullOf( AABB aabb )
		{
			// TODO: 07.04.2016 Bounding box + position
			return ( aabb.min.x > xMin && aabb.max.x < xMax &&
					 aabb.min.z > yMin && aabb.max.z < yMax );
		}

		void remove( Entity entity )
		{
			list.remove( entity );

			clean();
		}

		void clean()
		{
			if( list.isEmpty() && nodeTL.isEmpty() && nodeTR.isEmpty() && nodeBL.isEmpty() && nodeBR.isEmpty() )
			{
				nodeTL = null;
				nodeTR = null;
				nodeBL = null;
				nodeBR = null;
			}

			if( parent != null )
				parent.clean();
		}

		boolean isEmpty()
		{
			return ( nodeTL == null );
		}

		List<Entity> getContent()
		{
			List<Entity> returnList = new LinkedList<>();
			downRecursionGetContent( returnList );
			if( parent != null )
				parent.upRecursionGetContent( returnList );
			return returnList;
		}

		void downRecursionGetContent( List<Entity> returnList )
		{
			if( isEmpty() )
				return;

			returnList.addAll( list );
			nodeTL.downRecursionGetContent( list );
			nodeTR.downRecursionGetContent( list );
			nodeBL.downRecursionGetContent( list );
			nodeBR.downRecursionGetContent( list );
		}

		void upRecursionGetContent( List<Entity> returnList )
		{
			returnList.addAll( list );
			if( parent != null )
				parent.upRecursionGetContent( returnList );
		}
	}
}
