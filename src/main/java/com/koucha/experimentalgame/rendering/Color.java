package com.koucha.experimentalgame.rendering;

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
}
