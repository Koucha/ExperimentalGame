package com.koucha.experimentalgame.rendering;

import com.koucha.experimentalgame.input.InputBridge;

/**
 * Has to accept a {@link Renderable} object
 */
public interface Renderer
{
	/**
	 * Renders the given {@link Renderable}
	 *
	 * @param renderable is rendered
	 */
	void render( Renderable renderable );

	void createWindow( int initialWidth, int initialHeight, String s );

	void setInputBridge( InputBridge inputBridge );

	void initializeUpdateIteration();

	void initializeRenderIteration();

	void finishRenderIteration();
}
