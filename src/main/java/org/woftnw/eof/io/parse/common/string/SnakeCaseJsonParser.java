package org.woftnw.eof.io.parse.common.string;

/**
 * Parses a snake-case JSON string.
 */
public class SnakeCaseJsonParser extends StringToStringJsonParser {
	private final static String PATTERN = "^[a-z0-9_]+$";

	/**
	 * Create a SnakeCaseJsonParser.
	 */
	public SnakeCaseJsonParser() {
		super(PATTERN);
	}
}
