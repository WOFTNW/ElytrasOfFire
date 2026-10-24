package org.woftnw.eof.ability.effect;

import org.jspecify.annotations.Nullable;
import org.woftnw.eof.ability.effect.target.Target;

/**
 * Represents an effect that an ability has.
 */
public class AbilityEffect {
	/**
	 * The target of the effect.
	 */
	protected final Target TARGET;
	/**
	 * The damage of the effect, if applicable.
	 */
	protected final float DAMAGE;
	/**
	 * The duration of the effect or <code>null</code> if infinite.
	 */
	@Nullable
	protected final Integer DURATION;
	// TODO: Ray cast count, spread, arc.

	/**
	 * Create an AbilityEffect.
	 *
	 * @param target   The target of the effect.
	 * @param damage   The damage of the effect.
	 * @param duration The duration of the effect, or <code>null</code> if infinite.
	 */
	public AbilityEffect(Target target, float damage, @Nullable Integer duration) {
		TARGET = target;
		DAMAGE = damage;
		DURATION = duration;
	}

	/**
	 * Get the target of this effect.
	 *
	 * @return The {@link Target} of this effect.
	 */
	public Target getTarget() {
		return TARGET;
	}

	/**
	 * Get the damage of this effect.
	 *
	 * @return The damage of this effect.
	 */
	public float getDamage() {
		return DAMAGE;
	}

	/**
	 * Get the duration of this effect, or <code>null</code> if infinite.
	 *
	 * @return The duration of this effect as an {@link Integer} or <code>null</code> if infinite.
	 */
	@Nullable
	public Integer getDuration() {
		return DURATION;
	}

	/**
	 * Check whether this effect is infinite.
	 *
	 * @return <code>true</code> if infinite, <code>false</code> otherwise.
	 */
	public boolean isInfinite() {
		return DURATION == null;
	}
}
