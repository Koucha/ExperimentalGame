package com.koucha.experimentalgame.rendering;

import com.koucha.experimentalgame.entity.Mesh;
import com.koucha.experimentalgame.entity.Position;
import org.joml.Vector3f;

/**
 * Represents a Cube that can be rendered
 */
public class Cube implements Mesh
{
	private Vector3f size;
	private Position position;
	private Color color;

	public Cube( Vector3f size, Position position, Color color )
	{
		this.size = size;
		this.position = position;
		this.color = color;
	}

	@Override
	public void render( Renderer renderer )
	{
		renderer.render( this );
	}

	public Position getPosition()
	{
		return position;
	}

	public Vector3f getSize()
	{
		return size;
	}

	public Color getColor()
	{
		return color;
	}
}
