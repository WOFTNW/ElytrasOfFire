package org.woftnw.eof.io.parse.common.string;

import com.google.gson.JsonElement;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.exception.parse.string.EoFStringLengthJsonParseException;
import org.woftnw.eof.exception.parse.string.EoFStringPatternJsonParseException;
import org.woftnw.eof.io.parse.JsonParser;

public class NaturalCaseJsonParser implements JsonParser<String> {
	@Override
	public String parse(@NonNull JsonElement element, ElytrasOfFire plugin) throws EoFStringLengthJsonParseException, EoFStringPatternJsonParseException {
		final String PATTERN = "^.+$";

		final String STRING = element.getAsString();
		if (STRING.isEmpty()) {
			throw new EoFStringLengthJsonParseException(1, null);
		} else if (!STRING.matches(PATTERN)) {
			throw new EoFStringPatternJsonParseException(PATTERN);
		}

		return STRING;
	}
}
