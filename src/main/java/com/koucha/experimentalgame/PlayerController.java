package com.koucha.experimentalgame;

import com.koucha.experimentalgame.input.InputEvent;
import com.koucha.experimentalgame.input.InputMap;
import com.sun.glass.events.KeyEvent;

/**
 * Receives mouse and keyboard input and generates instructions for the player {@link Entity}
 */
public class PlayerController implements Controller
{
	private int bitmask = 0;

	/**
	 * Constructor
	 *
	 * @param inputMap links between keys and actions are saved into this map
	 */
	public PlayerController( InputMap inputMap )
	{
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
	public Action getAction()
	{
		Action action = new Action();

		if( testMask( Selector.right ) )
		{
			action.angle = 1;
		} else if( testMask( Selector.left ) )
		{
			action.angle = -1;
		}

		if( testMask( Selector.up ) )
		{
			action.vel = 1;
		} else if( testMask( Selector.down ) )
		{
			action.vel = -1;
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
	 * Tests if the bit of the mask specified by the selector is set
	 *
	 * @param bit selector (see {@link Selector}
	 * @return true if the bit was set, false otherwise
	 */
	private boolean testMask( Selector bit )
	{
		return (bitmask & bit.value()) != 0;
	}

	/**
	 * Associates actions to flags of a bit mask
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
		 * @return flag bit
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
