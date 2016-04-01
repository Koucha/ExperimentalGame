package com.koucha.experimentalgame.entitySystem;

/**
 * List of all Systems
 * <p>
 * Each {@link System} implementation has its own unique ComponentFlag containing an id (ordinal) and a flag
 * Different flags combined build a unique long mask:
 * <p>
 * {@code System1.getMask() | System2.getMask() = longMaskS1S2}
 */
public enum SystemFlag
{
	System1,
	System2,

	// Has to be the last element in the list!
	AFTER_LAST;

	private long mask;

	SystemFlag()
	{
		mask = 1L;

		mask <<= ordinal();
	}

	/**
	 * get the flag bit of this SystemFlag
	 *
	 * @return the flag bit
	 */
	public long getMask()
	{
		return mask;
	}
}
