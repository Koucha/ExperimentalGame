package com.koucha.experimentalgame.rendering;

import com.koucha.experimentalgame.entitySystem.component.Mesh;

/**
 * Represents a Line that can be rendered
 */
public class Line extends Mesh
{
	/**
	 * Starting point
	 */
	private float startX, startY;

	/**
	 * Ending point
	 */
	private float endX, endY;

	/**
	 * Line width
	 */
	private float width;

	private Color color;

	/**
	 * Creates a Line that goes from (startX, startY) to (endX, endY)
	 *
	 * @param startX x coordinate of the beginning of the line
	 * @param startY y coordinate of the beginning of the line
	 * @param endX   x coordinate of the ending of the line
	 * @param endY   y coordinate of the ending of the line
	 * @param width  width of the line
	 * @param color  line color
	 */
	public Line( float startX, float startY, float endX, float endY, float width, Color color )
	{
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.width = width;
		this.color = color;
	}

	public float getStartX()
	{
		return startX;
	}

	public void setStartX( float startX )
	{
		this.startX = startX;
	}

	public float getStartY()
	{
		return startY;
	}

	public void setStartY( float startY )
	{
		this.startY = startY;
	}

	public float getEndX()
	{
		return endX;
	}

	public void setEndX( float endX )
	{
		this.endX = endX;
	}

	public float getEndY()
	{
		return endY;
	}

	public void setEndY( float endY )
	{
		this.endY = endY;
	}

	public float getWidth()
	{
		return width;
	}

	public void setWidth( float width )
	{
		this.width = width;
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
