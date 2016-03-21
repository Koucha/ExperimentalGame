package com.koucha.experimentalgame.rendering.lwjgl;

import com.koucha.experimentalgame.rendering.Line;
import com.koucha.experimentalgame.rendering.Rectangle;
import com.koucha.experimentalgame.rendering.Text;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * Contains the code to render specific Renderables
 */
class ShapeRenderer
{

	ShapeRenderer()
	{
		// nothing
	}

	public void render( Line line )
	{
		Color color = line.getColor();
		glColor4f( color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, color.getAlpha()/255f );
		glLineWidth( line.getWidth() );
		glBegin( GL_LINES );
		glVertex2f( line.getStartX(), line.getStartY() );
		glVertex2f( line.getEndX(), line.getEndY() );
		glEnd();
	}

	public void render( Rectangle rectangle )
	{
		Color color = rectangle.getColor();
		glColor4f( color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, color.getAlpha()/255f );

		if( rectangle.isFilled() )
		{
			glBegin( GL_POLYGON );
			glVertex3f( rectangle.getPosX() - rectangle.getWidth() / 2f, rectangle.getPosY() - rectangle.getHeight() / 2f, 0 );
			glVertex3f( rectangle.getPosX() - rectangle.getWidth() / 2f, rectangle.getPosY() + rectangle.getHeight() / 2f, 0 );
			glVertex3f( rectangle.getPosX() + rectangle.getWidth() / 2f, rectangle.getPosY() + rectangle.getHeight() / 2f, 0 );
			glVertex3f( rectangle.getPosX() + rectangle.getWidth() / 2f, rectangle.getPosY() - rectangle.getHeight() / 2f, 0 );
			glEnd();
		} else
		{

			glLineWidth( 1.1f );
			glBegin( GL_LINE_LOOP );
			glVertex2f( rectangle.getPosX() - rectangle.getWidth() / 2f, rectangle.getPosY() - rectangle.getHeight() / 2f );
			glVertex2f( rectangle.getPosX() - rectangle.getWidth() / 2f, rectangle.getPosY() + rectangle.getHeight() / 2f );
			glVertex2f( rectangle.getPosX() + rectangle.getWidth() / 2f, rectangle.getPosY() + rectangle.getHeight() / 2f );
			glVertex2f( rectangle.getPosX() + rectangle.getWidth() / 2f, rectangle.getPosY() - rectangle.getHeight() / 2f );
			glEnd();
		}
	}

	public void render( Text text )
	{
		//todo
	}
}
