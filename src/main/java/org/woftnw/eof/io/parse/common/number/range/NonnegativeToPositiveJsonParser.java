package org.woftnw.eof.io.parse.common.number.range;

import org.woftnw.eof.io.parse.common.number.NumberJsonParser;
import org.woftnw.eof.io.parse.common.number.NonnegativeIntJsonParser;
import org.woftnw.eof.io.parse.common.number.PositiveIntJsonParser;

/**
 * Parses a nonnegative-to-positive JSON range object.
 */
public class NonnegativeToPositiveJsonParser extends RangeJsonParser {

	@Override
	protected NumberJsonParser<Integer> getMinParser() {
		return new NonnegativeIntJsonParser();
	}

	@Override
	protected NumberJsonParser<Integer> getMaxParser() {
		return new PositiveIntJsonParser();
	}
}
