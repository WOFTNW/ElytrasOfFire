package org.woftnw.eof.ability.effect.target;

/**
 * Represents a target that is ranged.
 */
public abstract class RangedTarget implements Target {
	/**
	 * The maximum target range for the effect.
	 */
	protected final float RANGE;

	/**
	 * Create a RangedTarget.
	 * @param range The maximum range of the effect.
	 */
	protected RangedTarget(float range) {
		RANGE = range;
	}
}
