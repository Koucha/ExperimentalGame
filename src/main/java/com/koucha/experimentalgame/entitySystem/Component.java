package com.koucha.experimentalgame.entitySystem;

/**
 * Component that can be added to an Entity
 */
public interface Component
{
	/**
	 * Get the unique flag bit for this Component
	 *
	 * @return mask bit
	 */
	default FastBitSet getMask()
	{
		return getFlag().getMask();
	}

	/**
	 * Get this Components flag
	 *
	 * @return identification flag of the Component
	 */
	ComponentFlag getFlag();
}
