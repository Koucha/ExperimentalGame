package com.koucha.experimentalgame.entitySystem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test the genereated masks and fill status of {@link SystemFlag}
 */
public class SystemFlagTest
{
	@Test
	public void maskOfFirstSystemFlagShouldBe0x01() throws Exception
	{
		SystemFlag sys = SystemFlag.System1;    // Set to first System in list!

		assertEquals( "Tested SystemFlag is not the first in the list", 0, sys.ordinal() );
		assertEquals( "Tested SystemFlag (" + sys.name() + ") has wrong mask!", 0x01L, sys.getMask() );
	}

	@Test
	public void maskOfSecondSystemFlagShouldBe0x02() throws Exception
	{
		SystemFlag sys = SystemFlag.System2;    // Set to second System in list!

		assertEquals( "Tested SystemFlag is not the second in the list", 1, sys.ordinal() );
		assertEquals( "Tested SystemFlag (" + sys.name() + ") has wrong mask!", 0x02L, sys.getMask() );
	}

	/**
	 * Successfull if there's at least one free spot in the list.
	 * <p>
	 * If it fails the generation of valid masks is not guaranteed for SystemFlag elements!
	 */
	@Test
	public void listSizeShouldNotExceedMaskSize() throws Exception
	{
		SystemFlag sys = SystemFlag.AFTER_LAST;    // Element after the last Element

		assertEquals( "AFTER_LAST has wrong mask", 0x1 << sys.ordinal(), sys.getMask() );
		assertFalse( "The list of SystemFlags is full or over filled! There could be invalid masks!!", sys.getMask() == 0x0L );
	}
}