package org.woftnw.eof.exception.parse;

/**
 * Thrown when a JSON parsing exception occurs while parsing or validating a property of the current element.
 */
public class EoFWrappedJsonParseException extends EoFJsonParseException {
	/**
	 * The exception that occurred while parsing or validating the property.
	 */
	private final EoFJsonParseException EXCEPTION;
	/**
	 * The key of the property that failed to parse or validate.
	 */
	private final String KEY;

	/**
	 * Create an EoFWrappedJsonParseException.
	 *
	 * @param key       The key of the property that failed to parse or validate.
	 * @param exception The exception that occurred while parsing or validating the property.
	 */
	public EoFWrappedJsonParseException(String key, EoFJsonParseException exception) {
		EXCEPTION = exception;
		KEY = key;
	}

	/**
	 * Get the exception that occurred while parsing or validating the property.
	 *
	 * @return The exception that occurred while parsing or validating the property.
	 */
	public EoFJsonParseException getException() {
		return EXCEPTION;
	}

	/**
	 * Get the key of the property that failed to parse or validate.
	 *
	 * @return The key of the property that failed to parse or validate.
	 */
	public String getKey() {
		return KEY;
	}

	@Override
	public String getMessage() {
		return String.format("Exception on key \"%s\": %s", KEY, EXCEPTION.getMessage());
	}
}
