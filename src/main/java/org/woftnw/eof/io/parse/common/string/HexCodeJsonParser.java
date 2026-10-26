package org.woftnw.eof.io.parse.common.string;

/**
 * Parses a JSON string as a color hex code.
 */
public class HexCodeJsonParser extends StringToStringJsonParser {
	private final static String[] ANY_OF_PATTERNS = new String[]{
			"^#[0-9A-Fa-f]{6}$",
			"^#[0-9A-Fa-f]{8}$"
	};
	private final static String[] ALL_OF_PATTERNS = new String[0];

	/**
	 * Create a SnakeCaseJsonParser.
	 */
	public HexCodeJsonParser() {
		super(ANY_OF_PATTERNS, ALL_OF_PATTERNS);
	}
}
