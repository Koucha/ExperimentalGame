package com.koucha.experimentalgame.entitySystem.system;

import com.koucha.experimentalgame.entitySystem.Entity;

// TODO: 10.04.2016 comment
public interface EntityList extends Iterable< EntityList.Element >
{

	void add( Entity entity );

	void remove( Entity entity );

	boolean contains( Entity entity );

	int size();

	default void forEach( Processor processor )
	{
		forEach( processor, false );
	}

	void forEach( Processor processor, boolean parallel );

	interface Element
	{
		Element makeFrom( Entity entity );
	}

	interface Processor
	{
		void process( Element element );
	}
}
