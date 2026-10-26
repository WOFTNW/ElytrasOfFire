package org.woftnw.eof.io.parse.common.number;

import com.google.gson.JsonElement;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.exception.parse.number.EoFNumberJsonParseException;
import org.woftnw.eof.io.parse.JsonParser;

/**
 * Parses a number in some way.
 *
 * @param <T> The type of number to return.
 */
public interface NumberJsonParser<T extends Number> extends JsonParser<T> {
	@Override
	T parse(@NonNull JsonElement element, ElytrasOfFire plugin) throws EoFNumberJsonParseException;
}
