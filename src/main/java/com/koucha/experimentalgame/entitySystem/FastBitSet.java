package com.koucha.experimentalgame.entitySystem;

import java.text.Format;

/**
 * Implements a fast BitSet with over 64 flags
 */
public class FastBitSet
{
	public static final int BIT_COUNT = 4 * 64;

	private long set1;
	private long set2;
	private long set3;
	private long set4;

	/**
	 * Constructor; all flags are set to false
	 */
	public FastBitSet()
	{
		set1 = 0;
		set2 = 0;
		set3 = 0;
		set4 = 0;
	}

	/**
	 * Constructor; sets one flag to true
	 *
	 * @param i index of the flag to be set to true
	 */
	public FastBitSet( int i )
	{
		this();
		setBitTrue( i );
	}

	/**
	 * @param i Index of the flag to be set to true
	 */
	public void setBitTrue( int i )
	{
		if( i < 0 )
			throw new IndexOutOfBoundsException( "Negative indices are not allowed" );
		if( i >= BIT_COUNT )
			throw new IndexOutOfBoundsException( "Index too big (is: " + i + " max: " + BIT_COUNT + ")" );

		if( i < 64 )
		{
			set1 |= 0x01L << i;
		}else if( i < 128 )
		{
			set2 |= 0x01L << ( i - 64 );
		}else if( i < 192 )
		{
			set3 |= 0x01L << ( i - 128 );
		}else
		{
			set4 |= 0x01L << ( i - 192 );
		}
	}

	/**
	 * @param i Index of the flag to be set to false
	 */
	public void setBitFalse( int i )
	{
		if( i < 0 )
			throw new IndexOutOfBoundsException( "Negative indices are not allowed" );
		if( i >= BIT_COUNT )
			throw new IndexOutOfBoundsException( "Index too big (is: " + i + " max: " + BIT_COUNT + ")" );

		if( i < 64 )
		{
			set1 &= ~( 0x01L << i );
		}else if( i < 128 )
		{
			set2 &= ~( 0x01L << ( i - 64 ) );
		}else if( i < 192 )
		{
			set3 &= ~( 0x01L << ( i - 128 ) );
		}else
		{
			set4 &= ~( 0x01L << ( i - 192 ) );
		}
	}

	/**
	 * @param i Index of the flag to be queried
	 * @return {@code true} if the flag is set, {@code false} otherwise
	 */
	public boolean getBit( int i )
	{
		if( i < 0 )
			throw new IndexOutOfBoundsException( "Negative indices are not allowed" );
		if( i >= BIT_COUNT )
			throw new IndexOutOfBoundsException( "Index too big (is: " + i + " max: " + BIT_COUNT + ")" );

		if( i < 64 )
		{
			return ( set1 & ( 0x01L << i ) ) != 0;
		}else if( i < 128 )
		{
			return ( set2 & ( 0x01L << ( i - 64 ) ) ) != 0;
		}else if( i < 192 )
		{
			return ( set3 & ( 0x01L << ( i - 128 ) ) ) != 0;
		}else
		{
			return ( set4 & ( 0x01L << ( i - 192 ) ) ) != 0;
		}
	}

	/**
	 * Test if no flag is set
	 *
	 * @return {@code true} if no flag is set, {@code false} otherwise
	 */
	public boolean isZero()
	{
		return (set1 | set2 | set3 | set4) == 0;
	}

	/**
	 * Set all bits that are true in the given bitset to true in this bitset
	 *
	 * @param toAdd contains all flags that should be set to true in this bitset
	 */
	public void add( FastBitSet toAdd )
	{
		set1 |= toAdd.set1;
		set2 |= toAdd.set2;
		set3 |= toAdd.set3;
		set4 |= toAdd.set4;
	}

	/**
	 * Set bits that are true in the given bitset to false in this bitset
	 *
	 * @param toRemove contains all flags thet should be set to false in this bitset
	 */
	public void remove( FastBitSet toRemove )
	{
		set1 &= ~toRemove.set1;
		set2 &= ~toRemove.set2;
		set3 &= ~toRemove.set3;
		set4 &= ~toRemove.set4;
	}

	/**
	 * Test if this all the flags that are true in the subSet are true in this bitset
	 * @param subSet contains all flags to be tested
	 * @return {@code true} if all flags that are true in subSet are true in this bitset too, {@code false} otherwise
	 */
	public boolean contains( FastBitSet subSet )
	{
		return  ((set1 & subSet.set1) == subSet.set1) &&
				((set2 & subSet.set2) == subSet.set2) &&
				((set3 & subSet.set3) == subSet.set3) &&
				((set4 & subSet.set4) == subSet.set4);
	}

	@Override
	public String toString()
	{
		return String.format( "0x%016X%016X%016X%016X", set4, set3, set2, set1 );
	}

	@Override
	public int hashCode()
	{
		return Long.hashCode( set1 ) ^ Long.hashCode( set2 ) ^ Long.hashCode( set3 ) ^ Long.hashCode( set4 );
	}

	@Override
	public boolean equals( Object obj )
	{
		if( obj instanceof FastBitSet )
		{
			FastBitSet fbs = (FastBitSet)obj;
			return ( set1 == fbs.set1 ) && ( set2 == fbs.set2 ) && ( set3 == fbs.set3 ) && ( set4 == fbs.set4 );
		}
		return false;
	}
}
