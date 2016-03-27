package com.koucha.experimentalgame.entity;


import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Represents the position, orientation and velocity of an Entity
 */
public class Position
{
	private Matrix4f orientedPosition;
	private Vector3f velocity;

	public Position()
	{
		this.orientedPosition = new Matrix4f();
		this.velocity = new Vector3f();
	}

	public Position( Vector3f position )
	{
		this.orientedPosition = new Matrix4f();
		setOrientedPosition( position, null );
		this.velocity = new Vector3f();
	}

	public void setOrientedPosition( Vector3f position, Quaternionf orientation )
	{
		if( orientation != null )
			orientation.get( orientedPosition );
		else
			orientedPosition.identity();
		if( position != null )
			orientedPosition.translate( position );
	}

	public Position( Matrix4f orientedPosition )
	{
		this.orientedPosition = orientedPosition;
		this.velocity = new Vector3f();
	}

	public Position( Vector3f position, Quaternionf orientation )
	{
		this.orientedPosition = new Matrix4f();
		setOrientedPosition( position, orientation );
		this.velocity = new Vector3f();
	}

	public Position( Vector3f position, Quaternionf orientation, Vector3f velocity )
	{
		this.orientedPosition = new Matrix4f();
		setOrientedPosition( position, orientation );
		this.velocity = velocity;
	}

	public Matrix4f getOrientedPosition()
	{
		return orientedPosition;
	}

	public void setOrientedPosition( Matrix4f orientedPosition )
	{
		this.orientedPosition = orientedPosition;
	}

	public Vector3f getVelocity()
	{
		return velocity;
	}

	public void setVelocity( Vector3f velocity )
	{
		this.velocity = velocity;
	}
}
