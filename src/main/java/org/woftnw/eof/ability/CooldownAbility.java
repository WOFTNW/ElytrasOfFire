package org.woftnw.eof.ability;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.woftnw.eof.exception.AbilityActivationException;
import org.woftnw.eof.exception.AbilityCooldownException;

public class CooldownAbility extends Ability {
	private static final int DEFAULT_COOLDOWN = 20 * 10;

	protected final int COOLDOWN;
	protected long lastUsed;

	public CooldownAbility(TextComponent name, PotionEffect[] selfEffects, int cooldown) {
		super(name, selfEffects);
		this.COOLDOWN = cooldown;
		this.lastUsed = 0;
	}

	public CooldownAbility(TextComponent name, PotionEffect[] selfEffects) {
		this(name, selfEffects, DEFAULT_COOLDOWN);
	}

	public boolean isOnCooldown() {
		final long GAME_TIME = Bukkit.getWorlds().getFirst().getGameTime();
		return (getCoolDownEndTime() > GAME_TIME);
	}

	public long getCoolDownEndTime() {
		return (lastUsed + COOLDOWN);
	}

	@Override
	public void activate(Player player) throws AbilityActivationException {
		if (isOnCooldown()) {
			throw new AbilityCooldownException(getCoolDownEndTime());
		}
		lastUsed = Bukkit.getWorlds().getFirst().getGameTime();
		super.activate(player);
	}
}
