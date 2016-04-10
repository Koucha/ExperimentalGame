package com.koucha.experimentalgame.entitySystem.system;

import com.koucha.experimentalgame.entitySystem.component.Mesh;
import org.junit.Before;
import org.junit.Test;

import static net.trajano.commons.testing.UtilityClassTestUtil.assertUtilityClassWellDefined;

// TODO: 10.04.2016 implement tests
public class MeshSubSystemTest
{
	private final static float epsilon = 0.000001f;
	private Mesh mesh;

	@Before
	public void setUp() throws Exception
	{
		mesh = new Mesh();
	}

	@Test
	public void shouldBeAWellDefinedUtilityClass() throws Exception
	{
		assertUtilityClassWellDefined( MeshSubSystem.class );
	}
}