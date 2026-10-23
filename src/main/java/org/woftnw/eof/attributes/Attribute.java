package org.woftnw.eof.attributes;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.jspecify.annotations.Nullable;

import java.util.function.Predicate;

/**
 * Represents a tribal attribute.
 */
public class Attribute {
	private static final int ATTRIBUTE_TICK_DURATION = 20;
	private static final int EFFECT_OVERSHOOT_DURATION = 40;

	protected PotionEffect effect;
	@Nullable
	protected Predicate<Player> predicate;

	/**
	 * Create a tribe attribute.
	 *
	 * @param effect The potion effect to apply. The duration will be ignored.
	 * @param predicate The predicate that must be true to apply the attribute or null if it should always be applied.
	 */
	public Attribute(PotionEffect effect, @Nullable Predicate<Player> predicate) {
		this.effect = effect;
		this.predicate = predicate;
	}

	/**
	 * Tick this attribute.
	 *
	 * @param player The player to apply to.
	 */
	public void tick(Player player) {
		if (predicate == null || predicate.test(player)) {
			player.addPotionEffect(effect.withDuration(ATTRIBUTE_TICK_DURATION + EFFECT_OVERSHOOT_DURATION));
		}
	}
}
