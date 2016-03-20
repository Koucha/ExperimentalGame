package com.koucha.experimentalgame.effects;

import com.koucha.experimentalgame.BackBone;
import com.koucha.experimentalgame.rendering.Line;
import com.koucha.experimentalgame.rendering.Renderer;

import java.awt.*;

/**
 * Small projectile
 * <p>
 * For more info see {@link Effect}
 */
public class EffectProjectile extends Effect
{
	private float angle;
	private float posX;
	private float posY;
	private int length;
	private int width;
	private float vel;
	private Color color;

	/**
	 * Constructs a EffectProjectile object
	 *
	 * @param posX   x coordinate of source position
	 * @param posY   y coordinate of source position
	 * @param angle  angle of the direction (radians measured from top, clockwise)
	 * @param length length of the projectile
	 * @param width  width of the projectile
	 * @param vel    velocity of the projectile
	 * @param color  color of the projectile
	 */
	public EffectProjectile( float posX, float posY, float angle, int length, int width, float vel, Color color )
	{
		this.angle = angle;
		this.posX = posX;
		this.posY = posY;
		this.length = length;
		this.width = width;
		this.vel = vel;
		this.color = color;
	}

	@Override
	public void update()
	{
		posX -= vel * Math.sin( angle );
		posY += vel * Math.cos( angle );

		if( posX + length < 0 || posY + length < 0 || posX - length > BackBone.INITIAL_WIDTH || posY - length > BackBone.INITIAL_HEIGHT )
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
