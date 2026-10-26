package org.woftnw.eof.exception.parse.number;

import org.jspecify.annotations.NonNull;

/**
 * Thrown when a numeric value is outside the permitted range.
 */
public class EoFNumberValueJsonParseException extends EoFNumberJsonParseException {
	/**
	 * The minimum value.
	 */
	private final Number MIN;
	/**
	 * The maximum value.
	 */
	private final Number MAX;

	/**
	 * Create an EoFNumberValueJsonParseException.
	 *
	 * @param min The minimum value.
	 * @param max The maximum value.
	 */
	public EoFNumberValueJsonParseException(@NonNull Number min, @NonNull Number max) {
		super();
		MIN = min;
		MAX = max;
	}

	/**
	 * Get the minimum value.
	 *
	 * @return The minimum value.
	 */
	public @NonNull Number getMin() {
		return MIN;
	}

	/**
	 * Get the maximum value.
	 *
	 * @return The miaximum value.
	 */
	public @NonNull Number getMax() {
		return MAX;
	}

	@Override
	public String getMessage() {
		return String.format("Key value must be between %s and %s.", MIN, MAX);
	}
}
