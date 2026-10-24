package org.woftnw.eof.ability;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.woftnw.eof.exception.AbilityActivationException;

/**
 * Represents the simplest most generic form of an ability.
 * <p>
 * This class only provides the most basic attributes and behaviors of a tribal ability. If you're creating an ability,
 * you should extend a subclass of Ability like {@link ActiveAbility} or {@link RayCastAbility}.
 * </p>
 *
 * @see CooldownAbility
 * @see ActiveAbility
 * @see RayCastAbility
 */
public class Ability {
	protected static PotionEffect[] DEFAULT_SELF_EFFECTS;

	/**
	 * The display name of this ability.
	 */
	protected TextComponent name;
	/**
	 * The effects to apply to the user on activation.
	 */
	protected PotionEffect[] selfEffects;

	/**
	 * Create an ability with some self effects.
	 * <p>
	 * You probably want to use a subclass like {@link ActiveAbility}. A generic Ability will not be of much use.
	 * </p>
	 *
	 * @param name        The display name of this ability.
	 * @param selfEffects The effects to apply to the user on activation. May be empty.
	 */
	public Ability(TextComponent name, PotionEffect[] selfEffects) {
		this.name = name;
		this.selfEffects = selfEffects;
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
		this.selfEffects = DEFAULT_SELF_EFFECTS;
	}

	/**
	 * Activate the ability.
	 *
	 * @param player The player to activate this ability upon.
	 * @throws AbilityActivationException When an exception occurs that prevents the ability from being fully activated.
	 */
	public void activate(Player player) throws AbilityActivationException {
		for (PotionEffect effect : selfEffects) {
			player.addPotionEffect(effect);
		}
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
