package com.koucha.experimentalgame;

/**
 * Controller provide instructions to {@link Character} objects
 */
public interface Controller
{
	/**
	 * @return instruction to be done by character
	 */
	Action getAction();
}
