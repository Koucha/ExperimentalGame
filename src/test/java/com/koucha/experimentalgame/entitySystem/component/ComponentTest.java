package com.koucha.experimentalgame.entitySystem.component;

import com.koucha.experimentalgame.entitySystem.Component;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Asserts Component classes are well defined
 */
public class ComponentTest
{
	private static final boolean Public = true;
	@SuppressWarnings( "unused" )
	private static final boolean Package = false;
	private static final boolean Default = Public;

	@Test
	public void AttachedCameraWellDefined() throws Exception
	{
		wellDefined( new AttachedCamera() );
	}

	static private void wellDefined( Component component )
	{
		wellDefined( component, Default );
	}

	static private void wellDefined( final Component component, boolean isPublic )
	{
		final Class< ? > clazz = component.getClass();

		final int modifiers = clazz.getModifiers();
		if( isPublic )
		{
			if( !Modifier.isPublic( modifiers ) )
				throw new AssertionError( MessageFormat.format( "Class \"{0}\" is not public.", clazz.getSimpleName() ) );
		} else
		{
			if( !Modifier.isPublic( modifiers ) )
				throw new AssertionError( MessageFormat.format( "Class \"{0}\" is not package private.", clazz.getSimpleName() ) );
		}

		if( Modifier.isAbstract( modifiers ) )
		{
			throw new AssertionError( MessageFormat.format( "Class \"{0}\" should not be abstract.", clazz.getSimpleName() ) );
		}
		if( Modifier.isFinal( modifiers ) )
		{
			throw new AssertionError( MessageFormat.format( "Class \"{0}\" should not be final.", clazz.getSimpleName() ) );
		}
		if( Modifier.isInterface( modifiers ) )
		{
			throw new AssertionError( MessageFormat.format( "\"{0}\" is not a Class but an Interface.", clazz.getSimpleName() ) );
		}
		if( Modifier.isStatic( modifiers ) )
		{
			throw new AssertionError( MessageFormat.format( "Class \"{0}\" should not be static.", clazz.getSimpleName() ) );
		}
		if( clazz.isEnum() )
		{
			return;
		}

		final Method[] methods = clazz.getDeclaredMethods();

		if( methods.length != 1 )
		{
			StringBuilder methodsString = new StringBuilder();
			for( Method method : methods )
			{
				methodsString.append( method.getName() ).append( "\n" );
			}
			throw new AssertionError( MessageFormat.format( "Class \"{0}\" should have exactly one method.\ngetFlag is neccessary, declared are:\n{1}", clazz.getSimpleName(), methodsString ) );
		}

		if( !methods[0].getName().equals( "getFlag" ) )
		{
			throw new AssertionError( MessageFormat.format( "Class \"{0}\" should implement \"ComponentFlag getFlag()\" an not \"{1}\"", clazz.getSimpleName(), methods[0].getName() ) );
		}

		for( final Field field : clazz.getDeclaredFields() )
		{
			int fieldModifiers = field.getModifiers();
			if( !Modifier.isPublic( fieldModifiers ) )
			{
				throw new AssertionError( MessageFormat.format( "Field \"{1}\" of Class \"{0}\" not public.", clazz.getSimpleName(), field.getName() ) );
			}
		}

		Class< ? >[] innerClazzes = clazz.getDeclaredClasses();
		for( Class< ? > innerClazz : innerClazzes )
		{
			if( !innerClazz.isEnum() )
			{
				throw new AssertionError( MessageFormat.format( "Class \"{0}\" has inner Classes/Interfaces.", clazz.getSimpleName(), methods[0].getName() ) );
			}
		}

		assertNotNull( "Component \"" + component.getClass().getSimpleName() + "\" has no Flag", component.getFlag() );
		assertFalse( "Component \"" + component.getClass().getSimpleName() + "\" has an invalid mask", component.getMask().isZero() );
	}

	@Test
	public void AABBWellDefined() throws Exception
	{
		wellDefined( new AABB() );
	}

	@Test
	public void MeshWellDefined() throws Exception
	{
		wellDefined( new Mesh() );
	}

	@Test
	public void LocalPlayerWellDefined() throws Exception
	{
		wellDefined( new LocalPlayer() );
	}

	@Test
	public void PositionWellDefined() throws Exception
	{
		wellDefined( new Position() );
	}

	@Test
	public void VelocityWellDefined() throws Exception
	{
		wellDefined( new Velocity() );
	}

}