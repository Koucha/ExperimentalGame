package com.koucha.experimentalgame.entitySystem.system;


import com.koucha.experimentalgame.entitySystem.component.AttachedCamera;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Provides utility functions specific to {@link AttachedCamera}
 */
public final class AttachedCameraSubSystem
{
	private AttachedCameraSubSystem()
	{
		//empty
	}

	public static void setFocusPoint( AttachedCamera camera, float x, float y, float z )
	{
		camera.focusPoint.set( x, y, z );
	}

	/**
	 * @param camera AttachedCamera to be changed
	 * @param topAngle angle between the line of sight and the up vector at the focusPoint (look at point)
	 * @param turnAngle signed angle of the rotation around the up vector centered at the focusPoint (look at point)
	 */
	public static void setDirection( AttachedCamera camera, float topAngle, float turnAngle )
	{
		topAngle = (topAngle < ((float) Math.PI - 0.1f)) ? ((topAngle > 0.1f) ? (topAngle) : (0.1f)) : ((float) Math.PI - 0.1f);
		camera.direction.rotationY( turnAngle ).rotateX( topAngle );
	}

	public static Vector3f getFocusPoint( AttachedCamera camera )
	{
		return camera.focusPoint;
	}

	public static void setFocusPoint( AttachedCamera camera, Vector3f focusPoint )
	{
		camera.focusPoint.set( focusPoint );
	}

	public static float getDistance( AttachedCamera camera )
	{
		return camera.distance;
	}

	public static void setDistance( AttachedCamera camera, float distance )
	{
		camera.distance = distance;
	}

	public static Quaternionf getDirection( AttachedCamera camera )
	{
		return camera.direction;
	}

	public static void setDirection( AttachedCamera camera, Quaternionf direction )
	{
		camera.direction.set( direction );
	}

	/**
	 * Calculate the ProjectionCamera matrix of a AttachedCamera
	 *
	 * @param camera AttachedCamera to be converted into a matrix
	 * @return calculated ProjectionCamera matrix
	 */
	public static Matrix4f getCameraMatrix( AttachedCamera camera )
	{
		return getCameraMatrix( camera, new Matrix4f() );
	}

	/**
	 * Calculate the ProjectionCamera matrix of a AttachedCamera
	 *
	 * @param camera AttachedCamera to be converted into a matrix
	 * @param dest the calculated matrix is stored in this object
	 * @return dest with calculated matrix inside
	 */
	public static Matrix4f getCameraMatrix( AttachedCamera camera, Matrix4f dest )
	{
		return dest.setLookAt( camera.direction
						.transform( new Vector3f( 0, camera.distance, 0 ) )
						.add( camera.focusPoint ),
				camera.focusPoint,
				new Vector3f( 0, 1, 0 ) );
	}
}
