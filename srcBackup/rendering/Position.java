package com.koucha.experimentalgame.rendering;


import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Represents the position, orientation and velocity of an Entity
 */
public class Position
{
	private Matrix4f orientedPosition;
	private Quaternionf orientation;
	private Vector3f position;
	private Vector3f velocity;

	public Position()
	{
		this.orientedPosition = new Matrix4f();
		this.orientation = new Quaternionf();
		this.position = new Vector3f();
		this.velocity = new Vector3f();
	}

	public Position( Vector3f position )
	{
		this.orientedPosition = new Matrix4f();
		this.orientation = new Quaternionf();
		this.position = position;
		this.velocity = new Vector3f();

		updateMatrix();
	}

	private void updateMatrix()
	{
		orientedPosition.translation( position ).rotate( orientation );
	}

	public Position( Vector3f position, Quaternionf orientation )
	{
		this.orientedPosition = new Matrix4f();
		this.orientation = orientation;
		this.position = position;
		this.velocity = new Vector3f();

		updateMatrix();
	}

	public Matrix4f getOrientedPosition()
	{
		return orientedPosition;
	}

	public Position( Vector3f position, Quaternionf orientation, Vector3f velocity )
	{
		this.orientedPosition = new Matrix4f();
		this.orientation = orientation;
		this.position = position;
		this.velocity = velocity;

		updateMatrix();
	}

	public Quaternionf getOrientation()
	{
		return orientation;
	}

	public void setOrientation( Quaternionf orientation )
	{
		this.orientation = orientation;
		updateMatrix();
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public void setPosition( Vector3f position )
	{
		this.position = position;
		updateMatrix();
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
