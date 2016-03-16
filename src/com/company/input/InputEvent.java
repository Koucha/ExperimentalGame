package com.company.input;

/**
 * Event containing information about input from a key
 */
public class InputEvent
{
	public KeyEventType type;
	public int code;
	public String name;

	/**
	 * Constructor
	 *
	 * @param code code of the key this event originates from
	 * @param name label of the key (ex. "shift")
	 * @param type type of the action the key performed (see {@link KeyEventType})
	 */
	public InputEvent( int code, String name, KeyEventType type )
	{
		this.code = code;
		this.type = type;
		this.name = name;
	}
}
