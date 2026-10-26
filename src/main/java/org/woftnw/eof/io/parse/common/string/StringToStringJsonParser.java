package org.woftnw.eof.io.parse.common.string;

import com.google.gson.JsonElement;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.exception.parse.EoFJsonParseException;
import org.woftnw.eof.exception.parse.string.EoFStringLengthJsonParseException;
import org.woftnw.eof.exception.parse.string.EoFStringPatternJsonParseException;

/**
 * Parses a JSON string in some way.
 */
public class StringToStringJsonParser extends StringJsonParser<String> {

	/**
	 * Create a StringToStringJsonParser.
	 *
	 * @param minLength     The minimum string length.
	 * @param maxLength     The maximum string length.
	 * @param anyOfPatterns The patterns that a given string must match one of. If this is an empty array, none will be needed.
	 * @param allOfPatterns The patterns that a given string must match all of.
	 */
	public StringToStringJsonParser(int minLength, int maxLength, String[] anyOfPatterns, String[] allOfPatterns) {
		super(minLength, maxLength, anyOfPatterns, allOfPatterns);
	}

	/**
	 * Create a StringToStringJsonParser with the default minimum and maximum lengths.
	 *
	 * @param minLength The minimum string length.
	 * @param maxLength The maximum string length.
	 * @param pattern   The pattern that a given string must match.
	 */
	public StringToStringJsonParser(int minLength, int maxLength, String pattern) {
		super(minLength, maxLength, pattern);
	}

	/**
	 * Create a StringJsonParser with the default minimum and maximum lengths.
	 *
	 * @param pattern The pattern that a given string must match.
	 */
	public StringToStringJsonParser(String pattern) {
		super(pattern);
	}

	/**
	 * Create a StringToStringJsonParser with the default minimum and maximum lengths.
	 *
	 * @param anyOfPatterns The patterns that a given string must match one of. If this is an empty array, none will be needed.
	 * @param allOfPatterns The patterns that a given string must match all of.
	 */
	public StringToStringJsonParser(String[] anyOfPatterns, String[] allOfPatterns) {
		super(anyOfPatterns, allOfPatterns);
	}

	/**
	 * Parse a JSON string.
	 *
	 * @param element The element to parse and validate.
	 * @param plugin  The Elytras of Fire plugin instance.
	 * @return The resulting string.
	 * @throws EoFStringLengthJsonParseException  Thrown if the string is empty.
	 * @throws EoFStringPatternJsonParseException Thrown if the string is not natural-case.
	 */
	@Override
	public String parse(@NonNull JsonElement element, ElytrasOfFire plugin) throws EoFJsonParseException {
		final String STRING = element.getAsString();

		if (STRING.length() < MIN_LENGTH || STRING.length() > MAX_LENGTH)
			throw new EoFStringLengthJsonParseException(MIN_LENGTH, MAX_LENGTH);

		if (ANY_OF_PATTERNS.length != 0) {
			boolean match = false;
			for (final String PATTERN : ANY_OF_PATTERNS) {
				if (STRING.matches(PATTERN)) {
					match = true;
					break;
				}
			}
			if (!match) throw new EoFStringPatternJsonParseException(ANY_OF_PATTERNS);
		}

		for (final String PATTERN : ALL_OF_PATTERNS) {
			if (!STRING.matches(PATTERN)) throw new EoFStringPatternJsonParseException(PATTERN);
		}

		return STRING;
	}
}
