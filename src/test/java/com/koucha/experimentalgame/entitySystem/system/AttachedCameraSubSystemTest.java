package com.koucha.experimentalgame.entitySystem.system;


import com.koucha.experimentalgame.entitySystem.component.AttachedCamera;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.junit.Before;
import org.junit.Test;

import static net.trajano.commons.testing.UtilityClassTestUtil.assertUtilityClassWellDefined;
import static org.junit.Assert.*;


public class AttachedCameraSubSystemTest
{
	private final static float epsilon = 0.000001f;
	private AttachedCamera cam;

	@Before
	public void setUp() throws Exception
	{
		cam = new AttachedCamera();
	}

	@Test
	public void shouldBeAWellDefinedUtilityClass() throws Exception
	{
		assertUtilityClassWellDefined( AttachedCameraSubSystem.class );
	}

	@Test
	public void setFocusPointVector3f() throws Exception
	{
		Vector3f vect = new Vector3f( 1, 2, 3 );

		AttachedCameraSubSystem.setFocusPoint( cam, vect );

		assertEquals( "Values don't match input", vect, cam.focusPoint );
		assertFalse( "Input Vector3f is reused instead of copied", vect == cam.focusPoint );
	}

	@Test
	public void setFocusPointFloat() throws Exception
	{
		float x = 0, y = 3, z = -5;

		AttachedCameraSubSystem.setFocusPoint( cam, x, y, z );

		assertEquals( "Set x value doesn't match input", x, cam.focusPoint.x, epsilon );
		assertEquals( "Set y value doesn't match input", y, cam.focusPoint.y, epsilon );
		assertEquals( "Set z value doesn't match input", z, cam.focusPoint.z, epsilon );
	}

	@Test
	public void getFocusPoint() throws Exception
	{
		cam.focusPoint.set( 1, 6, -1 );

		Vector3f vect = AttachedCameraSubSystem.getFocusPoint( cam );

		assertEquals( "Wrong return value", cam.focusPoint, vect );
	}

	@Test
	public void getDistance() throws Exception
	{
		cam.distance = 5;

		float dist = AttachedCameraSubSystem.getDistance( cam );

		assertEquals( "Wrong return value", cam.distance, dist, epsilon );
	}

	@Test
	public void setDistance() throws Exception
	{
		float dist = 10f;

		AttachedCameraSubSystem.setDistance( cam, dist );

		assertEquals( "Wrong distance set", dist, cam.distance, epsilon );
	}

	@Test
	public void setDirectionQuaternion() throws Exception
	{
		Quaternionf quat = new Quaternionf( -6, 3, 7, 3 );

		AttachedCameraSubSystem.setDirection( cam, quat );

		assertEquals( "Values don't match input", quat, cam.direction );
		assertFalse( "Input Quaternion4f is reused instead of copied", quat == cam.direction );
	}

	@Test
	public void setDirectionFloat() throws Exception
	{
		float topAngle = 1.5f, rotAngle = 1;

		Quaternionf quat = new Quaternionf();
		quat.rotateY( rotAngle );
		quat.rotateX( topAngle );

		AttachedCameraSubSystem.setDirection( cam, topAngle, rotAngle );

		assertEquals( "", quat, cam.direction );
	}

	@Test
	public void setDirectionFloatShouldClampTopAngleInput() throws Exception
	{
		float topAngleGimbalPI = (float) Math.PI, topAngleOverPI = topAngleGimbalPI + 1, topAngleGimbalZero = 0, topAngleNegative = -2;

		Quaternionf quat = new Quaternionf();
		quat.rotateX( topAngleGimbalPI - 0.1f );

		AttachedCameraSubSystem.setDirection( cam, topAngleGimbalPI, 0 );

		assertEquals( "Top Angle should be clamped to <= (PI - 0.1), but is not", quat, cam.direction );

		AttachedCameraSubSystem.setDirection( cam, topAngleOverPI, 0 );

		assertEquals( "Top Angle should be clamped to <= (PI - 0.1), but is not", quat, cam.direction );

		quat.rotationX( 0.1f );

		AttachedCameraSubSystem.setDirection( cam, topAngleGimbalZero, 0 );

		assertEquals( "Top Angle should be clamped to >= 0.1, but is not", quat, cam.direction );

		AttachedCameraSubSystem.setDirection( cam, topAngleNegative, 0 );

		assertEquals( "Top Angle should be clamped to >= 0.1, but is not", quat, cam.direction );
	}

	@Test
	public void getDirection() throws Exception
	{
		Quaternionf quat = new Quaternionf( 1, 3, 5, 9 );
		cam.direction.set( quat );

		Quaternionf result = AttachedCameraSubSystem.getDirection( cam );

		assertEquals( "Wrong return value", quat, result );
	}

	@Test
	public void getCameraMatrixNew() throws Exception
	{
		cam.distance = 10;
		cam.focusPoint.set( 1, 0, 1 );
		cam.direction.set( 0.44721359549995793928183473374626f, 0, 0, 0.89442719099991587856366946749251f );

		Matrix4f mat = new Matrix4f().setLookAt( new Vector3f( 1, 6, 9 ), new Vector3f( 1, 0, 1 ), new Vector3f( 0, 1, 0 ) );

		Matrix4f result = AttachedCameraSubSystem.getCameraMatrix( cam );

		assertEquals( "Wrong return value", mat, result );
	}

	@Test
	public void getCameraMatrixIntoParameter() throws Exception
	{
		cam.distance = 5;
		cam.focusPoint.set( 1, 1, 1 );
		cam.direction.set( 0.44721359549995793928183473374626f, 0, 0, 0.89442719099991587856366946749251f );

		Matrix4f mat = new Matrix4f().setLookAt( new Vector3f( 1, 4, 5 ), new Vector3f( 1, 1, 1 ), new Vector3f( 0, 1, 0 ) );

		Matrix4f input = new Matrix4f();
		Matrix4f result = AttachedCameraSubSystem.getCameraMatrix( cam, input );

		assertEquals( "Wrong return value", mat, result );
		assertTrue( "Didn't return input matrix object", input == result );
	}
}