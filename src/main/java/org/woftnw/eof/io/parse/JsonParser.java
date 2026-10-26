package org.woftnw.eof.io.parse;

import com.google.gson.JsonElement;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.exception.parse.EoFJsonParseException;

/**
 * Parses a JSON element to a specific type.
 * <p>
 * This is implemented by classes such as {@link org.woftnw.eof.io.parse.common.string.NamespaceJsonParser} and {@link org.woftnw.eof.io.parse.common.number.PositiveIntJsonParser} for validating complex data types not handled by GSON alone.
 * </p>
 *
 * @param <T> The type the element will be parsed to.
 */
public interface JsonParser<T> {

	/**
	 * Parse a given {@link JsonElement}.
	 *
	 * @param element The element to parse and validate.
	 * @param plugin The Elytras of Fire plugin instance.
	 * @return The parsed and validated object.
	 * @throws EoFJsonParseException If something goes wrong while parsing or validating.
	 */
	T parse(@NonNull JsonElement element, ElytrasOfFire plugin) throws EoFJsonParseException;
}
