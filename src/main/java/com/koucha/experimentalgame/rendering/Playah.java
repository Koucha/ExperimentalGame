package com.koucha.experimentalgame.rendering;

import com.koucha.experimentalgame.entitySystem.component.Mesh;
import org.joml.Vector3f;

/**
 * Represents a Playah that can be rendered
 */
public class Playah extends Mesh
{
	private Vector3f size;
	private Color color;

	public Playah( Vector3f size, Color color )
	{
		this.size = size;
		this.color = color;
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
