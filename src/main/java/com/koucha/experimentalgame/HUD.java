package com.koucha.experimentalgame;

import com.koucha.experimentalgame.entity.Entity;
import com.koucha.experimentalgame.rendering.Color;
import com.koucha.experimentalgame.rendering.Rectangle;
import com.koucha.experimentalgame.rendering.Renderer;
import com.koucha.experimentalgame.rendering.Text;
import org.joml.Vector3f;


/**
 * Heads up display
 * <p>
 * Displays infos as an overlay over the game graphics
 */
public class HUD implements GameObject
{
	public static final int HP_BAR_WIDTH = 200;
	public static final int HP_BAR_HEIGHT = 15;

	private Entity player;
	private GameObjectList handler;

	/**
	 * Constructor
	 *
	 * @param player infos of this player will be displayed
	 */
	public HUD( Entity player )
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
		int posY = 5 * HP_BAR_HEIGHT / 2;
		int posX = 2 * HP_BAR_HEIGHT;

		int hp = 1000;//todo player.getHp();

		hp = (hp * HP_BAR_WIDTH) / 1000; // todo Entity.BASE_HP;

		renderer.render( new Rectangle( new Vector3f( posX + HP_BAR_WIDTH / 2f, posY, 0 ), 0, HP_BAR_WIDTH + 2, HP_BAR_HEIGHT + 2, new Color( 200, 200, 200 ) ) );
		renderer.render( new Rectangle( new Vector3f( posX + (hp) / 2f, posY, -1 ), 0, hp, HP_BAR_HEIGHT, new Color( 0, 200, 0 ) ) );
		renderer.render( new Rectangle( new Vector3f( posX + (hp + HP_BAR_WIDTH) / 2f, posY, -1 ), 0, HP_BAR_WIDTH - hp, HP_BAR_HEIGHT, new Color( 160, 100, 100 ) ) );

		int cd = 0;//todo player.skillCooldown( 1 );
		Color color;
		if( cd == 0 )
		{
			color = new Color( 0, 200, 0 );
		} else
		{
			color = new Color( 160, 100, 100 );
		}
		renderer.render( new Rectangle( new Vector3f( posX + HP_BAR_WIDTH + HP_BAR_HEIGHT * 3f / 2f, posY, 0 ), 0, HP_BAR_HEIGHT + 2, HP_BAR_HEIGHT + 2, new Color( 200, 200, 200 ) ) );
		//noinspection SuspiciousNameCombination
		renderer.render( new Rectangle( new Vector3f( posX + HP_BAR_WIDTH + HP_BAR_HEIGHT * 3f / 2f, posY, -1 ), 0, HP_BAR_HEIGHT, HP_BAR_HEIGHT, color ) );

		renderer.render( new Text( "" + (cd / 60000), 10 + HP_BAR_WIDTH + 10 + HP_BAR_HEIGHT / 2 - 3, 10 + HP_BAR_HEIGHT / 2 + 5, new Color( 200, 200, 200 ) ) );

		cd = 0;//todo player.skillCooldown( 2 );
		if( cd == 0 )
		{
			color = new Color( 0, 200, 0 );
		} else
		{
			color = new Color( 160, 100, 100 );
		}
		renderer.render( new Rectangle( new Vector3f( posX + HP_BAR_WIDTH + HP_BAR_HEIGHT * 7f / 2f, posY, 0 ), 0, HP_BAR_HEIGHT + 2, HP_BAR_HEIGHT + 2, new Color( 200, 200, 200 ) ) );
		//noinspection SuspiciousNameCombination
		renderer.render( new Rectangle( new Vector3f( posX + HP_BAR_WIDTH + HP_BAR_HEIGHT * 7f / 2f, posY, -1 ), 0, HP_BAR_HEIGHT, HP_BAR_HEIGHT, color ) );

		renderer.render( new Text( "" + (cd / 60000), 10 + HP_BAR_WIDTH + 10 + HP_BAR_HEIGHT + 10 + HP_BAR_HEIGHT / 2 - 3, 10 + HP_BAR_HEIGHT / 2 + 5, new Color( 200, 200, 200 ) ) );

	}

	@Override
	public void setHandler( GameObjectList list )
	{
		handler = list;
	}
}
