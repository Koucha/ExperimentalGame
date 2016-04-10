package com.koucha.experimentalgame.entitySystem.system;


import com.koucha.experimentalgame.entitySystem.component.Mesh;
import org.joml.Matrix4f;

// TODO: 10.04.2016 comment
public final class MeshSubSystem
{
	private MeshSubSystem()
	{
		// empty
	}

	public static Matrix4f getOrientedPosition( Mesh mesh )
	{
		Matrix4f mat = new Matrix4f();
		mat.setTranslation( mesh.position.position );
		return mat;
	}
}
