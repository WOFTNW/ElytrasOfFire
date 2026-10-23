package org.woftnw.eof.exception;

/**
 * This exception is thrown when an ability is attempted to be activated while on cooldown.
 */
public class AbilityCooldownException extends Exception {
	private final long COOLDOWN_TIME_END;

	/**
	 * Create an AbilityCooldownException.
	 *
	 * @param cooldownTimeEnds The game time at which the cooldown will end.
	 */
	public AbilityCooldownException(long cooldownTimeEnds) {
		COOLDOWN_TIME_END = cooldownTimeEnds;
	}

	/**
	 * Get the game time at which the cooldown will end.
	 *
	 * @return The game time at which the cooldown will end.
	 */
	public long getCooldownTimeLeft() {
		return COOLDOWN_TIME_END;
	}
}
