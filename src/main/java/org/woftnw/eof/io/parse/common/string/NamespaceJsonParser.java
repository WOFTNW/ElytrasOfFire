package org.woftnw.eof.io.parse.common.string;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import org.bukkit.NamespacedKey;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.exception.parse.string.EoFStringLengthJsonParseException;
import org.woftnw.eof.exception.parse.string.EoFStringPatternJsonParseException;
import org.woftnw.eof.io.parse.JsonParser;

public class NamespaceJsonParser implements JsonParser<NamespacedKey> {
	final int MIN_LENGTH = 3;
	final String PATTERN_1 = "^[^:]+:[^:]+$";
	final String PATTERN_2 = "^[a-z_-]+:[a-z_-]+$";

	@Override
	public NamespacedKey parse(@NonNull JsonElement element, ElytrasOfFire plugin) throws EoFStringLengthJsonParseException, EoFStringPatternJsonParseException {
		final String STRING = element.getAsString();
		if (STRING.length() < MIN_LENGTH) {
			throw new EoFStringLengthJsonParseException(MIN_LENGTH, null);
		} else if (!STRING.matches(PATTERN_1)) {
			throw new EoFStringPatternJsonParseException(PATTERN_1);
		} else if (!STRING.matches(PATTERN_2)) {
			throw new EoFStringPatternJsonParseException(PATTERN_2);
		}

		final String[] SPLIT = STRING.split(":");
		if (SPLIT.length < 2) throw new JsonSyntaxException("Key is not a namespace key. Use format \"namespace:key\".");
		final String NAMESPACE = SPLIT[0];
		final String KEY = SPLIT[1];

		return new NamespacedKey(NAMESPACE, KEY);
	}
}
