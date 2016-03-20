package com.koucha.experimentalgame;

import com.koucha.experimentalgame.rendering.Rectangle;
import com.koucha.experimentalgame.rendering.Renderer;
import com.koucha.experimentalgame.rendering.Text;

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
	public void update()
	{
		//nothing
	}

	@Override
	public void render( Renderer renderer )
	{
		int hp = player.getHp();

		hp = (hp * HP_BAR_WIDTH) / Character.BASE_HP;

		renderer.render( new Rectangle( 10, 10, 0, hp, HP_BAR_HEIGHT, new Color( 0, 200, 0, 128 ) ) );
		renderer.render( new Rectangle( 10 + hp, 10, 0, HP_BAR_WIDTH - hp, HP_BAR_HEIGHT, new Color( 160, 100, 100, 128 ) ) );
		renderer.render( new Rectangle( 10, 10, 0, HP_BAR_WIDTH - 1, HP_BAR_HEIGHT - 1, new Color( 200, 200, 200, 128 ), false ) );

		int cd = player.skillCooldown( 1 );
		Color color;
		if( cd == 0 )
		{
			color = new Color( 0, 200, 0, 128 );
		} else
		{
			color = new Color( 160, 100, 100, 128 );
		}
		//noinspection SuspiciousNameCombination
		renderer.render( new Rectangle( 10 + HP_BAR_WIDTH + 10, 10, 0, HP_BAR_HEIGHT, HP_BAR_HEIGHT, color ) );

		color = new Color( 200, 200, 200, 128 );
		renderer.render( new Text( "" + (cd / 60000), 10 + HP_BAR_WIDTH + 10 + HP_BAR_HEIGHT / 2 - 3, 10 + HP_BAR_HEIGHT / 2 + 5, color ) );

		cd = player.skillCooldown( 2 );
		if( cd == 0 )
		{
			color = new Color( 0, 200, 0, 128 );
		} else
		{
			color = new Color( 160, 100, 100, 128 );
		}
		//noinspection SuspiciousNameCombination
		renderer.render( new Rectangle( 10 + HP_BAR_WIDTH + 10 + HP_BAR_HEIGHT + 10, 10f, 0, HP_BAR_HEIGHT, HP_BAR_HEIGHT, color ) );

		color = new Color( 200, 200, 200, 128 );
		renderer.render( new Text( "" + (cd / 60000), 10 + HP_BAR_WIDTH + 10 + HP_BAR_HEIGHT + 10 + HP_BAR_HEIGHT / 2 - 3, 10 + HP_BAR_HEIGHT / 2 + 5, color ) );
		renderer.render( new Rectangle( 10 + HP_BAR_WIDTH + 10, 10, 0, HP_BAR_HEIGHT - 1, HP_BAR_HEIGHT - 1, color, false ) );
		renderer.render( new Rectangle( 10 + HP_BAR_WIDTH + 10 + HP_BAR_HEIGHT + 10, 10, 0, HP_BAR_HEIGHT - 1, HP_BAR_HEIGHT - 1, color, false ) );

	}

	@Override
	public void setHandler( GameObjectList list )
	{
		handler = list;
	}
}
