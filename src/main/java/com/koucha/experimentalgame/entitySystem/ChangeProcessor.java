package com.koucha.experimentalgame.entitySystem;


public interface ChangeProcessor
{
	void process( Entity entity, ChangeType type );
}
