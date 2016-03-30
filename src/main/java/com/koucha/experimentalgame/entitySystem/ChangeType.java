package com.koucha.experimentalgame.entitySystem;


public enum ChangeType
{
	/** new Entity was added */
	Add,

	/** Component(s) added to Entity */
	Addition,

	/** Component(s) removed from Entity */
	Deletion,

	/** Entity should be removed from all Systems */
	Remove;
}
