package org.woftnw.eof.exception;

/**
 * Thrown when a tribe was attempted to be found by {@link org.bukkit.NamespacedKey} but no matching tribe was found.
 *
 * This could happen if the tribe a player joined was removed.
 */
public class TribeNotFoundException extends RuntimeException {
	public TribeNotFoundException(String tribeString) {
		super(String.format("Tribe %s was not found!", tribeString));
	}
}
