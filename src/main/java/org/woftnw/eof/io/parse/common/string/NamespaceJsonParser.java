package org.woftnw.eof.io.parse.common.string;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import org.bukkit.NamespacedKey;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.exception.parse.EoFJsonParseException;

/**
 * Parses a JSON string to a {@link NamespacedKey}.
 */
public class NamespaceJsonParser extends StringJsonParser<NamespacedKey> {
	private static final int MIN_LENGTH = 3;
	private static final int MAX_LENGTH = DEFAULT_MAX_LENGTH;
	private static final String[] ANY_OF_PATTERNS = new String[0];
	private static final String[] ALL_OF_PATTERNS = new String[]{
			"^[^:]+:[^:]+$",
			"^[a-z_-]+:[a-z_-]+$"
	};

	/**
	 * Create a NamespaceJsonParser.
	 */
	public NamespaceJsonParser() {
		super(MIN_LENGTH, MAX_LENGTH, ANY_OF_PATTERNS, ALL_OF_PATTERNS);
	}

	@Override
	public NamespacedKey parse(@NonNull JsonElement element, ElytrasOfFire plugin) throws EoFJsonParseException {
		final String STRING = new StringToStringJsonParser(MIN_LENGTH, MAX_LENGTH, ANY_OF_PATTERNS, ALL_OF_PATTERNS).parse(element, plugin);

		final String[] SPLIT = STRING.split(":");
		if (SPLIT.length < 2)
			throw new JsonSyntaxException("Key is not a namespace key. Use format \"namespace:key\".");
		final String NAMESPACE = SPLIT[0];
		final String KEY = SPLIT[1];

		return new NamespacedKey(NAMESPACE, KEY);
	}
}
