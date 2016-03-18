package com.koucha.experimentalgame;

import com.koucha.experimentalgame.effects.EffectProjectile;
import com.koucha.experimentalgame.effects.EffectRay;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

/**
 * Character. Monster, Player etc.
 */
public class Character implements GameObject
{
	public static final int BASE_DAMAGE = 10;
	public static final int BASE_HP = 1000;
	public static final int BASE_DEFENSE = 10;
	public static final int BASE_ATTACK = 10;

	private int hp;
	private int defense;
	private float defenseMultiplier;
	private int attack;
	private float attackMultiplier;
	private float posX;
	private float posY;
	private float angle;
	private int counter1;
	private int counter2;
	private float boundingRadius;
	private Controller controller;
	private GameObjectList handler;

	/**
	 * Constructor
	 *
	 * @param controller the character gets his instructions from this controller
	 */
	public Character( Controller controller )
	{
		hp = BASE_HP;
		setDefense( BASE_DEFENSE );
		setAttack( BASE_ATTACK );

		posX = Game.WIDTH / 2;
		posY = Game.HEIGHT / 2;
		angle = (float) Math.PI;

		counter1 = 0;

		this.controller = controller;
	}

	/**
	 * set the defense stat
	 *
	 * @param defense stat value
	 * @return resulting defense multiplier
	 */
	public float setDefense( int defense )
	{
		this.defense = defense;
		defenseMultiplier = 1f / scaleFactor( defense );
		return defenseMultiplier;
	}

	/**
	 * set the attack stat
	 *
	 * @param attack attack value
	 * @return resulting attack multiplier
	 */
	public float setAttack( int attack )
	{
		this.attack = attack;
		attackMultiplier = scaleFactor( attack );
		return attackMultiplier;
	}

	/**
	 * Calculates the multipliers from the stat values
	 *
	 * @param a stat value
	 * @return corresponding multiplier
	 */
	private float scaleFactor( int a )
	{
		return a / 10f;
	}

	/**
	 * @return x coordinate of the position of the character
	 */
	public float getPosX()
	{
		return posX;
	}

	/**
	 * @return y coordinate of the position of the character
	 */
	public float getPosY()
	{
		return posY;
	}

	/**
	 * @return radius of the bounding sphere (used for collision detection)
	 */
	public float getBoundingRadius()
	{
		return boundingRadius;
	}

	/**
	 * @param boundingRadius radius of the bounding sphere (used for collision detection)
	 */
	public void setBoundingRadius( float boundingRadius )
	{
		this.boundingRadius = boundingRadius;
	}

	/**
	 * @return current health
	 */
	public int getHp()
	{
		return hp;
	}

	/**
	 * @return defense stat
	 */
	public int getDefense()
	{
		return defense;
	}

	/**
	 * @return attack stat
	 */
	public int getAttack()
	{
		return attack;
	}

	/**
	 * Calculates the outgoing damage of a skill
	 *
	 * @param skill the damage of this skill is calculated
	 * @return outgoing damage
	 */
	public int getOutputDamage( Skill skill )
	{
		return (int) ((BASE_DAMAGE + skill.getBaseDamagePlus()) * skill.getDamageMultiplier() * attackMultiplier);
	}

	/**
	 * Decreases the health depending on the incoming damage
	 *
	 * @param inputDamage incoming damage
	 * @return final damage deducted from health (after defense factors are applied)
	 */
	public int doDamage( int inputDamage )
	{
		int damage = getEffectiveDamage( inputDamage );

		if( hp > damage )
		{
			hp = hp - damage;
			return damage;
		} else
		{
			damage = hp;
			hp = 0;
			return damage;
		}
	}

	/**
	 * Calculates the final damage the character takes from the incoming damage
	 *
	 * @param inputDamage incoming damage
	 * @return final damage (after defense factors are applied)
	 */
	public int getEffectiveDamage( int inputDamage )
	{
		return (int) (inputDamage * defenseMultiplier);
	}

	/**
	 * @param i number of the skill
	 * @return its cooldown in 1/60000 seconds
	 */
	public int skillCooldown( int i )
	{
		if( i == 1 )
		{
			return counter1;
		}
		if( i == 2 )
		{
			return counter2;
		}
		return -1;
	}

	@Override
	public void tick()
	{
		// TODO
		if( counter1 > 0 )
			counter1--;

		if( counter2 > 0 )
			counter2--;

		Action action = controller.getAction();

		float vel = action.vel / 200f;
		angle += action.angle / 200f / 60;

		posX -= vel * Math.sin( angle );
		posY += vel * Math.cos( angle );

		posX = Game.clamp( posX, 15, Game.WIDTH - 15 );
		posY = Game.clamp( posY, 15, Game.HEIGHT - 15 );

		if( action.useSkill && action.skillNr == 1 && counter1 == 0 )
		{
			counter1 = 30000;
			handler.add( new EffectProjectile( posX - 15 * (float) Math.sin( angle ), posY + 15 * (float) Math.cos( angle ), angle, 10, 1, 1 / 50f, Color.blue ) );
		}

		if( action.useSkill && action.skillNr == 2 && counter2 == 0 )
		{
			counter2 = 600000;
			handler.add( new EffectRay( posX - 25 * (float) Math.sin( angle ), posY + 25 * (float) Math.cos( angle ), angle, 10, 1, vel * 2, 6000, Color.blue ) );
		}
	}

	@Override
	public void render( Graphics g )
	{
		// TODO
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor( Color.white );
		Rectangle r1 = new Rectangle( -5, 10, 10, 5 );
		Rectangle r2 = new Rectangle( -15, -15, 30, 25 );
		Path2D.Double path = new Path2D.Double();
		path.append( r1, false );
		path.append( r2, false );
		AffineTransform t = new AffineTransform();
		t.rotate( angle );
		path.transform( t );
		AffineTransform t2 = new AffineTransform();
		t2.translate( posX, posY );
		path.transform( t2 );
		g2.fill( path );
	}

	@Override
	public void setHandler( GameObjectList list )
	{
		handler = list;
	}
}
