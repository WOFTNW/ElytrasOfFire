package org.woftnw.eof.exception.parse;

public class EoFRequiredKeyNullJsonParseException extends EoFJsonParseException {
	public EoFRequiredKeyNullJsonParseException() {
		super();
	}

	@Override
	public String getMessage() {
		return "Key is required but value is null.";
	}
}
