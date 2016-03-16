package com.company.input;

public class InputEvent
{
	public KeyEventType type;
	public int code;
	public String name;

	public InputEvent( int code, String name, KeyEventType type )
	{
		this.code = code;
		this.type = type;
		this.name = name;
	}
}
