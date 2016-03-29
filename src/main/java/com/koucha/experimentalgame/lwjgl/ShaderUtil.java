package com.koucha.experimentalgame.lwjgl;


import com.koucha.experimentalgame.Util;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

/**
 * Utility for creating and loading GLSL shader programs
 */
public class ShaderUtil
{
	private ShaderUtil()
	{
	}

	/**
	 * Load a shader program to the graphics card
	 *
	 * @param vertPath path to the vertex shader
	 * @param fragPath path to the fragment shader
	 * @return id of the generated shader program
	 */
	public static int load( String vertPath, String fragPath )
	{
		String vert = Util.loadAsString( vertPath );
		String frag = Util.loadAsString( fragPath );
		return create( vert, frag );
	}

	/**
	 * Create a shader program on the graphics card
	 *
	 * @param vert content of the vertex shader
	 * @param frag content of the fragment shader
	 * @return id of the generated shadr program
	 */
	public static int create( String vert, String frag )
	{
		int program = glCreateProgram();
		int vertID = glCreateShader( GL_VERTEX_SHADER );
		int fragID = glCreateShader( GL_FRAGMENT_SHADER );
		glShaderSource( vertID, vert );
		glShaderSource( fragID, frag );

		glCompileShader( vertID );
		if( glGetShaderi( vertID, GL_COMPILE_STATUS ) == GL_FALSE )
		{
			System.err.println( "Failed to compile vertex shader!" );
			System.err.println( glGetShaderInfoLog( vertID, 2048 ) );
		}
		glCompileShader( fragID );
		if( glGetShaderi( fragID, GL_COMPILE_STATUS ) == GL_FALSE )
		{
			System.err.println( "Failed to compile fragment shader!" );
			System.err.println( glGetShaderInfoLog( fragID, 2048 ) );
		}

		glAttachShader( program, vertID );
		glAttachShader( program, fragID );
		glLinkProgram( program );
		glValidateProgram( program );

		glDetachShader( program, vertID );
		glDetachShader( program, fragID );

		glDeleteShader( vertID );
		glDeleteShader( fragID );

		return program;
	}
}
