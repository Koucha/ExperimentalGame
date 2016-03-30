package com.koucha.experimentalgame.entity;

/**
 * List of all Components
 * <p>
 * Each {@link Component} has its own unique ComponentFlag containing a id (ordinal) and a flag
 * Different flags combined build a unique long mask:
 * <p>
 * {@code Component1.getMask() | Component2.getMask() = longMaskC1C2}
 */
public enum ComponentFlag
{
	Position, Velocity, AttachedCamera;

	private long mask;

	ComponentFlag()
	{
		mask = 1L;

		mask <<= ordinal();
	}

	/**
	 * get the flag bit of this ComponentFlag
	 *
	 * @return the flag bit
	 */
	public long getMask()
	{
		return mask;
	}
}
