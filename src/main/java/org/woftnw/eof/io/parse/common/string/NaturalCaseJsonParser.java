package org.woftnw.eof.io.parse.common.string;

/**
 * Parses a natural-case JSON string.
 */
public class NaturalCaseJsonParser extends StringToStringJsonParser {
	private final static String PATTERN = "^.+$";

	/**
	 * Create a NaturalCaseJsonParser.
	 */
	public NaturalCaseJsonParser() {
		super(PATTERN);
	}
}
