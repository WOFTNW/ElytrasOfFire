package org.woftnw.eof.io.parse.common.number.range;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.exception.parse.EoFRequiredKeyNullJsonParseException;
import org.woftnw.eof.exception.parse.EoFWrappedJsonParseException;
import org.woftnw.eof.exception.parse.number.EoFNumberJsonParseException;
import org.woftnw.eof.io.parse.JsonParser;
import org.woftnw.eof.io.parse.common.number.NumberJsonParser;

/**
 * Parses a JSON range object.
 */
public abstract class RangeJsonParser implements JsonParser<int[]> {
	@Override
	public int[] parse(@NonNull JsonElement element, ElytrasOfFire plugin) throws EoFWrappedJsonParseException, UnsupportedOperationException {
		final JsonObject ROOT = element.getAsJsonObject();

		String key = "min";
		JsonElement MIN_ELEMENT = ROOT.get(key);
		if (MIN_ELEMENT == null)
			throw new EoFWrappedJsonParseException(key, new EoFRequiredKeyNullJsonParseException());
		int min;
		try {
			min = getMinParser().parse(MIN_ELEMENT, plugin);
		} catch (EoFNumberJsonParseException | UnsupportedOperationException e) {
			throw new EoFWrappedJsonParseException(key, e);
		}

		key = "max";
		JsonElement MAX_ELEMENT = ROOT.get(key);
		if (MAX_ELEMENT == null)
			throw new EoFWrappedJsonParseException(key, new EoFRequiredKeyNullJsonParseException());
		int max;
		try {
			max = getMaxParser().parse(MIN_ELEMENT, plugin);
		} catch (EoFNumberJsonParseException | UnsupportedOperationException e) {
			throw new EoFWrappedJsonParseException(key, e);
		}

		return new int[]{min, max};
	}

	/**
	 * Get the parser to use for the minimum number.
	 *
	 * @return The {@link NumberJsonParser} to use.
	 * @see org.woftnw.eof.io.parse.common.number.PositiveIntJsonParser
	 * @see org.woftnw.eof.io.parse.common.number.NegativeIntJsonParser
	 * @see org.woftnw.eof.io.parse.common.number.NonnegativeIntJsonParser
	 * @see org.woftnw.eof.io.parse.common.number.NonpositiveIntJsonParser
	 */
	protected abstract NumberJsonParser<Integer> getMinParser();

	/**
	 * Get the parser to use for the maximum number.
	 *
	 * @return The {@link NumberJsonParser} to use.
	 * @see org.woftnw.eof.io.parse.common.number.PositiveIntJsonParser
	 * @see org.woftnw.eof.io.parse.common.number.NegativeIntJsonParser
	 * @see org.woftnw.eof.io.parse.common.number.NonnegativeIntJsonParser
	 * @see org.woftnw.eof.io.parse.common.number.NonpositiveIntJsonParser
	 */
	protected abstract NumberJsonParser<Integer> getMaxParser();

}