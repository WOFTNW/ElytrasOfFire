package org.woftnw.eof.io.parse.common.number;

import com.google.gson.JsonElement;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.exception.parse.number.EoFNumberJsonParseException;
import org.woftnw.eof.io.parse.JsonParser;

public interface NumberJsonParser<T extends Number> extends JsonParser<T> {
	@Override
	T parse(@NonNull JsonElement element, ElytrasOfFire plugin) throws EoFNumberJsonParseException;
}
