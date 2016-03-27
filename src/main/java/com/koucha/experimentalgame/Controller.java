package com.koucha.experimentalgame;

import com.koucha.experimentalgame.entity.Entity;

/**
 * Controller provide instructions to {@link Entity} objects
 */
public interface Controller
{
	/**
	 * @return instruction to be done by character
	 */
	Action getAction();
}
