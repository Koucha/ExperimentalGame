package com.koucha.experimentalgame;

import com.koucha.experimentalgame.rendering.Renderer;

/**
 * GraphicsHubs implement all graphics specific functions
 * <p>
 * Switch GraphicsHub to switch between graphics implementations
 */
public interface GraphicsHub
{

	Renderer createRenderer();
}
