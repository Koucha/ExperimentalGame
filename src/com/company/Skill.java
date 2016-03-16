package com.company;

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

	public TargetType getTargeting()
	{
		return targeting;
	}

	public float getDamageMultiplier()
	{
		return damageMultiplier;
	}

	public int getBaseDamagePlus()
	{
		return baseDamagePlus;
	}

	public enum TargetType
	{
		none(),
		single(),
		direction(),
		area()
	}
}
