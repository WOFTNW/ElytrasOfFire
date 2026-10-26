package org.woftnw.eof.exception.parse;

public class EoFNotInEnumJsonParseException extends EoFJsonParseException {
	final String[] ALLOWED_VALUES;

	public EoFNotInEnumJsonParseException(String[] allowedValues) {
		this.ALLOWED_VALUES = allowedValues;
	}

	public String[] getAllowedValues() {
		return ALLOWED_VALUES;
	}

	@Override
	public String getMessage() {
		return String.format("Key may only be one of: %s", String.join(", ", ALLOWED_VALUES));
	}
}
