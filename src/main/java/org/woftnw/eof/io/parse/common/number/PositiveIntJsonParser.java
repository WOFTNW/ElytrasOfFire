package org.woftnw.eof.io.parse.common.number;

import com.google.gson.JsonElement;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.exception.parse.number.EoFNumberValueJsonParseException;

public class PositiveIntJsonParser implements NumberJsonParser<Integer> {
	@Override
	public Integer parse(@NonNull JsonElement element, ElytrasOfFire plugin) throws EoFNumberValueJsonParseException {
		final int INTEGER = element.getAsInt();
		if (INTEGER < 1) throw new EoFNumberValueJsonParseException(1, Integer.MAX_VALUE);
		return INTEGER;
	}
}
