package com.koucha.experimentalgame.entity;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Attached Camera. Contains focus point, distance from that point and direction from the point to the camera
 */
public class AttachedCamera implements Component
{
	private Vector3f focusPoint = new Vector3f();
	/** distance between camera and focusPoint */
	private float distance = 1;
	/** direction from focus point to camera */
	private Quaternionf direction = new Quaternionf();

	public AttachedCamera()
	{
	}

	public AttachedCamera( float x, float y, float z, float distance )
	{
		this.distance = distance;
		setFocusPoint( x, y, z );
	}

	public void setFocusPoint( float x, float y, float z )
	{
		this.focusPoint.set( x, y, z );
	}

	public AttachedCamera( float x, float y, float z, float distance, float topAngle, float turnAngle )
	{
		this.focusPoint.set( x, y, z );
		this.distance = distance;
		setDirection( topAngle, turnAngle );
	}

	public void setDirection( float topAngle, float turnAngle )
	{
		topAngle = (topAngle < ((float) Math.PI - 0.1f)) ? ((topAngle > 0.1f) ? (topAngle) : (0.1f)) : ((float) Math.PI - 0.1f);
		this.direction.rotationY( turnAngle ).rotateX( topAngle );
	}

	public AttachedCamera( Vector3f focusPoint, float distance )
	{
		this.focusPoint = focusPoint;
		this.distance = distance;
	}

	public AttachedCamera( Vector3f focusPoint, float distance, Quaternionf direction )
	{
		this.focusPoint = focusPoint;
		this.distance = distance;
		this.direction = direction;
	}

	@Override
	public ComponentFlag getFlag()
	{
		return ComponentFlag.AttachedCamera;
	}

	public Vector3f getFocusPoint()
	{
		return focusPoint;
	}

	public void setFocusPoint( Vector3f focusPoint )
	{
		this.focusPoint = focusPoint;
	}

	public float getDistance()
	{
		return distance;
	}

	public void setDistance( float distance )
	{
		this.distance = distance;
	}

	public Quaternionf getDirection()
	{
		return direction;
	}

	public void setDirection( Quaternionf direction )
	{
		this.direction = direction;
	}

	public Matrix4f getCameraMatrix()
	{
		return getCameraMatrix( new Matrix4f() );
	}

	public Matrix4f getCameraMatrix( Matrix4f dest )
	{
		return dest.setLookAt( direction.transform( new Vector3f( 0, distance, 0 ) ).add( focusPoint ), focusPoint, new Vector3f( 0, 1, 0 ) );
	}
}
