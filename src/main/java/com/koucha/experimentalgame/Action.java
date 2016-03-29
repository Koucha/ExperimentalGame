package com.koucha.experimentalgame;

import com.koucha.experimentalgame.entity.Controller;
import com.koucha.experimentalgame.entity.Entity;

/**
 * Instruction sent from a {@link Controller} to a {@link Entity}
 */
public class Action
{
	/**
	 * movement to the right
	 */
	public float right;

	/**
	 * forward movement
	 */
	public float forward;

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
		right = 0;
		forward = 0;
		useSkill = false;
		skillNr = 0;
	}
}
