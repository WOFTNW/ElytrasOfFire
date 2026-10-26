package org.woftnw.eof.ability;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.woftnw.eof.MessageUtility;
import org.woftnw.eof.ability.effect.AbilityEffect;
import org.woftnw.eof.exception.AbilityActivationException;

/**
 * Represents the simplest most generic form of an ability.
 * <p>
 * This class only provides the most basic attributes and behaviors of a tribal ability. If you're creating an ability,
 * you should extend a subclass of Ability like {@link ActiveAbility}.
 * </p>
 *
 * @see ActiveAbility
 */
public class Ability {
	protected final static AbilityEffect[] DEFAULT_EFFECTS = new AbilityEffect[0];

	/**
	 * The display name of this ability.
	 */
	protected TextComponent name;
	/**
	 * The effects to apply to the user on activation.
	 */
	protected AbilityEffect[] effects;

	/**
	 * Create an ability with some self effects.
	 * <p>
	 * You probably want to use a subclass like {@link ActiveAbility}. A generic Ability will not be of much use.
	 * </p>
	 *
	 * @param name    The display name of this ability.
	 * @param effects The effects to apply on activation. May be empty.
	 */
	public Ability(TextComponent name, AbilityEffect[] effects) {
		this.name = name;
		this.effects = effects;
	}

	/**
	 * Create an empty ability.
	 * <p>
	 * You probably want to use a subclass like {@link ActiveAbility}. A generic Ability will not be of much use.
	 * </p>
	 *
	 * @param name The display name of this ability.
	 */
	public Ability(TextComponent name) {
		this.name = name;
		this.effects = DEFAULT_EFFECTS;
	}

	/**
	 * Activate the ability.
	 *
	 * @param player The player to activate this ability upon.
	 * @throws AbilityActivationException When an exception occurs that prevents the ability from being fully activated.
	 */
	public void activate(Player player) throws AbilityActivationException {
		for (AbilityEffect effect : effects) {
			effect.apply(player);
		}
		notifyActivation(player);
	}

	/**
	 * Notify the player that the ability has been activated.
	 *
	 * @param player The player to notify.
	 */
	protected void notifyActivation(@NotNull Player player) {
		player.sendActionBar(Component.text("Activated ").color(MessageUtility.INFO_TEXT_COLOR).append(this.name));
	}

	/**
	 * Get the name of this ability.
	 *
	 * @return The ability name as a TextComponent.
	 */
	public TextComponent getName() {
		return name;
	}

	/**
	 * Get the name of this ability as a String.
	 *
	 * @return The ability name as a String.
	 */
	public String getNameAsString() {
		return name.content();
	}
}
