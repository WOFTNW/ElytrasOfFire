package org.woftnw.eof.ability;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.exception.AbilityActivationException;
import org.woftnw.eof.exception.AbilityCooldownException;

import java.util.List;

public class CooldownAbility extends Ability {
	private static final int DEFAULT_COOLDOWN = 20 * 10;

	protected final int COOLDOWN;
	protected long lastUsed;

	public CooldownAbility(TextComponent name, List<PotionEffect> selfEffects, int cooldown) {
		super(name, selfEffects);
		this.COOLDOWN = cooldown;
		this.lastUsed = 0;
	}

	public CooldownAbility(TextComponent name, List<PotionEffect> selfEffects) {
		this(name, selfEffects, DEFAULT_COOLDOWN);
	}

	public boolean isOnCooldown(@NonNull Player player) {
		final long GAME_TIME = player.getWorld().getGameTime();
		return (getCoolDownEndTime(player) < GAME_TIME);
	}

	public long getCoolDownEndTime(@NonNull Player player) {
		return (lastUsed + COOLDOWN);
	}

	@Override
	public void activate(Player player) throws AbilityActivationException {
		if (isOnCooldown(player)) {
			throw new AbilityCooldownException(getCoolDownEndTime(player));
		}
		super.activate(player);
	}
}
