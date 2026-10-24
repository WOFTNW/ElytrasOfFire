package org.woftnw.eof.ability;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.woftnw.eof.exception.AbilityActivationException;
import org.woftnw.eof.exception.AbilityCooldownException;

/**
 * Represents an ability that has an activation cooldown.
 *
 * @see ActiveAbility
 * @see RayCastAbility
 */
public class CooldownAbility extends Ability {
	private static final int DEFAULT_COOLDOWN = 20 * 10;

	protected final int COOLDOWN;
	protected long lastUsed;

	/**
	 * Create an ability with some self effects and a cooldown.
	 * <p>
	 * You probably want to use a subclass like {@link ActiveAbility}. A generic CooldownAbility will not be of much use.
	 * </p>
	 *
	 * @param name        The display name of this ability.
	 * @param selfEffects The effects to apply to the user on activation. May be empty.
	 * @param cooldown    The number of ticks after activation until the ability can be activated again.
	 * @see ActiveAbility
	 * @see RayCastAbility
	 */
	public CooldownAbility(TextComponent name, PotionEffect[] selfEffects, int cooldown) {
		super(name, selfEffects);
		this.COOLDOWN = cooldown;
		this.lastUsed = 0;
	}

	/**
	 * Create an ability with some self effects and a cooldown of 10 seconds.
	 * <p>
	 * You probably want to use a subclass like {@link ActiveAbility}. A generic CooldownAbility will not be of much use.
	 * </p>
	 *
	 * @param name        The display name of this ability.
	 * @param selfEffects The effects to apply to the user on activation. May be empty.
	 * @see ActiveAbility
	 * @see RayCastAbility
	 */
	public CooldownAbility(TextComponent name, PotionEffect[] selfEffects) {
		this(name, selfEffects, DEFAULT_COOLDOWN);
	}

	/**
	 * Create an ability with a cooldown of 10 seconds.
	 * <p>
	 * You probably want to use a subclass like {@link ActiveAbility}. A generic CooldownAbility will not be of much use.
	 * </p>
	 *
	 * @param name The display name of this ability.
	 * @see ActiveAbility
	 * @see RayCastAbility
	 */
	public CooldownAbility(TextComponent name) {
		this(name, DEFAULT_SELF_EFFECTS, DEFAULT_COOLDOWN);
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
