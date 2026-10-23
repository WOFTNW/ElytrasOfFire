package org.woftnw.eof.attributes;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.jspecify.annotations.Nullable;

import java.util.function.Predicate;

/**
 * Represents a tribal attribute.
 */
public class Attribute {
	private final PotionEffect EFFECT;
	@Nullable
	private final Predicate<Player> PREDICATE;

	/**
	 * Create a tribe attribute.
	 *
	 * @param effect The potion effect to apply.
	 * @param predicate The predicate that must be true to apply the attribute or null if it should always be applied.
	 */
	public Attribute(PotionEffect effect, @Nullable Predicate<Player> predicate) {
		this.EFFECT = effect;
		this.PREDICATE = predicate;
	}

	/**
	 * Tick this attribute.
	 *
	 * @param player The player to apply to.
	 */
	public void tick(Player player) {
		if (PREDICATE == null || PREDICATE.test(player)) {
			player.addPotionEffect(EFFECT);
		}
	}
}
