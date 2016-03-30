package com.koucha.experimentalgame.entitySystem.component;

import com.koucha.experimentalgame.entitySystem.Component;
import com.koucha.experimentalgame.entitySystem.ComponentFlag;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Contains focus point, distance from that point and direction from the point to the camera
 */
public class AttachedCamera implements Component
{
	/** look at point */
	public Vector3f focusPoint = new Vector3f();
	/** distance between camera and focusPoint */
	public float distance = 1;
	/** direction from focus point to camera */
	public Quaternionf direction = new Quaternionf();

	public AttachedCamera()
	{
		//empty (like your hopes)
	}

	@Override
	public ComponentFlag getFlag()
	{
		return ComponentFlag.AttachedCamera;
	}
}
