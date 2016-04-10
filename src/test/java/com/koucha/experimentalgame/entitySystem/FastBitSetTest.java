package com.koucha.experimentalgame.entitySystem;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;


public class FastBitSetTest
{
	@Test
	public void setBitTrue() throws Exception
	{
		FastBitSet bs = new FastBitSet();
		Field set = FastBitSet.class.getDeclaredField( "set1" );
		set.setAccessible( true );

		assertEquals( "Initialized FastBitSet not zero", 0, set.getLong( bs ) );

		bs.setBitTrue( 5 );

		assertEquals( "Set bit isn't matching", 0b100000, set.getLong( bs ) );
	}

	@Test
	public void setBitFalse() throws Exception
	{
		FastBitSet bs = new FastBitSet();
		Field set = FastBitSet.class.getDeclaredField( "set2" );
		set.setAccessible( true );

		assertEquals( "Initialized FastBitSet not zero", 0, set.getLong( bs ) );

		set.setLong( bs, 0b100 );

		bs.setBitFalse( 2 + 64 );

		assertEquals( "Set bit isn't matching", 0, set.getLong( bs ) );
	}

	@Test
	public void getBit() throws Exception
	{
		FastBitSet bs = new FastBitSet();
		Field set = FastBitSet.class.getDeclaredField( "set3" );
		set.setAccessible( true );

		assertEquals( "Initialized FastBitSet not zero", 0, set.getLong( bs ) );

		set.setLong( bs, 0b100 );

		assertTrue( "Set bit isn't matching (not true)", bs.getBit( 2 + 128 ) );
		assertFalse( "Set bit isn't matching (not false)", bs.getBit( 12 + 128 ) );
	}

	@Test
	public void isZero() throws Exception
	{
		FastBitSet bs = new FastBitSet();
		Field set = FastBitSet.class.getDeclaredField( "set4" );
		set.setAccessible( true );

		assertTrue( "isZero doesn't detect zero state", bs.isZero() );

		set.setLong( bs, 0b100 );

		assertFalse( "isZero doesn't detect non zero state", bs.isZero() );
	}

	@Test
	public void add() throws Exception
	{
		FastBitSet bs1 = new FastBitSet(), bs2 = new FastBitSet();
		Field set = FastBitSet.class.getDeclaredField( "set4" );
		set.setAccessible( true );

		assertEquals( "Initialized FastBitSet not zero", 0, set.getLong( bs1 ) );
		assertEquals( "Initialized FastBitSet not zero", 0, set.getLong( bs2 ) );

		set.setLong( bs2, 1 );

		bs1.add( bs2 );

		assertEquals( "bs2 was changed!", 1, set.getLong( bs2 ) );
		assertEquals( "bs2 wasn't added to bs1", 1, set.getLong( bs1 ) );
	}

	@Test
	public void remove() throws Exception
	{
		FastBitSet bs1 = new FastBitSet(), bs2 = new FastBitSet();
		Field set = FastBitSet.class.getDeclaredField( "set2" );
		set.setAccessible( true );

		assertEquals( "Initialized FastBitSet not zero", 0, set.getLong( bs1 ) );
		assertEquals( "Initialized FastBitSet not zero", 0, set.getLong( bs2 ) );

		set.setLong( bs2, 28 );
		set.setLong( bs1, 111 );

		bs1.remove( bs2 );

		assertEquals( "bs2 was changed!", 28, set.getLong( bs2 ) );
		assertEquals( "wrong result for bs1", 99, set.getLong( bs1 ) );
	}

	@Test
	public void contains() throws Exception
	{
		FastBitSet bs842 = new FastBitSet(), bs42 = new FastBitSet(), bs2 = new FastBitSet();
		Field set = FastBitSet.class.getDeclaredField( "set1" );
		set.setAccessible( true );

		assertEquals( "Initialized FastBitSet not zero", 0, set.getLong( bs842 ) );
		assertEquals( "Initialized FastBitSet not zero", 0, set.getLong( bs42 ) );
		assertEquals( "Initialized FastBitSet not zero", 0, set.getLong( bs2 ) );

		set.setLong( bs2, 2 );
		set.setLong( bs42, 2 + 4 );
		set.setLong( bs842, 2 + 4 + 8 );

		assertTrue( "self inclusion failed", bs842.contains( bs842 ) );
		assertTrue( "subset inclusion failed", bs842.contains( bs42 ) );
		assertTrue( "subset inclusion failed", bs842.contains( bs2 ) );
		assertFalse( "superset exclusion failed", bs42.contains( bs842 ) );
		assertTrue( "self inclusion failed", bs42.contains( bs42 ) );
		assertTrue( "subset inclusion failed", bs42.contains( bs2 ) );
		assertFalse( "superset exclusion failed", bs2.contains( bs842 ) );
		assertFalse( "superset exclusion failed", bs2.contains( bs42 ) );
		assertTrue( "self inclusion failed", bs2.contains( bs2 ) );

		set.setLong( bs2, 2 + 1 );

		assertFalse( "mutual exclusion failed", bs842.contains( bs2 ) );
		assertFalse( "mutual exclusion failed", bs2.contains( bs842 ) );
	}

}