package com.koucha.experimentalgame.rendering;

import com.koucha.experimentalgame.system.Mesh;

/**
 * Represents a Text that can be rendered
 */
public class Text implements Mesh
{
	/**
	 * Position
	 */
	private float posX, posY;

	/**
	 * Rotation angle in radians clockwise from top
	 */
	private float angle;

	private Color color;

	private String text;

	/**
	 * Constructor
	 *
	 * @param text text to be displayed
	 * @param posX x coordinate of the position of the Text
	 * @param posY y coordinate of the position of the Text
	 * @param color text color
	 */
	public Text( String text, float posX, float posY, Color color )
	{
		this( text, posX, posY, 0, color );
	}

	/**
	 * Constructor
	 *
	 * @param text text to be displayed
	 * @param posX x coordinate of the position of the Text
	 * @param posY y coordinate of the position of the Text
	 * @param angle angle of the up direction in radians clockwise from the top
	 * @param color text color
	 */
	public Text( String text, float posX, float posY, float angle, Color color )
	{
		this.posX = posX;
		this.posY = posY;
		this.angle = angle;
		this.color = color;
		this.text = text;
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

	public Color getColor()
	{
		return color;
	}

	public void setColor( Color color )
	{
		this.color = color;
	}

	public String getText()
	{
		return text;
	}

	public void setText( String text )
	{
		this.text = text;
	}

	@Override
	public void render( Renderer renderer )
	{

	}
}
