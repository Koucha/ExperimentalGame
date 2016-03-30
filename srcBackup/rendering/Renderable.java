package com.koucha.experimentalgame.rendering;

/**
 * Can be rendered by a {@link Renderer}
 */
public interface Renderable
{
	/**
	 * forwards the render data of this object to the {@link Renderer}
	 *
	 * @param renderer renderer to render the data of this object
	 */
	void render( Renderer renderer );
}
