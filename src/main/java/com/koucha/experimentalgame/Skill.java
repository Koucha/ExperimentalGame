package com.koucha.experimentalgame;

import com.koucha.experimentalgame.entity.Entity;

/**
 * Represents a skill that can be used by {@link Entity}s
 */
public class Skill
{
	private TargetType targeting;
	private float damageMultiplier;
	private int baseDamagePlus;

	public Skill()
	{
		targeting = TargetType.single;
		damageMultiplier = 1;
		baseDamagePlus = 0;
	}

	/**
	 * @return the targeting type used by this skill (see {@link TargetType})
	 */
	public TargetType getTargeting()
	{
		return targeting;
	}

	/**
	 * @return the damage multiplier of this skill
	 */
	public float getDamageMultiplier()
	{
		return damageMultiplier;
	}

	/**
	 * @return the base damage bonus of this skill
	 */
	public int getBaseDamagePlus()
	{
		return baseDamagePlus;
	}

	/**
	 * Different types of Targeting
	 */
	public enum TargetType
	{
		/**
		 * no target has to be selected
		 */
		none,

		/**
		 * one character is targeted
		 */
		single,

		/**
		 * the skill strikes in one direction
		 */
		direction,

		/**
		 * the skill affects the area around a point
		 */
		area
	}
}
