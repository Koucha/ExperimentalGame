package com.koucha.experimentalgame.rendering;

import com.koucha.experimentalgame.entitySystem.component.Mesh;
import org.joml.Vector3f;

/**
 * Represents a Rectangle that can be rendered
 */
public class Rectangle extends Mesh
{
	/**
	 * Position of the center of the rotated rectangle
	 */
	private Vector3f position;

	/**
	 * Rotation angle in radians clockwise from top
	 */
	private float angle;

	/**
	 * Rectangle dimensions
	 */
	private float width, height;

	/**
	 * Fill color
	 */
	private Color color;

	/**
	 * Creates a filled Rectangle
	 *
	 * @param position coordinate of the center of the rectangle
	 * @param angle rotation in radians clockwise from top
	 * @param width x axis aligned size of the rectangle
	 * @param height y axis aligned size of the rectangle
	 * @param color paint color
	 */
	public Rectangle( Vector3f position, float angle, float width, float height, Color color )
	{
		this.position = position;
		this.angle = angle;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public void setPosition( Vector3f position )
	{
		this.position = position;
	}

	public float getAngle()
	{
		return angle;
	}

	public void setAngle( float angle )
	{
		this.angle = angle;
	}

	public float getWidth()
	{
		return width;
	}

	public void setWidth( float width )
	{
		this.width = width;
	}

	public float getHeight()
	{
		return height;
	}

	public void setHeight( float height )
	{
		this.height = height;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor( Color color )
	{
		this.color = color;
	}
}
