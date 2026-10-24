package org.woftnw.eof.ability;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.woftnw.eof.exception.AbilityActivationException;

import java.util.List;

public class Ability {
	protected TextComponent name;
	protected List<PotionEffect> selfEffects;

	public Ability(TextComponent name, List<PotionEffect> selfEffects) {
		this.name = name;
		this.selfEffects = selfEffects;
	}

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
