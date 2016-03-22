package com.koucha.experimentalgame;

/**
 * Instruction sent from a {@link Controller} to a {@link Entity}
 */
public class Action
{
	/**
	 * velocity
	 */
	public float vel;

	/**
	 * angle of the movement direction in radians clockwise from the top
	 */
	public float angle;

	/**
	 * indicates if a skill should be used
	 */
	public boolean useSkill;

	/**
	 * identifies the skill that should be used
	 */
	public int skillNr;

	public Action()
	{
		vel = 0;
		angle = 0;
		useSkill = false;
		skillNr = 0;
	}
}
