package org.woftnw.eof.io.parse.common.string;

import org.woftnw.eof.io.parse.JsonParser;

/**
 * Parses a JSON string in some way.
 *
 * @param <T> The type to parse to.
 */
public abstract class StringJsonParser<T> implements JsonParser<T> {
	/**
	 * The default minimum string length.
	 */
	protected static final int DEFAULT_MIN_LENGTH = 1;
	/**
	 * The default maximum string length.
	 */
	protected static final int DEFAULT_MAX_LENGTH = Integer.MAX_VALUE;

	/**
	 * The minimum string length.
	 */
	protected final int MIN_LENGTH;
	/**
	 * The maximum string length.
	 */
	protected final int MAX_LENGTH;
	/**
	 * The patterns that a given string must match one of. If this is an empty array, none will be needed.
	 */
	protected final String[] ANY_OF_PATTERNS;
	/**
	 * The patterns that a given string must match all of.
	 */
	protected final String[] ALL_OF_PATTERNS;

	/**
	 * Create a StringJsonParser with the default minimum and maximum lengths.
	 *
	 * @param minLength     The minimum string length.
	 * @param maxLength     The maximum string length.
	 * @param anyOfPatterns The patterns that a given string must match one of. If this is an empty array, none will be needed.
	 * @param allOfPatterns The patterns that a given string must match all of.
	 */
	protected StringJsonParser(int minLength, int maxLength, String[] anyOfPatterns, String[] allOfPatterns) {
		MIN_LENGTH = minLength;
		MAX_LENGTH = maxLength;
		ANY_OF_PATTERNS = anyOfPatterns;
		ALL_OF_PATTERNS = allOfPatterns;
	}

	/**
	 * Create a StringJsonParser with the default minimum and maximum lengths.
	 *
	 * @param minLength The minimum string length.
	 * @param maxLength The maximum string length.
	 * @param pattern   The pattern that a given string must match.
	 */
	protected StringJsonParser(int minLength, int maxLength, String pattern) {
		MIN_LENGTH = minLength;
		MAX_LENGTH = maxLength;
		ANY_OF_PATTERNS = new String[0];
		ALL_OF_PATTERNS = new String[]{pattern};
	}

	/**
	 * Create a StringJsonParser with the default minimum and maximum lengths.
	 *
	 * @param pattern The pattern that a given string must match.
	 */
	protected StringJsonParser(String pattern) {
		MIN_LENGTH = DEFAULT_MIN_LENGTH;
		MAX_LENGTH = DEFAULT_MAX_LENGTH;
		ANY_OF_PATTERNS = new String[0];
		ALL_OF_PATTERNS = new String[]{pattern};
	}

	/**
	 * Create a StringJsonParser with the default minimum and maximum lengths.
	 *
	 * @param anyOfPatterns The patterns that a given string must match one of. If this is an empty array, none will be needed.
	 * @param allOfPatterns The patterns that a given string must match all of.
	 */
	protected StringJsonParser(String[] anyOfPatterns, String[] allOfPatterns) {
		MIN_LENGTH = DEFAULT_MIN_LENGTH;
		MAX_LENGTH = DEFAULT_MAX_LENGTH;
		ANY_OF_PATTERNS = anyOfPatterns;
		ALL_OF_PATTERNS = allOfPatterns;
	}
}
