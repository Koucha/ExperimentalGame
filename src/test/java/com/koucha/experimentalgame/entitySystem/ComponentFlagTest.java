package com.koucha.experimentalgame.entitySystem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test the genereated masks and fill status of {@link ComponentFlag}
 */
public class ComponentFlagTest
{
	@Test
	public void maskOfFirstComponentFlagShouldBe0x01() throws Exception
	{
		ComponentFlag sys = ComponentFlag.Position;    // Set to first Component in list!

		assertEquals( "Tested ComponentFlag is not the first in the list", 0, sys.ordinal() );
		assertEquals( "Tested ComponentFlag (" + sys.name() + ") has wrong mask!", 0x01L, sys.getMask() );
	}

	@Test
	public void maskOfSecondComponentFlagShouldBe0x02() throws Exception
	{
		ComponentFlag sys = ComponentFlag.Velocity;    // Set to second Component in list!

		assertEquals( "Tested ComponentFlag is not the second in the list", 1, sys.ordinal() );
		assertEquals( "Tested ComponentFlag (" + sys.name() + ") has wrong mask!", 0x02L, sys.getMask() );
	}

	/**
	 * Successfull if there's at least one free spot in the list.
	 * <p>
	 * If it fails the generation of valid masks is not guaranteed for ComponentFlag elements!
	 *
	 * @throws Exception assertion Exception
	 */
	@Test
	public void listSizeShouldNotExceedMaskSize() throws Exception
	{
		ComponentFlag sys = ComponentFlag.AFTER_LAST;    // Element after the last Element

		assertEquals( "AFTER_LAST has wrong mask", 0x1 << sys.ordinal(), sys.getMask() );
		assertFalse( "The list of ComponentFlags is full or over filled! There could be invalid masks!!", sys.getMask() == 0x0L );
	}
}