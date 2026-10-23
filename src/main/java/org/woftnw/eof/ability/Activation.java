package org.woftnw.eof.ability;

public enum Activation {
	SNEAK_USE("key.sneak", "key.use"),
	USE("key.use"),
	MELEE("key.attack");

	private final String[] KEYBINDS;

	Activation(String... keybinds) {
		KEYBINDS = keybinds;
	}

	/**
	 * Get the keybinds of this activation type.
	 *
	 * @return The list of keybinds this activation type uses.
	 */
	public String[] getKeybinds() {
		return KEYBINDS;
	}
}
