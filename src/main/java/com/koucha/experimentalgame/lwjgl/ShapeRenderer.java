package com.koucha.experimentalgame.lwjgl;

import com.koucha.experimentalgame.rendering.*;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Contains the code to render specific Renderables
 */
class ShapeRenderer
{
	private Matrix4f pvMatrix;
	private int defaultShader2D;
	private int defaultShader3D;

	private int playahVA;
	private int playahVB;
	private int playahNB;
	private int cubeVA;
	private int cubeVB;
	private int cubeNB;
	private int quadVA;
	private int quadVB;
	private int lineVA;
	private int lineVB;

	private FloatBuffer fb16;
	private FloatBuffer fb4;
	private Matrix4f working;

	ShapeRenderer()
	{
		fb16 = BufferUtils.createFloatBuffer( 16 );
		fb4 = BufferUtils.createFloatBuffer( 4 );
		working = new Matrix4f();

		defaultShader2D = ShaderUtil.load( "resources/shaders/glsl/default.vert", "resources/shaders/glsl/default.frag" );
		defaultShader3D = ShaderUtil.load( "resources/shaders/glsl/default3d.vert", "resources/shaders/glsl/default3d.frag" );

		loadPlayahVBO();
		loadCubeVBO();
		loadQuadVBO();
		loadLineVBO();
	}

