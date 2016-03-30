package com.koucha.experimentalgame.lwjgl;

import com.koucha.experimentalgame.GraphicsHub;
import com.koucha.experimentalgame.rendering.Renderer;

/**
 * OpenGL binding through GLFW form {@link org.lwjgl}
 */
public class GLFWGraphicsHub implements GraphicsHub
{

	@Override
	public Renderer createRenderer()
	{
		return new GLFWRenderer();
	}
}
