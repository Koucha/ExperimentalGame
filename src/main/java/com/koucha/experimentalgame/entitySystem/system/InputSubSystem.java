package com.koucha.experimentalgame.entitySystem.system;

import com.koucha.experimentalgame.entitySystem.component.Input;

// TODO: 10.04.2016 comment
public final class InputSubSystem
{
	public static void set( Input input, Selector selector, boolean set )
	{
		if( set )
			set( input, selector );
		else
			unSet( input, selector );
	}

	public static void set( Input input, Selector selector )
	{
		input.bitmask |= selector.value();
	}

	public static void unSet( Input input, Selector selector )
	{
		input.bitmask &= ~(selector.value());
	}

	public static void write( Input dest, Input origin )
	{
		dest.bitmask = origin.bitmask;
	}

	public static boolean check( Input input, Selector up )
	{
		return ( ( input.bitmask & up.value() ) == up.value() );
	}

	/**
	 * Associates actions to flags of a bit getMask
	 */
	public enum Selector
	{
		up,
		down,
		right,
		left,
		skill1,
		skill2,
		skill3,
		skill4,
		skill5,
		skill6,
		skill7,
		skill8;

		private final int val;

		Selector()
		{
			this.val = 0x01 << ordinal();
		}

		/**
		 * @return getMask bit
		 */
		public int value()
		{
			return val;
		}
	}
}
