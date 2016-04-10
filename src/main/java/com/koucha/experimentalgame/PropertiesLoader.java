package com.koucha.experimentalgame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * loads localized properties files
 */
public class PropertiesLoader
{
	private ClassLoader loader;

	public PropertiesLoader()
	{
		File file = new File( "./resources/localisation" );
		URL[] urls = new URL[0];
		try
		{
			urls = new URL[]{file.toURI().toURL()};
		} catch( MalformedURLException e )
		{
			e.printStackTrace();
		}
		loader = new URLClassLoader( urls );
	}

	/**
	 * @param bundle subpath as package after ./resources/localisation
	 * @return the bundle
	 */
	public ResourceBundle loadBundle( String bundle )
	{
		return loadBundle( bundle, Locale.getDefault() );
	}

	/**
	 * @param bundle subpath as package after ./resources/localisation
	 * @param locale a custom locale
	 * @return the bundle
	 */
	public ResourceBundle loadBundle( String bundle, Locale locale )
	{
		return ResourceBundle.getBundle( bundle, locale, loader );
	}

	public Properties loadPropertiesXML( String name )
	{
		try
		{
			File file = new File( "./resources/properties/" + name + ".xml" );
			FileInputStream fileInput = new FileInputStream( file );
			Properties properties = new Properties();
			properties.loadFromXML( fileInput );
			fileInput.close();
			return properties;
		} catch( IOException e )
		{
			e.printStackTrace();
		}
		return null;
	}

	public void savePropertiesXML( String name, Properties properties )
	{
		try
		{
			File file = new File( "./resources/properties/" + name + ".xml" );
			FileOutputStream fileOut = new FileOutputStream( file );
			properties.storeToXML( fileOut, name );
			fileOut.close();
		} catch( IOException e )
		{
			e.printStackTrace();
		}
	}

	public Properties loadProperties( String name )
	{
		try
		{
			File file = new File( "./resources/properties/" + name + ".properties" );
			FileInputStream fileInput = new FileInputStream( file );
			Properties properties = new Properties();
			properties.load( fileInput );
			fileInput.close();
			return properties;
		} catch( IOException e )
		{
			e.printStackTrace();
		}
		return null;
	}

	public void saveProperties( String name, Properties properties )
	{
		try
		{
			File file = new File( "./resources/properties/" + name + ".properties" );
			FileOutputStream fileOut = new FileOutputStream( file );
			properties.store( fileOut, name );
			fileOut.close();
		} catch( IOException e )
		{
			e.printStackTrace();
		}
	}
}
