package com.koucha.experimentalgame.rendering;

import java.nio.FloatBuffer;

public class Color
{
	private float r;
	private float g;
	private float b;
	private float a;

	/**
	 * Fully opaque color
	 *
	 * @param r [0, 255]
	 * @param g [0, 255]
	 * @param b [0, 255]
	 */
	public Color( int r, int g, int b )
	{
		this.r = r / 255f;
		this.g = g / 255f;
		this.b = b / 255f;
		this.a = 1f;
	}

	/**
	 * Color with variable opacity (a = 255 : fully opaque)
	 *
	 * @param r [0, 255]
	 * @param g [0, 255]
	 * @param b [0, 255]
	 * @param a [0, 255]
	 */
	public Color( int r, int g, int b, int a )
	{
		this.r = r / 255f;
		this.g = g / 255f;
		this.b = b / 255f;
		this.a = a / 255f;
	}

	/**
	 * Fully opaque color
	 *
	 * @param r [0.0f, 1.0f]
	 * @param g [0.0f, 1.0f]
	 * @param b [0.0f, 1.0f]
	 */
	public Color( float r, float g, float b )
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1f;
	}

	/**
	 * Color with variable opacity (a = 1.0f : fully opaque)
	 *
	 * @param r [0.0f, 1.0f]
	 * @param g [0.0f, 1.0f]
	 * @param b [0.0f, 1.0f]
	 * @param a [0.0f, 1.0f]
	 */
	public Color( float r, float g, float b, float a )
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	// --- As Integer ---

	/**
	 * @return [0, 255]
	 */
	public int getRedI()
	{
		return (int) (r * 255);
	}

	/**
	 * @param r [0, 255]
	 */
	public void setRed( int r )
	{
		this.r = r / 255f;
	}

	/**
	 * @return [0, 255]
	 */
	public int getGreenI()
	{
		return (int) (g * 255);
	}

	/**
	 * @param g [0, 255]
	 */
	public void setGreen( int g )
	{
		this.g = g / 255f;
	}

	/**
	 * @return [0, 255]
	 */
	public int getBlueI()
	{
		return (int) (b * 255);
	}

	/**
	 * @param b [0, 255]
	 */
	public void setBlue( int b )
	{
		this.b = b / 255f;
	}

	/**
	 * @return [0, 255]
	 */
	public int getAlphaI()
	{
		return (int) (a * 255);
	}

	/**
	 * fully opaque when a = 255
	 *
	 * @param a [0, 255]
	 */
	public void setAlpha( int a )
	{
		this.a = a / 255f;
	}


	// --- As Floats ---

	/**
	 * @return [0.0f, 1.0f]
	 */
	public float getRed()
	{
		return r;
	}

	/**
	 * @param r [0.0f, 1.0f]
	 */
	public void setRed( float r )
	{
		this.r = r;
	}

	/**
	 * @return [0.0f, 1.0f]
	 */
	public float getGreen()
	{
		return g;
	}

	/**
	 * @param g [0.0f, 1.0f]
	 */
	public void setGreen( float g )
	{
		this.g = g;
	}

	/**
	 * @return [0.0f, 1.0f]
	 */
	public float getBlue()
	{
		return b;
	}

	/**
	 * @param b [0.0f, 1.0f]
	 */
	public void setBlue( float b )
	{
		this.b = b;
	}

	/**
	 * @return [0.0f, 1.0f]
	 */
	public float getAlpha()
	{
		return a;
	}

	/**
	 * fully opaque when a = 1.0f
	 *
	 * @param a [0.0f, 1.0f]
	 */
	public void setAlpha( float a )
	{
		this.a = a;
	}

	/**
	 * Store this color into the supplied {@link FloatBuffer} at the current
	 * buffer {@link FloatBuffer#position() position}.
	 * <p>
	 * This method will not increment the position of the given FloatBuffer.
	 * <p>
	 * In order to specify the offset into the FloatBuffer at which
	 * the color is stored, use {@link #get(int, FloatBuffer)}, taking
	 * the absolute position as parameter.
	 *
	 * @param buffer will receive the values of this color in <tt>r, g, b, a</tt> order
	 * @return the passed in buffer
	 * @see #get(int, FloatBuffer)
	 * @see #get(int, FloatBuffer)
	 */
	public FloatBuffer get( FloatBuffer buffer )
	{
		return get( buffer.position(), buffer );
	}

	/**
	 * Store this color into the supplied {@link FloatBuffer} at the current
	 * buffer {@link FloatBuffer#position() position}.
	 * <p>
	 * This method will not increment the position of the given FloatBuffer.
	 *
	 * @param index  the absolute position into the FloatBuffer
	 * @param buffer will receive the values of this color in <tt>r, g, b, a</tt> order
	 * @return the passed in buffer
	 */
	public FloatBuffer get( int index, FloatBuffer buffer )
	{
		buffer.put( index, r );
		buffer.put( index + 1, g );
		buffer.put( index + 2, b );
		buffer.put( index + 3, a );
		return buffer;
	}
}
