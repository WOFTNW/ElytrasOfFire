package org.woftnw.eof.io.parse;

import com.google.gson.JsonElement;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.exception.parse.EoFJsonParseException;

public interface JsonParser<T> {
	T parse(@NonNull JsonElement element, ElytrasOfFire plugin) throws EoFJsonParseException;
}
