package com.koucha.experimentalgame.entitySystem.component;

// TODO: 10.04.2016 comment
public class Guard< T >
{
	private static boolean readFront = true;
	private T front;
	private T back;
	public Guard( T original, T copy )
	{
		front = original;
		back = copy;
	}

	public static void switchBuffer()
	{
		readFront = !readFront;
	}

	public T read()
	{
		if( readFront )
		{
			return front;
		} else
		{
			return back;
		}
	}

	public T write()
	{
		if( readFront )
		{
			return back;
		} else
		{
			return front;
		}
	}

	public void set( T in )
	{
		if( readFront )
		{
			back = in;
		} else
		{
			front = in;
		}
	}
}
