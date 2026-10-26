package org.woftnw.eof.io.parse.common.number.range;

import org.woftnw.eof.io.parse.common.number.NumberJsonParser;
import org.woftnw.eof.io.parse.common.number.NonnegativeIntJsonParser;

public class NonnegativeToNonnegativeJsonParser extends RangeJsonParser {

	@Override
	protected NumberJsonParser<Integer> getMinParser() {
		return new NonnegativeIntJsonParser();
	}

	@Override
	protected NumberJsonParser<Integer> getMaxParser() {
		return new NonnegativeIntJsonParser();
	}
}
