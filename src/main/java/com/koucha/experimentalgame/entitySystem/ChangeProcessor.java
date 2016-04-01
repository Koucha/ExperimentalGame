package com.koucha.experimentalgame.entitySystem;


/**
 * Light weight dependency injection
 *
 * @see EntityManager#checkChangedEntities(SystemFlag, ChangeProcessor)
 * @see com.koucha.experimentalgame.entitySystem.system.AbstractSystem#updateEntityList()
 */
public interface ChangeProcessor
{
	void process( Entity entity, ChangeType type );
}
