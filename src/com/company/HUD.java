package com.company;

import java.awt.*;

public class HUD implements GameObject
{
	public static final int HPBAR_WIDTH = 200;
	public static final int HPBAR_HEIGHT = 15;

	private Character player;
	private GameObjectList handler;

	public HUD( Character player )
	{
		this.player = player;
	}

	@Override
	public void setHandler( GameObjectList list )
	{
		handler = list;
	}

	@Override
	public void tick()
	{
		//nothing
	}

	@Override
	public void render( Graphics g )
	{
		int hp = player.getHp();

		hp = (hp * HPBAR_WIDTH) / Character.BASE_HP;

		g.setColor( new Color( 0, 200, 0, 128 ) );
		g.fillRect( 10, 10, hp, HPBAR_HEIGHT );
		g.setColor( new Color( 160, 100, 100, 128 ) );
		g.fillRect( 10 + hp, 10, HPBAR_WIDTH - hp, HPBAR_HEIGHT );
		g.setColor( new Color( 200, 200, 200, 128 ) );
		g.drawRect( 10, 10, HPBAR_WIDTH - 1, HPBAR_HEIGHT - 1 );

		int cd = player.skillCooldown( 1 );
		if( cd == 0 )
		{
			g.setColor( new Color( 0, 200, 0, 128 ) );
		} else
		{
			g.setColor( new Color( 160, 100, 100, 128 ) );
		}
		g.fillRect( 10 + HPBAR_WIDTH + 10, 10, HPBAR_HEIGHT, HPBAR_HEIGHT );
		g.drawString( "" + (cd / 60000), 10 + HPBAR_WIDTH + 10 + HPBAR_HEIGHT / 2 - 3, 10 + HPBAR_HEIGHT / 2 + 5 );

		cd = player.skillCooldown( 2 );
		if( cd == 0 )
		{
			g.setColor( new Color( 0, 200, 0, 128 ) );
		} else
		{
			g.setColor( new Color( 160, 100, 100, 128 ) );
		}
		g.fillRect( 10 + HPBAR_WIDTH + 10 + HPBAR_HEIGHT + 10, 10, HPBAR_HEIGHT, HPBAR_HEIGHT );
		g.drawString( "" + (cd / 60000), 10 + HPBAR_WIDTH + 10 + HPBAR_HEIGHT + 10 + HPBAR_HEIGHT / 2 - 3, 10 + HPBAR_HEIGHT / 2 + 5 );

		g.setColor( new Color( 200, 200, 200, 128 ) );
		g.drawRect( 10 + HPBAR_WIDTH + 10, 10, HPBAR_HEIGHT - 1, HPBAR_HEIGHT - 1 );
		g.drawRect( 10 + HPBAR_WIDTH + 10 + HPBAR_HEIGHT + 10, 10, HPBAR_HEIGHT - 1, HPBAR_HEIGHT - 1 );

	}
}
