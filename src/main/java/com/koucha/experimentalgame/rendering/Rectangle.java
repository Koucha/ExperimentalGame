package com.koucha.experimentalgame.rendering;

import java.awt.*;

/**
 * Represents a Rectangle that can be rendered
 */
public class Rectangle implements Renderable
{
	/**
	 * Position of the center of the rotated rectangle
	 */
	private float posX, posY;

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
	 * @param posX x coordinate of the center of the rectangle
	 * @param posY y coordinate of the center of the rectangle
	 * @param angle rotation in radians clockwise from top
	 * @param width x axis aligned size of the rectangle
	 * @param height y axis aligned size of the rectangle
	 * @param color paint color
	 */
	public Rectangle( float posX, float posY, float angle, float width, float height, Color color )
	{
		this( posX, posY, angle, width, height, color, true );
	}

	/**
	 * Constructor
	 *
	 * @param posX x coordinate of the center of the rectangle
	 * @param posY y coordinate of the center of the rectangle
	 * @param angle rotation in radians clockwise from top
	 * @param width x axis aligned size of the rectangle
	 * @param height y axis aligned size of the rectangle
	 * @param color paint color
	 * @param filled if true the rectangle will be filled, otherwise only the border is drawn
	 */
	public Rectangle( float posX, float posY, float angle, float width, float height, Color color, boolean filled )
	{
		this.posX = posX;
		this.posY = posY;
		this.angle = angle;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	public float getPosX()
	{
		return posX;
	}

	public void setPosX( float posX )
	{
		this.posX = posX;
	}

	public float getPosY()
	{
		return posY;
	}

	public void setPosY( float posY )
	{
		this.posY = posY;
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
