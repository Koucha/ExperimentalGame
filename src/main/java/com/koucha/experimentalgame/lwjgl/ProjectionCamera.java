package com.koucha.experimentalgame.lwjgl;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Camera for viewing 3D scenery
 */
public class ProjectionCamera
{
	private Matrix4f cameraMatrix;
	private Matrix4f inverseCameraMatrix;
	private Matrix4f pVMatrix;
	private Matrix4f inversePVMatrix;
	private Matrix4f projectionMatrix;

	public ProjectionCamera( float aspectRatio )
	{
		projectionMatrix = new Matrix4f().perspective( 0.66f, aspectRatio, 0.1f, 100.0f );
		cameraMatrix = new Matrix4f();
		inverseCameraMatrix = new Matrix4f();
		pVMatrix = new Matrix4f();
		inversePVMatrix = new Matrix4f();
		pVMatrix.mul( projectionMatrix );
		pVMatrix.invert( inversePVMatrix );
	}

	public Matrix4f getViewProjectionMatrix()
	{
		return pVMatrix;
	}

	public Matrix4f getInverseViewProjectionMatrix()
	{
		return inversePVMatrix;
	}

	public Matrix4f getCameraMatrix()
	{
		return cameraMatrix;
	}

	public void setCameraMatrix( Matrix4f cameraMatrix )
	{
		this.cameraMatrix.set( cameraMatrix );
		updateMatrix();
	}

	private void updateMatrix()
	{
		pVMatrix.set( cameraMatrix );
		projectionMatrix.mul( pVMatrix, pVMatrix );
		pVMatrix.invert( inversePVMatrix );
		cameraMatrix.invert( inverseCameraMatrix );
	}

	public Matrix4f getInverseCameraMatrix()
	{
		return inverseCameraMatrix;
	}

	public void setAspectRatio( float aspectRatio )
	{
		projectionMatrix.perspective( 0.66f, aspectRatio, 0.1f, 100.0f );
		updateMatrix();
	}

	/**
	 * @return the Cameras x axis in world coordinates
	 */
	public Vector3f getXAxis()
	{
		Vector3f xAxis = new Vector3f( 1, 0, 0 );

		inverseCameraMatrix.transformDirection( xAxis );

		return xAxis;
	}
}
