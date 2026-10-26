package org.woftnw.eof.exception.parse;

/**
 * Thrown when a required property is not defined.
 */
public class EoFRequiredKeyNullJsonParseException extends EoFJsonParseException {
	public EoFRequiredKeyNullJsonParseException() {
		super();
	}

	@Override
	public String getMessage() {
		return "Key is required but value is null.";
	}
}
