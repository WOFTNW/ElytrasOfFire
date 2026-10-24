package org.woftnw.eof.ability.effect;

import org.bukkit.entity.Player;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.ability.effect.target.Target;

/**
 * Represents an effect that an ability has.
 */
public abstract class AbilityEffect {
	protected final ElytrasOfFire PLUGIN;

	/**
	 * The target of the effect.
	 */
	protected final Target TARGET;

	/**
	 * Create an AbilityEffect.
	 *
	 * @param target The target of the effect.
	 * @param plugin The plugin instance.
	 */
	public AbilityEffect(ElytrasOfFire plugin, Target target) {
		TARGET = target;
		PLUGIN = plugin;
	}

	/**
	 * Get the target of this effect.
	 *
	 * @return The {@link Target} of this effect.
	 */
	public Target getTarget() {
		return TARGET;
	}

	abstract public void apply(Player user);
}
