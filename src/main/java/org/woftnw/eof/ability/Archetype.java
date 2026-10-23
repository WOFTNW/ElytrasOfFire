package org.woftnw.eof.ability;

/**
 * Represents an active ability archetype.
 */
public enum Archetype {
	PRIMARY("Primary"),
	SECONDARY("Secondary"),
	TERTIARY("Tertiary");

	private final String NAME;

	Archetype(String name) {
		NAME = name;
	}

	/**
	 * Get the name of this Archetype.
	 *
	 * @return The name as a string.
	 */
	public String getName() {
		return NAME;
	}
}
