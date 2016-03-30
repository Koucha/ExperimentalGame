package com.koucha.experimentalgame.entitySystem;

public interface System
{
	/**
	 * Set the EntityManager this System should be linked to
	 *
	 * @param manager EntityManager managing the Entities this System should process
	 */
	void setManager( EntityManager manager );

	/**
	 * Get the unique flag bit for this System
	 *
	 * @return mask bit
	 */
	default long getMask()
	{
		return getFlag().getMask();
	}

	/**
	 * Get this Systems flag
	 *
	 * @return identification flag of the System
	 */
	SystemFlag getFlag();
}
