package com.koucha.experimentalgame.rendering.lwjgl;

import com.koucha.experimentalgame.input.InputBridge;
import com.koucha.experimentalgame.rendering.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

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
	private GLFWKeyCallback keyCallback;

	// The window handle
	private long window;


	private InputBridge inputBridge = null;

	@Override
	public void render( Renderable renderable )
	{
		if( renderable instanceof com.koucha.experimentalgame.rendering.Line )
		{
			ShapeRenderer.render( (Line) renderable );
		} else if( renderable instanceof Rectangle )
		{
			ShapeRenderer.render( (Rectangle) renderable );
		} else if( renderable instanceof Text )
		{
			ShapeRenderer.render( (Text) renderable );
		} else
		{
			// nothing
		}
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
			@Override
			public void invoke( long window, int key, int scanCode, int action, int mods )
			{
				if( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
					glfwSetWindowShouldClose( window, GL_TRUE ); // We will detect this in our rendering loop
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
}
