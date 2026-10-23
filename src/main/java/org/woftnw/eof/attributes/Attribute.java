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
	private final boolean REFRESH_ON_PREDICATE;

	private boolean lastTest = false;

	/**
	 * Create a tribe attribute.
	 *
	 * @param effect The potion effect to apply.
	 * @param predicate The predicate that must be true to apply the attribute or null if it should always be applied.
	 * @param refreshOnPredicate Whether this attribute should only refresh when the predicate switches from false to true.
	 */
	public Attribute(PotionEffect effect, @Nullable Predicate<Player> predicate, boolean refreshOnPredicate) {
		this.EFFECT = effect;
		this.PREDICATE = predicate;
		this.REFRESH_ON_PREDICATE = refreshOnPredicate;
	}

	/**
	 * Create a tribe attribute.
	 *
	 * @param effect The potion effect to apply.
	 * @param predicate The predicate that must be true to apply the attribute or null if it should always be applied.
	 */
	public Attribute(PotionEffect effect, @Nullable Predicate<Player> predicate) {
		this(effect, predicate, false);
	}

	/**
	 * Create a tribe attribute.
	 *
	 * @param effect The potion effect to apply.
	 */
	public Attribute(PotionEffect effect) {
		this(effect, null);
	}

	/**
	 * Tick this attribute.
	 *
	 * @param player The player to apply to.
	 */
	public void tick(Player player) {
		boolean test = PREDICATE == null || PREDICATE.test(player);
		if ((!REFRESH_ON_PREDICATE || !lastTest) && test) {
			player.addPotionEffect(EFFECT);
		}
		lastTest = test;
	}
}
