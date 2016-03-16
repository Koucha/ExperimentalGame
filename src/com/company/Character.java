package com.company;

import com.company.effects.EffectPojectile;
import com.company.effects.EffectRay;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

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
	private float posx;
	private float posy;
	private float angle;
	private int counter1;
	private int counter2;
	private float boundingRadius;
	private Controller controller;
	private GameObjectList handler;

	public Character( Controller controller )
	{
		hp = BASE_HP;
		setDefense( BASE_DEFENSE );
		setAttack( BASE_ATTACK );

		posx = Game.WIDTH / 2;
		posy = Game.HEIGHT / 2;
		angle = (float) Math.PI;

		counter1 = 0;

		this.controller = controller;
	}

	@Override
	public void setHandler( GameObjectList list )
	{
		handler = list;
	}

	public float getPosx()
	{
		return posx;
	}

	public float getPosy()
	{
		return posy;
	}

	public float getBoundingRadius()
	{
		return boundingRadius;
	}

	public void setBoundingRadius( float boundingRadius )
	{
		this.boundingRadius = boundingRadius;
	}

	public int getHp()
	{
		return hp;
	}

	public float setDefense( int defense )
	{
		this.defense = defense;
		defenseMultiplier = 1f / scaleFactor( defense );
		return defenseMultiplier;
	}

	public int getDefense()
	{
		return defense;
	}

	public float setAttack( int attack )
	{
		this.attack = attack;
		attackMultiplier = scaleFactor( attack );
		return attackMultiplier;
	}

	public int getAttack()
	{
		return attack;
	}

	public int getOutputDamage( Skill skill )
	{
		return (int) ((BASE_DAMAGE + skill.getBaseDamagePlus()) * skill.getDamageMultiplier() * attackMultiplier);
	}

	public int getEffectiveDamage( int inputDamage )
	{
		return (int) (inputDamage * defenseMultiplier);
	}

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

	private float scaleFactor( int a )
	{
		return a / 10f;
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

		posx -= vel * Math.sin( angle );
		posy += vel * Math.cos( angle );

		posx = Game.clamp( posx, 15, Game.WIDTH - 15 );
		posy = Game.clamp( posy, 15, Game.HEIGHT - 15 );

		if( action.useSkill && action.skillNr == 1 && counter1 == 0 )
		{
			counter1 = 30000;
			handler.add( new EffectPojectile( posx - 15 * (float) Math.sin( angle ), posy + 15 * (float) Math.cos( angle ), angle, 10, 1, 1 / 50f, Color.blue ) );
		}

		if( action.useSkill && action.skillNr == 2 && counter2 == 0 )
		{
			counter2 = 600000;
			handler.add( new EffectRay( posx - 25 * (float) Math.sin( angle ), posy + 25 * (float) Math.cos( angle ), angle, 10, 1, vel * 2, 6000, Color.blue ) );
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
		t2.translate( posx, posy );
		path.transform( t2 );
		g2.fill( path );
	}
}
