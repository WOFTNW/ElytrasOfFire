package org.woftnw.eof.exception.parse.string;

import org.jspecify.annotations.Nullable;

/**
 * Thrown when a string value is shorter or longer than permitted.
 */
public class EoFStringLengthJsonParseException extends EoFStringJsonParseException {
	/**
	 * The minimum string length.
	 */
	private final int MIN_LENGTH;
	/**
	 * The maximum string length.
	 */
	private final int MAX_LENGTH;

	/**
	 * Create an EoFStringLengthJsonParseException.
	 *
	 * @param minLength The minimum string length.
	 * @param maxLength The maximum string length.
	 */
	public EoFStringLengthJsonParseException(@Nullable Integer minLength, @Nullable Integer maxLength) {
		super();
		if (minLength == null) MIN_LENGTH = Integer.MIN_VALUE;
		else MIN_LENGTH = minLength;
		if (maxLength == null) MAX_LENGTH = Integer.MAX_VALUE;
		else MAX_LENGTH = maxLength;
	}

	/**
	 * Get the maximum string length.
	 *
	 * @return The maximum string length.
	 */
	public int getMaxLength() {
		return MAX_LENGTH;
	}

	/**
	 * Get the minimum string length.
	 *
	 * @return The minimum string length.
	 */
	public int getMinLength() {
		return MIN_LENGTH;
	}

	@Override
	public String getMessage() {
		return String.format("Key value length must be between %s and %s characters.", MIN_LENGTH, MAX_LENGTH);
	}
}
