package org.woftnw.eof;

import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class TribeUtility {

	/**
	 * Get a Tribe from a PCD value.
	 *
	 * @param value The value as stored in the PCD.
	 * @return The corresponding Tribe or null of no match.
	 */
	public static @Nullable Tribe getTribeFromPDCValue(String value) {
		for (Tribe tribe : Tribe.values()) {
			if (Objects.equals(tribe.getPDCValue(), value)) {
				return tribe;
			}
		}
		return null;
	}
}
