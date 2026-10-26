package org.woftnw.eof.io.parse.common.number;

import com.google.gson.JsonElement;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.exception.parse.number.EoFNumberValueJsonParseException;

/**
 * Parses a negative integer.
 */
public class NegativeIntJsonParser implements NumberJsonParser<Integer> {
	@Override
	public Integer parse(@NonNull JsonElement element, ElytrasOfFire plugin) throws EoFNumberValueJsonParseException, UnsupportedOperationException {
		final int INTEGER = element.getAsInt();
		if (INTEGER > -1) throw new EoFNumberValueJsonParseException(Integer.MIN_VALUE, -1);
		return INTEGER;
	}
}
