package com.company;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas
{
	public Window( int width, int height, String title, Game game )
	{
		JFrame frame = new JFrame( title );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		frame.setResizable( false );
		frame.getContentPane().setPreferredSize( new Dimension( width, height ) );
		frame.pack();

		frame.setLocationRelativeTo( null );
		frame.add( game );
		frame.setVisible( true );
		//game.start();
	}
}
