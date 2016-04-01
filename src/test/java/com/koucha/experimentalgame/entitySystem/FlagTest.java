package com.koucha.experimentalgame.entitySystem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Test the genereated masks of {@link SystemFlag} and {@link ComponentFlag}
 */
public class FlagTest
{
	@Test
	public void SystemFlag() throws Exception
	{
		for( SystemFlag flag : SystemFlag.class.getEnumConstants() )
		{
			testFlag( flag.name(), flag.getMask(), flag.ordinal() );
		}
	}

	@Test
	public void ComponentFlag() throws Exception
	{
		for( ComponentFlag flag : ComponentFlag.class.getEnumConstants() )
		{
			testFlag( flag.name(), flag.getMask(), flag.ordinal() );
		}
	}

	private void testFlag( String name, FastBitSet mask, int ordinal )
	{
		assertNotNull( "SystemFlag \"" + name + "\" has no mask", mask );
		assertFalse( "SystemFlag \"" + name + "\" has invalid mask", mask.isZero() );
		FastBitSet shoudBe = new FastBitSet( ordinal );
		assertEquals( "SystemFlag \"" + name + "\" has wrong mask\nis: " + mask + "\nshould be: " + shoudBe, shoudBe, mask );
	}
}