package com.koucha.experimentalgame.rendering.lwjgl;

import com.koucha.experimentalgame.input.InputBridge;
import com.koucha.experimentalgame.input.InputEvent;
import com.koucha.experimentalgame.input.KeyEventType;
import com.koucha.experimentalgame.rendering.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.*;
import java.lang.reflect.Field;

/**
 * Implements {@link Renderer} using LWJGL with OpenGL
 */
public class LWJGLRenderer implements Renderer
{
	// We need to strongly reference callback instances.
	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;

	private ShapeRenderer shapeRenderer;

	// The window handle
	private long window;


	private InputBridge inputBridge = null;

	public LWJGLRenderer()
	{
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void render( Renderable renderable )
	{
		if( renderable instanceof com.koucha.experimentalgame.rendering.Line )
		{
			shapeRenderer.render( (Line) renderable );
		} else if( renderable instanceof Rectangle )
		{
			shapeRenderer.render( (Rectangle) renderable );
		} else if( renderable instanceof Text )
		{
			shapeRenderer.render( (Text) renderable );
		} /*else
		{
			// nothing
		}*/
	}

	@Override
	public void createWindow( int initialWidth, int initialHeight, String s )
	{
		// Configure our window
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint( GLFW_VISIBLE, GL_FALSE ); // the window will stay hidden after creation
		glfwWindowHint( GLFW_RESIZABLE, GL_TRUE ); // the window will be resizable

		// Create the window
		window = glfwCreateWindow( initialWidth, initialHeight, s, NULL, NULL );
		if( window == NULL )
			throw new RuntimeException( "Failed to create the GLFW window" );

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback( window, keyCallback = new GLFWKeyCallback()
		{
			private final Map<Integer, String> KEY_CODES = apiClassTokens( ( field, value ) -> field.getName().startsWith("GLFW_KEY_"), null, GLFW.class);
			@Override
			public void invoke( long window, int key, int scanCode, int action, int mods )
			{
				if( key == GLFW_KEY_ESCAPE )
				{
					if(action == GLFW_RELEASE)
					{
						glfwSetWindowShouldClose( window, GL_TRUE ); // We will detect this in our rendering loop
					}
				} else
				{
					// System.out.println( "Key: " + KEY_CODES.get( key ) + " Key Code:" + scanCode );
					if( action == GLFW_PRESS )
					{
						inputBridge.doKeyEvent( new InputEvent( key, KEY_CODES.get( key ), KeyEventType.pressed ) );
					} else if( action == GLFW_RELEASE )
					{
						inputBridge.doKeyEvent( new InputEvent( key, KEY_CODES.get( key ), KeyEventType.released ) );
					} else if( action == GLFW_REPEAT )
					{
						inputBridge.doKeyEvent( new InputEvent( key, KEY_CODES.get( key ), KeyEventType.typed ) );
					}
				}
			}
		} );

		// Get the resolution of the primary monitor
		ByteBuffer vidMode = glfwGetVideoMode( glfwGetPrimaryMonitor() );
		// Center our window
		glfwSetWindowPos(
				window,
				(GLFWvidmode.width( vidMode ) - initialWidth) / 2,
				(GLFWvidmode.height( vidMode ) - initialHeight) / 2
		);

		// Make the OpenGL context current
		glfwMakeContextCurrent( window );

		// Enable v-sync
		glfwSwapInterval( 1 );

		// Make the window visible
		glfwShowWindow( window );
	}

	@Override
	public void setInputBridge( InputBridge inputBridge )
	{
		this.inputBridge = inputBridge;
	}

	@Override
	public void initializeUpdateIteration()
	{
		// Poll for window events. The key callback above will only be
		// invoked during this call.
		glfwPollEvents();
	}

	@Override
	public void initializeRenderIteration()
	{
		glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT ); // clear the framebuffer
	}

	@Override
	public void finishRenderIteration()
	{
		glfwSwapBuffers( window ); // swap the color buffers
	}

	@Override
	public boolean windowShouldClose()
	{
		return glfwWindowShouldClose( window ) == GL_TRUE;
	}

	@Override
	public void init()
	{
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		glfwSetErrorCallback( errorCallback = errorCallbackPrint( System.err ) );

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if( glfwInit() != GL_TRUE )
			throw new IllegalStateException( "Unable to initialize GLFW" );
	}

	@Override
	public void preLoopInitialization()
	{
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the ContextCapabilities instance and makes the OpenGL
		// bindings available for use.
		GLContext.createFromCurrent();

		/* Get width and height of framebuffer */
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
		glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
		int width = widthBuffer.get();
		int height = heightBuffer.get();

		glMatrixMode( GL_PROJECTION );
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho( 0, width, 0, height, -1, 1 );

		glMatrixMode(GL_MODELVIEW);
		glViewport( 0, 0, width, height );

		// Set the clear color
		glClearColor( 0.0f, 0.0f, 0.0f, 1.0f );

        /* Enable blending */
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void cleanUp()
	{
		/* GLFW has to be terminated or else the application will run in background */
		glfwTerminate();

		if( errorCallback != null )
			errorCallback.release();
	}

	@Override
	public boolean vSyncEnabled()
	{
		return true;
	}

	@Override
	public int getWindowHeight()
	{
		/* Get width and height of framebuffer */
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
		glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
		return heightBuffer.get();
	}

	@Override
	public int getWindowWidth()
	{
		/* Get width and height of framebuffer */
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
		glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
		return widthBuffer.get();
	}

	/**
	 * Returns a map of public static final integer fields in the specified classes, to their String representations. An optional filter can be specified to
	 * only include specific fields. The target map may be null, in which case a new map is allocated and returned.
	 *
	 * <p>This method is useful when debugging to quickly identify values returned from an API.</p>
	 *
	 * @param filter       the filter to use (optional)
	 * @param target       the target map (optional)
	 * @param tokenClasses the classes to get tokens from
	 *
	 * @return the token map
	 */
	public static Map<Integer, String> apiClassTokens( LWJGLUtil.TokenFilter filter, Map<Integer, String> target, Class<?>... tokenClasses)
	{
		if ( target == null )
			target = new HashMap(64);

		int TOKEN_MODIFIERS = Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL;

		for ( Class<?> tokenClass : tokenClasses ) {
			if ( tokenClass == null )
				continue;

			for ( Field field : tokenClass.getDeclaredFields() ) {
				// Get only <public static final int> fields.
				if ( (field.getModifiers() & TOKEN_MODIFIERS) == TOKEN_MODIFIERS && field.getType() == int.class ) {
					try {
						int value = field.getInt(null);
						if ( filter != null && !filter.accept(field, value) )
							continue;

						String name = target.get(value);
						target.put(value, name == null ? field.getName().substring( 9 ) : name + "|" + field.getName());
					} catch (IllegalAccessException e) {
						// Ignore
					}
				}
			}
		}

		return target;
	}
}
