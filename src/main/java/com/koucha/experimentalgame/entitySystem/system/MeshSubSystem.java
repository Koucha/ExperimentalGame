package com.koucha.experimentalgame.entitySystem.system;


import com.koucha.experimentalgame.entitySystem.component.Mesh;
import org.joml.Matrix4f;
import org.joml.Vector3f;

// TODO: 10.04.2016 comment
public final class MeshSubSystem
{
	private MeshSubSystem()
	{
		// empty
	}

	public static Matrix4f getOrientedPosition( Mesh mesh, float alpha )
	{
		Matrix4f mat = new Matrix4f();
		Vector3f vec1 = new Vector3f( mesh.position.position.read() );
		Vector3f vec2 = new Vector3f( mesh.position.position.write() );
		vec1.mul( 1 - alpha );
		vec2.mul( alpha );
		vec1.add( vec2 );
		mat.setTranslation( vec1 );
		return mat;
	}
}
