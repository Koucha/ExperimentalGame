package com.koucha.experimentalgame.entitySystem;

/**
 * List of all Components
 * <p>
 * Each {@link Component} implementation has its own unique ComponentFlag containing a id (ordinal) and a flag
 * Different flags combined build a unique long mask:
 * <p>
 * {@code Component1.getMask() | Component2.getMask() = longMaskC1C2}
 */
public enum ComponentFlag
{
	Position,
	Velocity,
	AttachedCamera,

	// Has to be the last element in the list!
	AFTER_LAST;

	private long mask;

	ComponentFlag()
	{
		int ord = ordinal();

		if(ord >= 64)
		{
			mask = 0L;
			return;
		}

		mask = 1L;

		mask <<= ord;
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
