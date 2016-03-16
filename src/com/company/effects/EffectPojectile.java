package com.company.effects;

import com.company.Game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class EffectPojectile extends Effect
{
	private float angle;
	private float posx;
	private float posy;
	private int length;
	private int width;
	private float vel;
	private Color color;

	public EffectPojectile( float posx, float posy, float angle, int length, int width, float vel, Color color )
	{
		this.angle = angle;
		this.posx = posx;
		this.posy = posy;
		this.length = length;
		this.width = width;
		this.vel = vel;
		this.color = color;
	}

	@Override
	public void tick()
	{
		posx -= vel * Math.sin( angle );
		posy += vel * Math.cos( angle );

		if( posx + length < 0 || posy + length < 0 || posx - length > Game.WIDTH || posy - length > Game.HEIGHT )
		{
			terminate();
		}
	}

	@Override
	public void render( Graphics g )
	{
		Graphics2D g2 = (Graphics2D) g;

		Stroke backup = g2.getStroke();
		g2.setStroke( new BasicStroke( width ) );
		g2.setColor( color );
		g2.drawLine( (int)posx, (int)posy, (int)(posx - length * Math.sin( angle )), (int)(posy + length * Math.cos( angle )) );
		g2.setStroke( backup );
	}
}
