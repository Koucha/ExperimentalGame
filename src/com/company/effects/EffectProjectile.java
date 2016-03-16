package com.company.effects;

import com.company.Game;

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
	public void tick()
	{
		posX -= vel * Math.sin( angle );
		posY += vel * Math.cos( angle );

		if( posX + length < 0 || posY + length < 0 || posX - length > Game.WIDTH || posY - length > Game.HEIGHT )
		{
			endEffect();
		}
	}

	@SuppressWarnings( "Duplicates" )
	@Override
	public void render( Graphics g )
	{
		Graphics2D g2 = (Graphics2D) g;

		Stroke backup = g2.getStroke();
		g2.setStroke( new BasicStroke( width ) );
		g2.setColor( color );
		g2.drawLine( (int) posX, (int) posY, (int) (posX - length * Math.sin( angle )), (int) (posY + length * Math.cos( angle )) );
		g2.setStroke( backup );
	}
}
