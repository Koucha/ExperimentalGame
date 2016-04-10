package com.koucha.experimentalgame.lwjgl;

import com.koucha.experimentalgame.input.InputBridge;
import com.koucha.experimentalgame.input.InputEvent;
import com.koucha.experimentalgame.input.KeyEventType;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;


/**
 * Mouse listener that forwards mouse button events and the mouse position to an {@link InputBridge}
 */
class GLFWMouseInput
{
	/**
	 * Mouse buttons mapped by their codes, ex: {@code <0, "1|LEFT">}
	 */
	private final Map< Integer, String > MOUSE_CODES = GLFWRenderer.getGLFWTokens( "MOUSE_BUTTON" );

	private InputBridge inputBridge;

	// Hard links needed to protect from garbage collection (used by JNI code)
	@SuppressWarnings( {"FieldCanBeLocal", "unused"} )
	private GLFWMouseButtonCallback buttonCallback;
	@SuppressWarnings( {"FieldCanBeLocal", "unused"} )
	private GLFWCursorPosCallback positionCallback;

	GLFWMouseInput( long window, InputBridge inputBridge )
	{
		this.inputBridge = inputBridge;

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetMouseButtonCallback( window, buttonCallback = new GLFWMouseButtonCallback()    //class not interface, no lambda for me :-/
		{
			@Override
			public void invoke( long window, int button, int action, int mods )
			{
				mouseButtonCallbackInvoke( button, action );
			}
		} );

		glfwSetCursorPosCallback( window, positionCallback = new GLFWCursorPosCallback()    //class not interface, no lambda for me :-/
		{
			@Override
			public void invoke( long window, double xPos, double yPos )
			{
				inputBridge.doMouseMovement( (int) xPos, (int) yPos );
			}
		} );
	}

	private void mouseButtonCallbackInvoke( int button, int action )
	{
		if( action == GLFW_PRESS )
		{
			inputBridge.doKeyEvent( new InputEvent( GLFW_MOUSE_BUTTON_1 - button - 1, MOUSE_CODES.get( button ), KeyEventType.pressed ) );
		} else if( action == GLFW_RELEASE )
		{
			inputBridge.doKeyEvent( new InputEvent( GLFW_MOUSE_BUTTON_1 - button - 1, MOUSE_CODES.get( button ), KeyEventType.released ) );
		}
	}

	public void release()
	{
		buttonCallback.release();
		positionCallback.release();
	}
}
