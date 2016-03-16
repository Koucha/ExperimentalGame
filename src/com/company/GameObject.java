package com.company;

import java.awt.*;

public interface GameObject
{
	void tick();

	void render( Graphics g );

	void setHandler( GameObjectList list );
}
