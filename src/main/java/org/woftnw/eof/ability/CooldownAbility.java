package org.woftnw.eof.ability;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class CooldownAbility extends Ability {
	private static final int DEFAULT_COOLDOWN = 20 * 10;

	protected final int COOLDOWN;
	protected int lastUsed;

	public CooldownAbility(TextComponent name, List<PotionEffect> selfEffects, int cooldown) {
		super(name, selfEffects);
		this.COOLDOWN = cooldown;
		this.lastUsed = 0;
	}

	public CooldownAbility(TextComponent name, List<PotionEffect> selfEffects) {
		this(name, selfEffects, DEFAULT_COOLDOWN);
	}
}
