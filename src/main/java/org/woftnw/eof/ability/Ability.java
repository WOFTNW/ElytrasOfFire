package org.woftnw.eof.ability;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.woftnw.eof.exception.AbilityActivationException;

/**
 * Represents the simplest most generic form of an ability.
 */
public class Ability {
	/**
	 * The display name of this ability.
	 */
	protected TextComponent name;
	/**
	 * The effects to apply to the user on activation.
	 */
	protected PotionEffect[] selfEffects;

	/**
	 * Create an ability.
	 *
	 * @param name The display name of this ability.
	 * @param selfEffects The effects to apply to the user on activation. May be empty.
	 */
	public Ability(TextComponent name, PotionEffect[] selfEffects) {
		this.name = name;
		this.selfEffects = selfEffects;
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
