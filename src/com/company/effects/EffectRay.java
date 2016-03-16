package com.company.effects;

import java.awt.*;

public class EffectRay extends Effect
{
	private float angle;
	private float posx;
	private float posy;
	private float length;
	private int width;
	private float vel;
	private Color color;
	private int lifeCycles;

	public EffectRay( float posx, float posy, float angle, int length, int width, float vel, int lifeCycles, Color color )
	{
		this.angle = angle;
		this.posx = posx;
		this.posy = posy;
		this.length = length;
		this.width = width;
		this.vel = vel;
		this.lifeCycles = lifeCycles;
		this.color = color;
	}

	@Override
	public void tick()
	{
		lifeCycles--;

		length++;
		posx -= vel * Math.sin( angle );
		posy += vel * Math.cos( angle );

		if( lifeCycles < 0 )
		{
			endEffect();
		}
	}

	@Override
	public void render( Graphics g )
	{
		Graphics2D g2 = (Graphics2D) g;

		Stroke backup = g2.getStroke();
		g2.setStroke( new BasicStroke( width ) );
		g2.setColor( color );
		g2.drawLine( (int) posx, (int) posy, (int) (posx - length * Math.sin( angle )), (int) (posy + length * Math.cos( angle )) );
		g2.setStroke( backup );
	}
}
