package com.koucha.experimentalgame.lwjgl;

import org.joml.Matrix4f;

/**
 * Camera with ortographic projection (for GUI etc)
 */
public class OrthoCam
{
	private Matrix4f matrix;

	public OrthoCam( int left, int right, int bottom, int top )
	{
		matrix = new Matrix4f();
		setFrame( left, right, bottom, top );
	}

	public void setFrame( int left, int right, int bottom, int top )
	{
		matrix.ortho( left, right, bottom, top, 100, -100 );
	}

	public Matrix4f getViewProjectionMatrix()
	{
		return matrix;
	}
}
