package org.woftnw.eof.io.parse.common.number.range;

import org.woftnw.eof.io.parse.common.number.NumberJsonParser;
import org.woftnw.eof.io.parse.common.number.NonnegativeIntJsonParser;
import org.woftnw.eof.io.parse.common.number.NonpositiveIntJsonParser;

public class NonpositiveToNonnegativeJsonParser extends RangeJsonParser {

	@Override
	protected NumberJsonParser<Integer> getMinParser() {
		return new NonpositiveIntJsonParser();
	}

	@Override
	protected NumberJsonParser<Integer> getMaxParser() {
		return new NonnegativeIntJsonParser();
	}
}
