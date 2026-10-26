package org.woftnw.eof.exception.parse.number;

import org.jspecify.annotations.NonNull;

public class EoFNumberValueJsonParseException extends EoFNumberJsonParseException {
	private final Number MIN;
	private final Number MAX;

	public EoFNumberValueJsonParseException(@NonNull Number min, @NonNull Number max) {
		super();
		MIN = min;
		MAX = max;
	}

	public @NonNull Number getMin() {
		return MIN;
	}

	public @NonNull Number getMax() {
		return MAX;
	}

	@Override
	public String getMessage() {
		return String.format("Key value must be between %s and %s.", MIN, MAX);
	}
}
