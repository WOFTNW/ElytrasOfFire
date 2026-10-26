package org.woftnw.eof.exception.parse;

/**
 * Thrown when a string value does not match a set of allowed values.
 */
public class EoFNotInEnumJsonParseException extends EoFJsonParseException {
	/**
	 * The allowed values.
	 */
	private final String[] ALLOWED_VALUES;

	/**
	 * Create an EoFNotInEnumJsonParseException.
	 *
	 * @param allowedValues The allowed values.
	 */
	public EoFNotInEnumJsonParseException(String[] allowedValues) {
		this.ALLOWED_VALUES = allowedValues;
	}

	/**
	 * Get the allowed values.
	 * @return The allowed values.
	 */
	public String[] getAllowedValues() {
		return ALLOWED_VALUES;
	}

	@Override
	public String getMessage() {
		return String.format("Key may only be one of: %s", String.join(", ", ALLOWED_VALUES));
	}
}
