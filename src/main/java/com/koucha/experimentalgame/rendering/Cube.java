package com.koucha.experimentalgame.rendering;

import com.koucha.experimentalgame.entitySystem.component.Mesh;
import com.koucha.experimentalgame.entitySystem.component.Position;
import org.joml.Vector3f;

/**
 * Represents a Cube that can be rendered
 */
public class Cube extends Mesh
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
