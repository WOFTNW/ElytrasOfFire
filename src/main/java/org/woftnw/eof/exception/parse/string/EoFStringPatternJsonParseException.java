package org.woftnw.eof.exception.parse.string;

/**
 * Thrown when a string value does not match any allowed patterns.
 */
public class EoFStringPatternJsonParseException extends EoFStringJsonParseException {
	/**
	 * The allowed patterns.
	 */
	private final String[] ALLOWED_PATTERNS;

	/**
	 * Create an EoFStringPatternJsonParseException.
	 *
	 * @param allowedPatterns The allowed patterns.
	 */
	public EoFStringPatternJsonParseException(String[] allowedPatterns) {
		super();
		ALLOWED_PATTERNS = allowedPatterns;
	}

	/**
	 * Create an EoFStringPatternJsonParseException.
	 *
	 * @param allowedPattern The required pattern.
	 */
	public EoFStringPatternJsonParseException(String allowedPattern) {
		super();
		ALLOWED_PATTERNS = new String[]{allowedPattern};
	}

	/**
	 * Get the allowed patterns.
	 *
	 * @return The allowed patterns.
	 */
	public String[] getAllowedPatterns() {
		return ALLOWED_PATTERNS;
	}

	@Override
	public String getMessage() {
		if (ALLOWED_PATTERNS.length == 1) {
			return String.format("Key does not match required pattern \"%s\".", ALLOWED_PATTERNS[0]);
		}
		return String.format("Key does not match allowed patterns \"%s\".", String.join(", ", ALLOWED_PATTERNS));
	}
}
