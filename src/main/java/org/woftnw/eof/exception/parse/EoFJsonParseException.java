package org.woftnw.eof.exception.parse;

/**
 * Represents a generic exception while parsing JSON data.
 */
public abstract class EoFJsonParseException extends Exception {
	@Override
	public String getMessage() {
		return "An unknown error occurred while parsing JSON data.";
	}
}
