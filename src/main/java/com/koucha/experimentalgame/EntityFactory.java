package com.koucha.experimentalgame;


import com.koucha.experimentalgame.entitySystem.Entity;
import com.koucha.experimentalgame.entitySystem.component.AABB;
import com.koucha.experimentalgame.entitySystem.component.LocalPlayer;
import com.koucha.experimentalgame.entitySystem.component.Position;
import com.koucha.experimentalgame.entitySystem.component.Velocity;
import com.koucha.experimentalgame.input.InputBridge;
import com.koucha.experimentalgame.rendering.Color;
import com.koucha.experimentalgame.rendering.Playah;
import org.joml.Vector3f;

// TODO: 10.04.2016 comment
public class EntityFactory
{
	private EntityFactory()
	{
		// empty
	}

	// TODO: 10.04.2016 move inputBridge to input system
	public static Entity makePlayer( InputBridge inputBridge )
	{
		Entity player = new Entity();

		Position pos = new Position();
		pos.position.set( 0f, 0f, -0.2f );
		player.add( pos );
		player.add( new Velocity() );
		player.add( new AABB() );
		player.add( new Playah( new Vector3f( 0.05f, 0.05f, 0.05f ), new Color( 0.5f, 0.5f, 1f ) ) );
		player.add( new LocalPlayer() );

		return player;
	}
}
