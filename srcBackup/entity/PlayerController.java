package com.koucha.experimentalgame.entitySystem.system;

import com.koucha.experimentalgame.Action;
import com.koucha.experimentalgame.Util;
import com.koucha.experimentalgame.entitySystem.component.Entity;
import com.koucha.experimentalgame.input.InputBridge;
import com.koucha.experimentalgame.input.InputEvent;
import com.koucha.experimentalgame.input.InputMap;
import com.sun.glass.events.KeyEvent;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Receives mouse and keyboard input and generates instructions for the player {@link Entity}
 */
public class PlayerController implements Controller
{
	private final InputBridge inputBridge;
	private Position position;
	private int bitmask = 0;
	private Camera camera;

	/**
	 * Constructor
	 *
	 * @param inputBridge take input from this bridge
	 * @param position position that can be altered by this controller
	 * @param camera camera that can be moved by this controller
	 */
	public PlayerController( InputBridge inputBridge, Position position, Camera camera )
	{
		this.position = position;
		this.camera = camera;
		this.inputBridge = inputBridge;

		InputMap inputMap = inputBridge.getInputMap();

		inputMap.addLink( "Forward", KeyEvent.VK_W, ( e ) -> handleEvent( e, Selector.up ) );
		inputMap.addLink( "Backward", KeyEvent.VK_S, ( e ) -> handleEvent( e, Selector.down ) );
		inputMap.addLink( "Turn Left", KeyEvent.VK_A, ( e ) -> handleEvent( e, Selector.left ) );
		inputMap.addLink( "Turn Right", KeyEvent.VK_D, ( e ) -> handleEvent( e, Selector.right ) );
		inputMap.addLink( "Skill 1", KeyEvent.VK_SPACE, ( e ) -> handleEvent( e, Selector.skill1 ) );
		inputMap.addLink( "Skill 2", KeyEvent.VK_F, ( e ) -> handleEvent( e, Selector.skill2 ) );

	}

	/**
	 * Handles the {@link InputEvent}s received through the {@link InputMap}
	 *
	 * @param evt key event
	 * @param bit selector specifying the action to be performed (see {@link Selector})
	 */
	private void handleEvent( InputEvent evt, Selector bit )
	{
		try
		{
			if( isPressed( evt ) )
			{
				bitmask = bitmask | bit.value();
			} else
			{
				bitmask = bitmask & ~(bit.value());
			}
		} catch( InvalidStateException ex )
		{
			// Do nothing
		}
	}

	/**
	 * Determine if the event was caused by a press or release action
	 *
	 * @param evt key event
	 * @return true if the key was pressed, false if it was released
	 * @throws InvalidStateException if the key was neither pressed nor released
	 */
	private boolean isPressed( InputEvent evt ) throws InvalidStateException
	{
		switch( evt.type )
		{
			case pressed:
				return true;
			case released:
				return false;
			default:
				throw new InvalidStateException();
		}
	}

	@Override
	public void update()
	{
		Action action = getAction();

		Vector3f right = camera.getXAxis();
		Vector3f forward = new Vector3f( 0, 1, 0 );
		forward.cross( right, forward );
		right.mul( action.right );
		forward.mul( action.forward );

		forward.add( right ).mul( 0.05f );

		Vector3f pos = new Vector3f();
		Vector3f zAxis = new Vector3f( 0, 0, 1 );

		Quaternionf dir = new Quaternionf();
		if( forward.lengthSquared() != 0 )
			dir.rotationTo( zAxis, forward );
		else
		{
			dir.rotationTo( zAxis, position.getOrientedPosition().transformDirection( zAxis, pos ) );
		}

		if( !Float.isFinite( dir.lengthSquared() ) )
		{
			dir.rotationY( (float) Math.PI );
		}

		pos = position.getOrientedPosition().transformPosition( pos.zero() );
		pos.add( position.getVelocity() );

		position.setPosition( pos );
		position.setOrientation( dir );
		position.setVelocity( forward );

		camera.setPosition( new Vector3f( pos ) );
		camera.resetOffset().rotate( new Quaternionf().rotationY( -inputBridge.getMouseX() / 1000f )
				.rotateX( Util.clamp( -inputBridge.getMouseY() / 1000f, -1.5f, 0.4f ) ) );
	}

	@Override
	public Action getAction()
	{
		Action action = new Action();

		if( testMask( Selector.right ) )
		{
			action.right = 1;
		} else if( testMask( Selector.left ) )
		{
			action.right = -1;
		}

		if( testMask( Selector.up ) )
		{
			action.forward = 1;
		} else if( testMask( Selector.down ) )
		{
			action.forward = -1;
		}

		if( action.forward != 0 && action.right != 0 )
		{
			action.forward *= 0.70710678f;
			action.right *= 0.70710678f;
		}

		if( testMask( Selector.skill1 ) )
		{
			action.useSkill = true;
			action.skillNr = 1;
		}

		if( testMask( Selector.skill2 ) )
		{
			action.useSkill = true;
			action.skillNr = 2;
		}

		return action;
	}

	/**
	 * Tests if the bit of the getMask specified by the selector is set
	 *
	 * @param bit selector (see {@link Selector}
	 * @return true if the bit was set, false otherwise
	 */
	private boolean testMask( Selector bit )
	{
		return (bitmask & bit.value()) != 0;
	}

	/**
	 * Associates actions to flags of a bit getMask
	 */
	private enum Selector
	{
		up( 0x0000001 ),
		down( 0x0000002 ),
		right( 0x0000004 ),
		left( 0x0000008 ),
		skill1( 0x0000010 ),
		skill2( 0x0000020 ),
		skill3( 0x0000040 ),
		skill4( 0x0000080 ),
		skill5( 0x0000100 ),
		skill6( 0x0000200 ),
		skill7( 0x0000400 ),
		skill8( 0x0000800 );

		private final int val;

		Selector( int val )
		{
			this.val = val;
		}

		/**
		 * @return getMask bit
		 */
		public int value()
		{
			return val;
		}
	}

	/**
	 * Is thrown if a key event performed an unknown action
	 */
	public class InvalidStateException extends Exception
	{
	}
}
