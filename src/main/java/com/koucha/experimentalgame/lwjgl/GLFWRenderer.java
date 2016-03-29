package com.koucha.experimentalgame.lwjgl;

import com.koucha.experimentalgame.entity.Camera;
import com.koucha.experimentalgame.entity.Mesh;
import com.koucha.experimentalgame.input.InputBridge;
import com.koucha.experimentalgame.rendering.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
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
class GLFWRenderer implements Renderer
{
	// We need to strongly reference callback instances.
	private GLFWErrorCallback errorCallback;
	private GLFWFramebufferSizeCallback framebufferSizeCallback;

	private ShapeRenderer shapeRenderer;

	// The window handle
	private long window = NULL;

	private Camera camera;
	private OrthoCam orthoCam;

	private InputBridge inputBridge = null;

	// Hard links needed to protect from garbage collection (used by JNI code)
	private GLFWKeyInput keyInput;
	private GLFWMouseInput mouseInput;

	GLFWRenderer()
	{

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
	public void render( Mesh renderable )
	{
		if( renderable instanceof Line )
		{
			shapeRenderer.render( (Line) renderable );
		} else if( renderable instanceof Rectangle )
		{
			shapeRenderer.render( (Rectangle) renderable );
		} else if( renderable instanceof Text )
		{
			shapeRenderer.render( (Text) renderable );
		} else if( renderable instanceof Cube )
		{
			shapeRenderer.render( (Cube) renderable );
		} else if( renderable instanceof Playah )
		{
			shapeRenderer.render( (Playah) renderable );
		}/*else
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
		glfwWindowHint( GLFW_SAMPLES, 16 );

		// Create the window
		window = glfwCreateWindow( initialWidth, initialHeight, s, NULL, NULL );
		if( window == NULL )
			throw new RuntimeException( "Failed to create the GLFW window" );

		glfwSetFramebufferSizeCallback( window, (framebufferSizeCallback = new GLFWFramebufferSizeCallback()
		{
			@Override
			public void invoke( long window, int width, int height )
			{
				onResize( width, height );
			}
		}) );

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

		glfwSetInputMode( window, GLFW_CURSOR, GLFW_CURSOR_DISABLED );
	}

	private void onResize( int width, int height )
	{
		if( camera != null )
		{
			camera.setAspectRatio( width / ((float) height) );
		}
		if( orthoCam != null )
		{
			orthoCam.setFrame( 0, width, height, 0 );
		}
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
		shapeRenderer.setPVMatrix( camera.getViewProjectionMatrix() );
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

		System.out.println( glGetString( GL_VERSION ) );

		shapeRenderer = new ShapeRenderer();

		/* Get width and height of framebuffer */
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
		glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
		int width = widthBuffer.get();
		int height = heightBuffer.get();

		orthoCam = new OrthoCam( 0, width, height, 0 );

		// Set the clear color
		glClearColor( 0.0f, 0.0f, 0.0f, 1.0f );

		glEnable( GL_DEPTH_TEST );
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
	public void cleanUp()
	{
		shapeRenderer.cleanUp();

		/* GLFW has to be terminated or else the application will run in background */
		glfwTerminate();

		if( errorCallback != null )
			errorCallback.release();
		if( framebufferSizeCallback != null )
			framebufferSizeCallback.release();
		if( keyInput != null )
			keyInput.release();
		if( mouseInput != null )
			mouseInput.release();
	}

	@Override
	public boolean vSyncEnabled()
	{
		return true;
	}

	@Override
	public void initializeGUIRenderIteration()
	{
		glClear( GL_DEPTH_BUFFER_BIT );
		shapeRenderer.setPVMatrix( orthoCam.getViewProjectionMatrix() );
	}

	@Override
	public void setCamera( Camera camera )
	{
		this.camera = camera;
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
	 * Input managers: {@link GLFWKeyInput} and {@link GLFWMouseInput}
	 */
	private void setUpInputManagers()
	{
		if( window != NULL && inputBridge != null )
		{
			if( keyInput == null )
				keyInput = new GLFWKeyInput( window, inputBridge );
			if( mouseInput == null )
				mouseInput = new GLFWMouseInput( window, inputBridge );
		}
	}
}
