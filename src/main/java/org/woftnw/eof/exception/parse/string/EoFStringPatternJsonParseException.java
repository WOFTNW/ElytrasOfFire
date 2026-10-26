package org.woftnw.eof.exception.parse.string;

/**
 * Thrown when a string value does not match a required pattern.
 */
public class EoFStringPatternJsonParseException extends EoFStringJsonParseException {
	/**
	 * The required pattern.
	 */
	private final String PATTERN;

	/**
	 * Create an EoFStringPatternJsonParseException.
	 *
	 * @param pattern The required pattern.
	 */
	public EoFStringPatternJsonParseException(String pattern) {
		super();
		PATTERN = pattern;
	}

	/**
	 * Get the required pattern.
	 *
	 * @return The required pattern.
	 */
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public String getMessage() {
		return String.format("Key does not match required pattern \"%s\".", PATTERN);
	}
}
