package com.koucha.experimentalgame.lwjgl;

import com.koucha.experimentalgame.input.InputBridge;
import com.koucha.experimentalgame.input.InputEvent;
import com.koucha.experimentalgame.input.KeyEventType;
import org.lwjgl.glfw.GLFWCharModsCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Key listener that forwards key events to an {@link InputBridge}
 */
class GLFWKeyInput
{
	/**
	 * Keys mapped by their codes, ex: {@code <32, "SPACE">}
	 */
	private final Map< Integer, String > KEY_CODES = GLFWRenderer.getGLFWTokens( "KEY" );

	private InputBridge inputBridge;

	// Hard links needed to protect from garbage collection (used by JNI code)
	@SuppressWarnings( {"FieldCanBeLocal", "unused"} )
	private GLFWKeyCallback keyCallback;
	@SuppressWarnings( {"FieldCanBeLocal", "unused"} )
	private GLFWCharModsCallback charModsCallback;

	private String lastPressed;

	GLFWKeyInput( long window, InputBridge inputBridge )
	{
		this.inputBridge = inputBridge;

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback( window, keyCallback = new GLFWKeyCallback()    //class not interface, no lambda for me :-/
		{
			@Override
			public void invoke( long window, int key, int scanCode, int action, int mods )
			{
				keyCallbackInvoke( key, action );
			}
		} );

		glfwSetCharModsCallback( window, charModsCallback = new GLFWCharModsCallback()    //class not interface, no lambda for me :-/
		{
			@Override
			public void invoke( long window, int codePoint, int mods )
			{
				lastPressed = new String( Character.toChars( codePoint ) ).toUpperCase();
				inputBridge.addText( lastPressed );
			}
		} );
	}

	/**
	 * Is invoked by the {@link GLFWKeyCallback}
	 *
	 * @param key    GLFW code of the key that generated the event
	 * @param action GLFW code of the action (pressed, released etc.)
	 */
	private void keyCallbackInvoke( int key, int action )
	{
		if( action == GLFW_PRESS )
		{
			inputBridge.doKeyEvent( new InputEvent( key, KEY_CODES.get( key ), KeyEventType.pressed ) );
		} else if( action == GLFW_RELEASE )
		{
			String name = KEY_CODES.get( key );
			inputBridge.doKeyEvent( new InputEvent( key, (name.length() == 1) ? (lastPressed) : (name), KeyEventType.released ) );
		}
	}

	public void release()
	{
		keyCallback.release();
		charModsCallback.release();
	}
}
