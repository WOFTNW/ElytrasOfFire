package org.woftnw.eof.ability;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class Ability {
	private TextComponent name;
	private List<PotionEffect> selfEffects;

	public Ability(TextComponent name, List<PotionEffect> selfEffects) {
		this.name = name;
		this.selfEffects = selfEffects;
	}

	public void activate(Player player) {
		for (PotionEffect effect : selfEffects) {
			player.addPotionEffect(effect);
		}
	}
}
