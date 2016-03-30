package com.koucha.experimentalgame.entitySystem.system;

import com.koucha.experimentalgame.GameObjectList;
import com.koucha.experimentalgame.entitySystem.component.Component;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Camera for viewing 3D scenery
 * <p>
 * The Camera looks at its position from the point (position + offset)
 */
public class Camera implements Component
{
	private static final Vector3f up = new Vector3f( 0, 1, 0 );
	private Vector3f centerPosition = new Vector3f();
	private Vector3f initialOffset = new Vector3f();
	private Vector3f offset = new Vector3f();
	private Vector3f cameraPosition = new Vector3f();
	private Matrix4f cameraMatrix;
	private Matrix4f inverseCameraMatrix;
	private Matrix4f projectionMatrix;

	public Camera( float aspectRatio )
	{
		projectionMatrix = new Matrix4f().perspective( 0.66f, aspectRatio, 0.1f, 100.0f );
		cameraMatrix = new Matrix4f();
		inverseCameraMatrix = new Matrix4f();
		cameraMatrix.mul( projectionMatrix );
		cameraMatrix.invert( inverseCameraMatrix );
	}

	@Override    // not needed by cameras
	public void setHandler( GameObjectList handler )
	{
	}

	/**
	 * @return camera position relative to look at point
	 */
	public Vector3f getOffset()
	{
		return offset;
	}

	/**
	 * set the camera position relative to look at point
	 *
	 * @param offset camera position relative to look at point
	 */
	public void setOffset( Vector3f offset )
	{
		this.initialOffset.set( offset );
		this.offset.set( offset );
		centerPosition.add( offset, cameraPosition );
		updateMatrix();
	}

	private void updateMatrix()
	{
		cameraMatrix.setLookAt( cameraPosition, centerPosition, up );
		projectionMatrix.mul( cameraMatrix, cameraMatrix );
		cameraMatrix.invert( inverseCameraMatrix );
	}

	public Vector3f resetOffset()
	{
		return offset.set( initialOffset );
	}

	/**
	 * @return look at point
	 */
	public Vector3f getPosition()
	{
		return centerPosition;
	}

	/**
	 * set the look at point
	 *
	 * @param position look at point
	 */
	public void setPosition( Vector3f position )
	{
		this.centerPosition = position;
		centerPosition.add( offset, cameraPosition );
		updateMatrix();
	}

	public Matrix4f getViewProjectionMatrix()
	{
		return cameraMatrix;
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
