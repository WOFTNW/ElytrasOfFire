package org.woftnw.eof.exception.parse;

public class EoFWrappedJsonParseException extends EoFJsonParseException {
	private final EoFJsonParseException EXCEPTION;
	private final String KEY;

	public EoFWrappedJsonParseException(String key, EoFJsonParseException exception) {
		EXCEPTION = exception;
		KEY = key;
	}

	public EoFJsonParseException getException() {
		return EXCEPTION;
	}

	public String getKey() {
		return KEY;
	}

	@Override
	public String getMessage() {
		return String.format("Exception on key \"%s\": %s", KEY, EXCEPTION.getMessage());
	}
}
