package org.woftnw.eof.ability.effect;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.ability.effect.target.Target;

/**
 * Represents an effect that an ability has.
 */
public abstract class AbilityEffect {
	protected final static TextColor SELF_EFFECT_COLOR = NamedTextColor.GREEN;
	protected final static TextColor ALL_EFFECT_COLOR = NamedTextColor.YELLOW;
	protected final static TextColor OTHERS_EFFECT_COLOR = NamedTextColor.RED;

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
	protected AbilityEffect(ElytrasOfFire plugin, Target target) {
		TARGET = target;
		PLUGIN = plugin;
	}

	/**
	 * Get the description of this ability.
	 *
	 * @return The description as a {@link TextComponent}.
	 */
	abstract public Component getDescription();

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
