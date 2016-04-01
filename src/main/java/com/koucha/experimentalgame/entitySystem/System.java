package com.koucha.experimentalgame.entitySystem;

/**
 * Each System implementation processes Entities that have certain Components
 */
public interface System
{
	/**
	 * Set the EntityManager this System should be linked to
	 *
	 * @param manager EntityManager managing the Entities this System should process
	 */
	void setEntityManager( EntityManager manager );

	/**
	 * Get the unique flag bit for this System
	 *
	 * @return mask bit
	 */
	default FastBitSet getMask()
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
