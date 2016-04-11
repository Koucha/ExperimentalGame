package com.koucha.experimentalgame.entitySystem.system;

import com.koucha.experimentalgame.entitySystem.ComponentFlag;
import com.koucha.experimentalgame.entitySystem.Entity;
import com.koucha.experimentalgame.entitySystem.SystemFlag;
import com.koucha.experimentalgame.entitySystem.component.Input;
import com.koucha.experimentalgame.input.InputBridge;
import com.koucha.experimentalgame.input.InputEvent;
import com.koucha.experimentalgame.input.InputMap;

// TODO: 10.04.2016 comment
public class LocalPlayerInputSystem extends AbstractSystem
{
	private InputBridge inputBridge;
	private Input input = new Input();

	public LocalPlayerInputSystem( InputBridge inputBridge )
	{
		super( new LinkedEntityList<>( new InputElement() ) );

		this.inputBridge = inputBridge;

		InputMap inputMap = inputBridge.getInputMap();

		inputMap.addLink( "Forward", 0x57, ( e ) -> handleEvent( e, InputSubSystem.Selector.up ) );
		inputMap.addLink( "Backward", 0x53, ( e ) -> handleEvent( e, InputSubSystem.Selector.down ) );
		inputMap.addLink( "Turn Left", 0x41, ( e ) -> handleEvent( e, InputSubSystem.Selector.left ) );
		inputMap.addLink( "Turn Right", 0x44, ( e ) -> handleEvent( e, InputSubSystem.Selector.right ) );
		inputMap.addLink( "Skill 1", 0x20, ( e ) -> handleEvent( e, InputSubSystem.Selector.skill1 ) );
		inputMap.addLink( "Skill 2", 0x46, ( e ) -> handleEvent( e, InputSubSystem.Selector.skill2 ) );
	}

	/**
	 * Handles the {@link InputEvent}s received through the {@link InputMap}
	 *
	 * @param evt key event
	 * @param bit selector specifying the action to be performed (see {@link InputSubSystem.Selector})
	 */
	private void handleEvent( InputEvent evt, InputSubSystem.Selector bit )
	{
		try
		{
			InputSubSystem.set( input, bit, isPressed( evt ) );
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
	public SystemFlag getFlag()
	{
		return SystemFlag.LocalPlayerInputSystem;
	}

	@Override
	protected void processEntities()
	{
		for( EntityList.Element element : entityList )
		{
			InputSubSystem.write( ((InputElement) element).input, input );
		}
	}

	@Override
	protected boolean acceptEntity( Entity entity )
	{
		return entity.accept( ComponentFlag.LocalPlayer );
	}

	private static class InputElement extends LinkedEntityList.LinkElement
	{
		Input input;

		private InputElement()
		{
		}

		private InputElement( Entity entity )
		{
			super( entity );
			input = (Input) entity.get( ComponentFlag.Input );
		}

		@Override
		public EntityList.Element makeFrom( Entity entity )
		{
			return new InputElement( entity );
		}
	}

	/**
	 * Is thrown if a key event performed an unknown action
	 */
	private class InvalidStateException extends Exception
	{
		// empty
	}
}
