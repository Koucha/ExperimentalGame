package com.koucha.experimentalgame;

import java.awt.*;

/**
 * Heads up display
 * <p>
 * Displays infos as an overlay over the game graphics
 */
public class HUD implements GameObject
{
	public static final int HP_BAR_WIDTH = 200;
	public static final int HP_BAR_HEIGHT = 15;

	private Character player;
	private GameObjectList handler;

	/**
	 * Constructor
	 *
	 * @param player infos of this player will be displayed
	 */
	public HUD( Character player )
	{
		this.player = player;
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

		hp = (hp * HP_BAR_WIDTH) / Character.BASE_HP;

		g.setColor( new Color( 0, 200, 0, 128 ) );
		g.fillRect( 10, 10, hp, HP_BAR_HEIGHT );
		g.setColor( new Color( 160, 100, 100, 128 ) );
		g.fillRect( 10 + hp, 10, HP_BAR_WIDTH - hp, HP_BAR_HEIGHT );
		g.setColor( new Color( 200, 200, 200, 128 ) );
		g.drawRect( 10, 10, HP_BAR_WIDTH - 1, HP_BAR_HEIGHT - 1 );

		int cd = player.skillCooldown( 1 );
		if( cd == 0 )
		{
			g.setColor( new Color( 0, 200, 0, 128 ) );
		} else
		{
			g.setColor( new Color( 160, 100, 100, 128 ) );
		}
		//noinspection SuspiciousNameCombination
		g.fillRect( 10 + HP_BAR_WIDTH + 10, 10, HP_BAR_HEIGHT, HP_BAR_HEIGHT );
		g.drawString( "" + (cd / 60000), 10 + HP_BAR_WIDTH + 10 + HP_BAR_HEIGHT / 2 - 3, 10 + HP_BAR_HEIGHT / 2 + 5 );

		cd = player.skillCooldown( 2 );
		if( cd == 0 )
		{
			g.setColor( new Color( 0, 200, 0, 128 ) );
		} else
		{
			g.setColor( new Color( 160, 100, 100, 128 ) );
		}
		//noinspection SuspiciousNameCombination
		g.fillRect( 10 + HP_BAR_WIDTH + 10 + HP_BAR_HEIGHT + 10, 10, HP_BAR_HEIGHT, HP_BAR_HEIGHT );
		g.drawString( "" + (cd / 60000), 10 + HP_BAR_WIDTH + 10 + HP_BAR_HEIGHT + 10 + HP_BAR_HEIGHT / 2 - 3, 10 + HP_BAR_HEIGHT / 2 + 5 );

		g.setColor( new Color( 200, 200, 200, 128 ) );
		g.drawRect( 10 + HP_BAR_WIDTH + 10, 10, HP_BAR_HEIGHT - 1, HP_BAR_HEIGHT - 1 );
		g.drawRect( 10 + HP_BAR_WIDTH + 10 + HP_BAR_HEIGHT + 10, 10, HP_BAR_HEIGHT - 1, HP_BAR_HEIGHT - 1 );

	}

	@Override
	public void setHandler( GameObjectList list )
	{
		handler = list;
	}
}
