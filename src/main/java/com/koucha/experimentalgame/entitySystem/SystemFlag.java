package com.koucha.experimentalgame.entitySystem;

/**
 * List of all Systems
 * <p>
 * Each {@link System} implementation has its own unique ComponentFlag containing an id (ordinal) and a FastBitSet flag
 *
 * @see FastBitSet
 */
public enum SystemFlag
{
	RenderSystem, PhysicsSystem, LocalPlayerInputSystem, InputProcessingSystem;

	private FastBitSet mask;

	SystemFlag()
	{
		int ord = ordinal();

		if( ord >= FastBitSet.BIT_COUNT )
		{
			mask = null;
			return;
		}

		mask = new FastBitSet( ordinal() );
	}

	/**
	 * get the flag bit of this SystemFlag
	 *
	 * @return the flag bit
	 */
	public FastBitSet getMask()
	{
		return mask;
	}
}