	private void loadPlayahVBO()
	{
		playahVA = glGenVertexArrays();
		glBindVertexArray( playahVA );

		playahVB = glGenBuffers();
		glBindBuffer( GL_ARRAY_BUFFER, playahVB );

		FloatBuffer buff = BufferUtils.createFloatBuffer( 3 * 12 );
		buff    .put( +0.0f ).put( +0.0f ).put( +0.5f ) .put( +0.0f ).put( +0.5f ).put( -0.5f ) .put( -0.5f ).put( -0.5f ).put( -0.5f )
				.put( +0.0f ).put( +0.0f ).put( +0.5f ) .put( +0.5f ).put( -0.5f ).put( -0.5f ) .put( +0.0f ).put( +0.5f ).put( -0.5f )
				.put( +0.0f ).put( +0.0f ).put( +0.5f ) .put( -0.5f ).put( -0.5f ).put( -0.5f ) .put( +0.5f ).put( -0.5f ).put( -0.5f )
				.put( +0.0f ).put( +0.5f ).put( -0.5f ) .put( +0.5f ).put( -0.5f ).put( -0.5f ) .put( -0.5f ).put( -0.5f ).put( -0.5f );
		buff.flip();

		glBufferData( GL_ARRAY_BUFFER, buff, GL_STATIC_DRAW );
		glVertexAttribPointer( 0, 3, GL_FLOAT, false, 0, 0 );
		glBindBuffer( GL_ARRAY_BUFFER, 0 );

		playahNB = glGenBuffers();
		glBindBuffer( GL_ARRAY_BUFFER, playahNB );

		float nx, ny, nz;
		buff.clear();
		nx = -0.872872f; ny = 0.436436f; nz = 0.218218f;
		buff.put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz );
		nx = 0.872872f; ny = 0.436436f; nz = 0.218218f;
		buff.put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz );
		nx = 0f; ny = -0.894427f; nz = 0.447214f;
		buff.put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz );
		nx = 0f; ny = 0f; nz = -1f;
		buff.put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz );
		buff.flip();

		glBufferData( GL_ARRAY_BUFFER, buff, GL_STATIC_DRAW );
		glVertexAttribPointer( 1, 3, GL_FLOAT, false, 0, 0 );
		glBindBuffer( GL_ARRAY_BUFFER, 0 );

		glBindVertexArray( 0 );
	}

	private void loadCubeVBO()
	{
		cubeVA = glGenVertexArrays();
		glBindVertexArray( cubeVA );

		cubeVB = glGenBuffers();
		glBindBuffer( GL_ARRAY_BUFFER, cubeVB );

		FloatBuffer buff = BufferUtils.createFloatBuffer( 3 * 36 );
		buff.put( +0.5f ).put( +0.5f ).put( -0.5f ).put( -0.5f ).put( +0.5f ).put( -0.5f ).put( -0.5f ).put( +0.5f ).put( +0.5f )
				.put( +0.5f ).put( +0.5f ).put( -0.5f ).put( -0.5f ).put( +0.5f ).put( +0.5f ).put( +0.5f ).put( +0.5f ).put( +0.5f )

				.put( -0.5f ).put( +0.5f ).put( +0.5f ).put( -0.5f ).put( +0.5f ).put( -0.5f ).put( -0.5f ).put( -0.5f ).put( -0.5f )
				.put( -0.5f ).put( +0.5f ).put( +0.5f ).put( -0.5f ).put( -0.5f ).put( -0.5f ).put( -0.5f ).put( -0.5f ).put( +0.5f )

				.put( +0.5f ).put( +0.5f ).put( +0.5f ).put( -0.5f ).put( +0.5f ).put( +0.5f ).put( -0.5f ).put( -0.5f ).put( +0.5f )
				.put( +0.5f ).put( +0.5f ).put( +0.5f ).put( -0.5f ).put( -0.5f ).put( +0.5f ).put( +0.5f ).put( -0.5f ).put( +0.5f )

				.put( +0.5f ).put( +0.5f ).put( -0.5f ).put( +0.5f ).put( +0.5f ).put( +0.5f ).put( +0.5f ).put( -0.5f ).put( +0.5f )
				.put( +0.5f ).put( +0.5f ).put( -0.5f ).put( +0.5f ).put( -0.5f ).put( +0.5f ).put( +0.5f ).put( -0.5f ).put( -0.5f )

				.put( +0.5f ).put( -0.5f ).put( +0.5f ).put( -0.5f ).put( -0.5f ).put( +0.5f ).put( -0.5f ).put( -0.5f ).put( -0.5f )
				.put( +0.5f ).put( -0.5f ).put( +0.5f ).put( -0.5f ).put( -0.5f ).put( -0.5f ).put( +0.5f ).put( -0.5f ).put( -0.5f )

				.put( +0.5f ).put( -0.5f ).put( -0.5f ).put( -0.5f ).put( -0.5f ).put( -0.5f ).put( -0.5f ).put( +0.5f ).put( -0.5f )
				.put( +0.5f ).put( -0.5f ).put( -0.5f ).put( -0.5f ).put( +0.5f ).put( -0.5f ).put( +0.5f ).put( +0.5f ).put( -0.5f );
		buff.flip();

		glBufferData( GL_ARRAY_BUFFER, buff, GL_STATIC_DRAW );
		glVertexAttribPointer( 0, 3, GL_FLOAT, false, 0, 0 );
		glBindBuffer( GL_ARRAY_BUFFER, 0 );

		cubeNB = glGenBuffers();
		glBindBuffer( GL_ARRAY_BUFFER, cubeNB );

		float nx, ny, nz;
		buff.clear();
		nx = 0f; ny = 1f; nz = 0f;
		buff.put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz );
		buff.put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz );
		nx = -1f; ny = 0f; nz = 0f;
		buff.put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz );
		buff.put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz );
		nx = 0f; ny = 0f; nz = 1f;
		buff.put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz );
		buff.put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz );
		nx = 1f; ny = 0f; nz = 0f;
		buff.put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz );
		buff.put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz );
		nx = 0f; ny = -1f; nz = 0f;
		buff.put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz );
		buff.put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz );
		nx = 0f; ny = 0f; nz = -1f;
		buff.put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz );
		buff.put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz ).put( nx ).put( ny ).put( nz );
		buff.flip();

		glBufferData( GL_ARRAY_BUFFER, buff, GL_STATIC_DRAW );
		glVertexAttribPointer( 1, 3, GL_FLOAT, false, 0, 0 );
		glBindBuffer( GL_ARRAY_BUFFER, 0 );

		glBindVertexArray( 0 );
	}

	private void loadQuadVBO()
	{
		quadVA = glGenVertexArrays();
		glBindVertexArray( quadVA );

		quadVB = glGenBuffers();
		glBindBuffer( GL_ARRAY_BUFFER, quadVB );

		FloatBuffer buff = BufferUtils.createFloatBuffer( 3 * 6 );
		buff.put( -0.5f ).put( -0.5f ).put( 0f )
				.put( -0.5f ).put( 0.5f ).put( 0f )
				.put( 0.5f ).put( -0.5f ).put( 0f )
				.put( -0.5f ).put( 0.5f ).put( 0f )
				.put( 0.5f ).put( -0.5f ).put( 0f )
				.put( 0.5f ).put( 0.5f ).put( 0f );
		buff.flip();

		glBufferData( GL_ARRAY_BUFFER, buff, GL_STATIC_DRAW );
		glVertexAttribPointer( 0, 3, GL_FLOAT, false, 0, 0 );
		glBindBuffer( GL_ARRAY_BUFFER, 0 );

		glBindVertexArray( 0 );
	}

	private void loadLineVBO()
	{
		lineVA = glGenVertexArrays();
		glBindVertexArray( lineVA );

		lineVB = glGenBuffers();
		glBindBuffer( GL_ARRAY_BUFFER, lineVB );

		FloatBuffer buff = BufferUtils.createFloatBuffer( 3 * 2 );
		buff.put( 0f ).put( -0.5f ).put( 0f )
				.put( 0f ).put( 0.5f ).put( 0f );
		buff.flip();

		glBufferData( GL_ARRAY_BUFFER, buff, GL_STATIC_DRAW );
		glVertexAttribPointer( 0, 3, GL_FLOAT, false, 0, 0 );
		glBindBuffer( GL_ARRAY_BUFFER, 0 );

		glBindVertexArray( 0 );
	}

	public void cleanUp()
	{
		glDeleteProgram( defaultShader2D );
		glDeleteProgram( defaultShader3D );
		glDeleteVertexArrays( playahVA );
		glDeleteBuffers( playahVB );
		glDeleteVertexArrays( cubeVA );
		glDeleteBuffers( cubeVB );
		glDeleteVertexArrays( quadVA );
		glDeleteBuffers( quadVB );
		glDeleteVertexArrays( lineVA );
		glDeleteBuffers( lineVB );
	}

	public void setPVMatrix( Matrix4f pvMatrix )
	{
		this.pvMatrix = pvMatrix;
	}

	public void render( Line line )
	{
//		Color color = line.getColor();
//		glColor4f( color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() );
//		glLineWidth( line.getWidth() );
//		glBegin( GL_LINES );
//		glVertex2f( line.getStartX(), line.getStartY() );
//		glVertex2f( line.getEndX(), line.getEndY() );
//		glEnd();
	}

	public void render( Rectangle rectangle )
	{

		working.translation( rectangle.getPosition() ).rotateZ( rectangle.getAngle() ).scale( rectangle.getWidth(), rectangle.getHeight(), 1 );

		drawVAO(defaultShader2D, rectangle.getColor(), quadVA, 6);
	}

	public void render( Text text )
	{
		//todo
	}

	public void render( Cube cube )
	{
		working.identity().mul( cube.getPosition().getOrientedPosition() ).scale( cube.getSize() );

		drawVAO(defaultShader3D, cube.getColor(), cubeVA, 36, true);
	}

	public void render( Playah playah )
	{
		working.identity().mul( playah.getPosition().getOrientedPosition() ).scale( playah.getSize() );

		drawVAO(defaultShader3D, playah.getColor(), playahVA, 12, true );
	}

	private void drawVAO( int shaderProgram, Color color, int vao, int verticeNumber )
	{
		drawVAO( shaderProgram, color, vao, verticeNumber, false );
	}

	private void drawVAO( int shaderProgram, Color color, int vao, int verticeNumber, boolean hasNormals)
	{
		glUseProgram( shaderProgram );

		int loc;

		if(hasNormals)
		{
			loc = glGetUniformLocation( shaderProgram, "NORM" );
			if(loc == -1) System.err.println("Couldn't get Uniform location for 'NORM'");
			new Matrix4f( working ).invert().transpose().get( fb16 );
			glUniformMatrix4fv( loc, false, fb16 );
		}

		pvMatrix.mul( working, working );

		loc = glGetUniformLocation( shaderProgram, "PVM" );
		if(loc == -1) System.err.println("Couldn't get Uniform location for 'PVM'");
		working.get( fb16 );
		glUniformMatrix4fv( loc, false, fb16 );

		loc = glGetUniformLocation( shaderProgram, "COLOR" );
		if(loc == -1) System.err.println("Couldn't get Uniform location for 'COLOR'");
		color.get( fb4 );
		glUniform4fv( loc, fb4 );

		glBindVertexArray( vao );
		glEnableVertexAttribArray( 0 );
		if( hasNormals ) glEnableVertexAttribArray( 1 );
		glDrawArrays( GL_TRIANGLES, 0, verticeNumber );
		if( hasNormals ) glDisableVertexAttribArray( 1 );
		glDisableVertexAttribArray( 0 );
		glBindVertexArray( 0 );
	}
}
