package com.koucha.experimentalgame.effects;

import com.koucha.experimentalgame.rendering.Color;
import com.koucha.experimentalgame.rendering.Line;
import com.koucha.experimentalgame.rendering.Renderer;


/**
 * Fast Ray
 * <p>
 * For more info see {@link Effect}
 */
public class EffectRay extends Effect
{
	private float angle;
	private float posX;
	private float posY;
	private float length;
	private int width;
	private float vel;
	private Color color;
	private int lifeCycles;

	/**
	 * Constructs a EffectRay object
	 *
	 * @param posX       x coordinate of source position
	 * @param posY       y coordinate of source position
	 * @param angle      angle of the direction (radians measured from top, clockwise)
	 * @param length     initial length of the ray (it will grow rapidly)
	 * @param width      width of the ray
	 * @param vel        velocity of the beginning of the ray
	 * @param lifeCycles number of cycles the ray should be displayed
	 * @param color      color of the ray
	 */
	public EffectRay( float posX, float posY, float angle, int length, int width, float vel, int lifeCycles, Color color )
	{
		this.angle = angle;
		this.posX = posX;
		this.posY = posY;
		this.length = length;
		this.width = width;
		this.vel = vel;
		this.lifeCycles = lifeCycles;
		this.color = color;
	}

	@Override
	public void update()
	{
		lifeCycles--;

		length += 100;
		posX -= vel * Math.sin( angle );
		posY += vel * Math.cos( angle );

		if( lifeCycles < 0 )
		{
			endEffect();
		}
	}

	@SuppressWarnings( "Duplicates" )
	@Override
	public void render( Renderer renderer )
	{
		renderer.render( new Line( posX, posY, posX - length * (float) Math.sin( angle ), posY + length * (float) Math.cos( angle ), width, color ) );
	}
}
