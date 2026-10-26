package org.woftnw.eof.exception.parse.string;

import org.jspecify.annotations.Nullable;

public class EoFStringLengthJsonParseException extends EoFStringJsonParseException {
	private final int MIN_LENGTH;
	private final int MAX_LENGTH;

	public EoFStringLengthJsonParseException(@Nullable Integer minLength, @Nullable Integer maxLength) {
		super();
		if (minLength == null) MIN_LENGTH = Integer.MIN_VALUE;
		else MIN_LENGTH = minLength;
		if (maxLength == null) MAX_LENGTH = Integer.MAX_VALUE;
		else MAX_LENGTH = maxLength;
	}

	public int getMaxLength() {
		return MAX_LENGTH;
	}

	public int getMinLength() {
		return MIN_LENGTH;
	}

	@Override
	public String getMessage() {
		return String.format("Key value length must be between %s and %s characters.", MIN_LENGTH, MAX_LENGTH);
	}
}
