package com.koucha.experimentalgame.entity;

/**
 * Component that can be added to an Entity
 */
public interface Component
{
	/**
	 * Get the unique flag bit for this Component
	 *
	 * @return getMask bit
	 */
	default long getMask()
	{
		return getFlag().getMask();
	}

	/**
	 * Get the Components Flag
	 *
	 * @return identification of the Component
	 */
	ComponentFlag getFlag();
}
