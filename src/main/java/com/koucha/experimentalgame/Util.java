package com.koucha.experimentalgame;

/**
 * Utility class containing only static methods of code parts that are repeatedly used in various context
 */
public class Util
{
	/**
	 * Clamps the variable var to an interval defined by a and b
	 * <p>
	 * The order of a and b does not matter, but var may not be null
	 *
	 * @param var variable to be clamped (not null)
	 * @param a   first boundary
	 * @param b   second boundary
	 * @param <T> Type of the variables
	 * @return clamped result
	 */
	static < T extends Number & Comparable< T > > T clamp( T var, T a, T b )
	{
		// ensure a is the smaller boundary
		if( a.compareTo( b ) > 0 )
		{
			T c = b;
			b = a;
			a = c;
		}

		if( var.compareTo( a ) < 0 )
		{
			return a;
		} else if( var.compareTo( b ) > 0 )
		{
			return b;
		} else
		{
			return var;
		}
	}
}
