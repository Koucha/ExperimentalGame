package com.koucha.experimentalgame.entitySystem;

/**
 * List of all Components
 * <p>
 * Each {@link Component} implementation has its own unique ComponentFlag containing a id (ordinal) and a FastBitSet flag
 *
 * @see FastBitSet
 */
public enum ComponentFlag
{
	Position, Velocity, AttachedCamera, Mesh, AABB, LocalPlayer, Input;

	private FastBitSet mask;

	ComponentFlag()
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
	 * get the flag bit of this ComponentFlag
	 *
	 * @return the flag bit
	 */
	public FastBitSet getMask()
	{
		return mask;
	}
}
