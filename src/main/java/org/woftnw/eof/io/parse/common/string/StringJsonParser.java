package org.woftnw.eof.io.parse.common.string;

import com.google.gson.JsonElement;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.exception.parse.string.EoFStringJsonParseException;
import org.woftnw.eof.io.parse.JsonParser;

public interface StringJsonParser<T> extends JsonParser<T> {
	@Override
	T parse(@NonNull JsonElement element, ElytrasOfFire plugin) throws EoFStringJsonParseException;
}
