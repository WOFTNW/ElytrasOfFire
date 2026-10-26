package org.woftnw.eof.exception.parse.string;

public class EoFStringPatternJsonParseException extends EoFStringJsonParseException {
	final String PATTERN;

	public EoFStringPatternJsonParseException(String pattern) {
		super();
		PATTERN = pattern;
	}

	@Override
	public String getMessage() {
		return String.format("Key does not match required pattern \"%s\".", PATTERN);
	}
}
