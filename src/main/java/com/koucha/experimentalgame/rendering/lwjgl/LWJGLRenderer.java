package com.koucha.experimentalgame.rendering.lwjgl;

import com.koucha.experimentalgame.input.InputBridge;
import com.koucha.experimentalgame.input.lwjgl.JoystickInput;
import com.koucha.experimentalgame.rendering.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Implements {@link Renderer} using LWJGL with OpenGL
 */
public class LWJGLRenderer implements Renderer
{
	// We need to strongly reference callback instances.
	private GLFWErrorCallback errorCallback;

	private ShapeRenderer shapeRenderer;

	// The window handle
	private long window = NULL;


	private InputBridge inputBridge = null;

	// Hard links needed to protect from garbage collection (used by JNI code)
	@SuppressWarnings( {"FieldCanBeLocal", "unused"} )
	private KeyInput keyInput;
	@SuppressWarnings( {"FieldCanBeLocal", "unused"} )
	private MouseInput mouseInput;

	public LWJGLRenderer()
	{
		shapeRenderer = new ShapeRenderer();
	}

	/**
	 * generate a Map of all static final int fields of {@link GLFW} that begin with {@code "GLFW_" + type + "_"}
	 *
	 * @param type defines what members to map
	 * @return the specified members mapped by their values
	 */
	static Map< Integer, String > getGLFWTokens( String type )
	{
		HashMap< Integer, String > target = new HashMap<>( 64 );

		final int TOKEN_MODIFIERS = Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL;

		final String startsWith = "GLFW_" + type + "_";
		final int offset = startsWith.length();

		for( Field field : GLFW.class.getDeclaredFields() )
		{
			// Get only <public static final int> fields.
			if( (field.getModifiers() & TOKEN_MODIFIERS) == TOKEN_MODIFIERS && field.getType() == int.class )
			{
				try
				{
					int value = field.getInt( null );
					if( !(field.getName().startsWith( startsWith )) )
						continue;

					String name = target.get( value );
					target.put( value, name == null ?
							field.getName().substring( offset ).replace( '_', ' ' ) :
							name + "|" + field.getName().substring( offset ).replace( '_', ' ' ) );
				} catch( IllegalAccessException e )
				{
					// Ignore
				}
			}
		}

		return target;
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

		setUpInputManagers();

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
		setUpInputManagers();
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
	 * Initializes the input managers, if a window and a inputBridge are present
	 *
	 * Input managers: {@link KeyInput}, {@link MouseInput} and {@link JoystickInput}
	 */
	private void setUpInputManagers()
	{
		if( window != NULL && inputBridge != null )
		{
			if( keyInput == null )
				keyInput = new KeyInput( window, inputBridge );
			if( mouseInput == null )
				mouseInput = new MouseInput( window, inputBridge );
		}
	}
}
