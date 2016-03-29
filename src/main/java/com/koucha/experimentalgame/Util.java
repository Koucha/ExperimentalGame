package com.koucha.experimentalgame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Utility class containing only static methods of code parts that are repeatedly used in various context
 */
@SuppressWarnings( "unused" )
public class Util
{
	private Util()
	{
	}

	/**
	 * Clamps the variable var to an interval defined by a and b
	 * <p>
	 * The order of a and b does not matter, but var may not be null
	 *
	 * @param var variable to be clamped
	 * @param a   first boundary
	 * @param b   second boundary
	 * @param <T> Type of the variables
	 * @return clamped result
	 */
	public static < T extends Number & Comparable< T > > T clamp( T var, T a, T b )
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

	/**
	 * Clamps the variable var to a value between minus infinity and top
	 *
	 * @param var variable to be clamped
	 * @param top top boundary
	 * @param <T> Type of the variables
	 * @return clamped result
	 */
	public static < T extends Number & Comparable< T > > T clampTop( T var, T top )
	{
		// ensure a is the smaller boundary
		if( var.compareTo( top ) > 0 )
		{
			return top;
		}

		return var;
	}

	/**
	 * Clamps the variable var to a value between bottom and infinity
	 *
	 * @param var    variable to be clamped
	 * @param bottom lower boundary
	 * @param <T>    Type of the variables
	 * @return clamped result
	 */
	public static < T extends Number & Comparable< T > > T clampBottom( T var, T bottom )
	{
		// ensure a is the smaller boundary
		if( var.compareTo( bottom ) < 0 )
		{
			return bottom;
		}

		return var;
	}

	public static String loadAsString( String fileName )
	{
		StringBuilder buff = new StringBuilder();
		try
		{
			BufferedReader reader = new BufferedReader( new FileReader( fileName ) );
			String line;
			while( (line = reader.readLine()) != null )
			{
				buff.append( line ).append( "\n" );
			}
			reader.close();
		} catch( IOException e )
		{
			e.printStackTrace();
		}

		return buff.toString();
	}

}
