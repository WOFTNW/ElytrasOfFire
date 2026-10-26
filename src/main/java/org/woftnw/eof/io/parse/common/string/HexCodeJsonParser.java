package org.woftnw.eof.io.parse.common.string;

import com.google.gson.JsonElement;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.exception.parse.string.EoFStringLengthJsonParseException;
import org.woftnw.eof.exception.parse.string.EoFStringPatternJsonParseException;

public class HexCodeJsonParser implements StringJsonParser<String> {
	@Override
	public String parse(@NonNull JsonElement element, ElytrasOfFire plugin) throws EoFStringLengthJsonParseException, EoFStringPatternJsonParseException {
		final String PATTERN_1 = "^#[0-9A-Fa-f]{6}$";
		final String PATTERN_2 = "^#[0-9A-Fa-f]{8}$";

		final String STRING = element.getAsString();
		if (STRING.isEmpty()) {
			throw new EoFStringLengthJsonParseException(1, null);
		} else if (!STRING.matches(PATTERN_1)) {
			throw new EoFStringPatternJsonParseException(PATTERN_1);
		} else if (!STRING.matches(PATTERN_2)) {
			throw new EoFStringPatternJsonParseException(PATTERN_2);
		}

		return STRING;
	}
}
