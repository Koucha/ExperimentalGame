package com.koucha.experimentalgame.rendering.lwjgl;

import com.koucha.experimentalgame.input.InputBridge;
import com.koucha.experimentalgame.input.InputEvent;
import com.koucha.experimentalgame.input.KeyEventType;
import org.lwjgl.glfw.GLFWCharModsCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * Key listener that forwards key events to an {@link InputBridge}
 */
class KeyInput
{
	/**
	 * Keys mapped by their codes, ex: {@code <32, "SPACE">}
	 */
	private final Map<Integer, String> KEY_CODES = LWJGLRenderer.getGLFWTokens( "KEY" );

	private InputBridge inputBridge;
	private GLFWKeyCallback keyCallback;
	private GLFWCharModsCallback charModsCallback;
	private String lastPressed;

	public KeyInput(long window, InputBridge inputBridge)
	{
		this.inputBridge = inputBridge;

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback( window, keyCallback = new GLFWKeyCallback()	//class not interface, no lambda for me :-/
		{
			@Override
			public void invoke( long window, int key, int scanCode, int action, int mods )
			{
				keyCallbackInvoke( window, key, action );
			}
		} );

		glfwSetCharModsCallback( window, charModsCallback = new GLFWCharModsCallback()	//class not interface, no lambda for me :-/
		{
			@Override
			public void invoke( long window, int codePoint, int mods )
			{
				lastPressed = new String(Character.toChars(codePoint)).toUpperCase();
				inputBridge.addText( lastPressed );
			}
		} );
	}
	/**
	 * Is invoked by the {@link GLFWKeyCallback}
	 *
	 * @param window	identifier of the GLFW window sending the event
	 * @param key 		GLFW code of the key that generated the event
	 * @param action	GLFW code of the action (pressed, released etc.)
	 */
	private void keyCallbackInvoke( long window, int key, int action )
	{
		if( key == GLFW_KEY_ESCAPE )
		{
			if(action == GLFW_RELEASE)
			{
				glfwSetWindowShouldClose( window, GL_TRUE ); // We will detect this in our rendering loop
			}
		} else
		{
			if( action == GLFW_PRESS )
			{
				inputBridge.doKeyEvent( new InputEvent( key, KEY_CODES.get( key ), KeyEventType.pressed ) );
			} else if( action == GLFW_RELEASE )
			{
				String name = KEY_CODES.get( key );
				inputBridge.doKeyEvent( new InputEvent( key, (name.length() == 1)?(lastPressed):(name), KeyEventType.released ) );
			}
		}
	}
}
