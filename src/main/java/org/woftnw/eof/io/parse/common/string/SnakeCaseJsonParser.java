package org.woftnw.eof.io.parse.common.string;

import com.google.gson.JsonElement;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.exception.parse.string.EoFStringLengthJsonParseException;
import org.woftnw.eof.exception.parse.string.EoFStringPatternJsonParseException;

public class SnakeCaseJsonParser implements StringJsonParser<String> {
	@Override
	public String parse(@NonNull JsonElement element, ElytrasOfFire plugin) throws EoFStringLengthJsonParseException, EoFStringPatternJsonParseException {
		final String PATTERN = "^[a-z0-9_]+$";

		final String STRING = element.getAsString();
		if (STRING.isEmpty()) {
			throw new EoFStringLengthJsonParseException(1, null);
		} else if (!STRING.matches(PATTERN)) {
			throw new EoFStringPatternJsonParseException(PATTERN);
		}

		return STRING;
	}
}
