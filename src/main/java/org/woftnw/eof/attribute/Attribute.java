package org.woftnw.eof.attribute;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Predicate;

/**
 * Represents a tribal attribute.
 */
public abstract class Attribute {
	@Nullable
	protected final Predicate<Player> PREDICATE;
	protected final boolean REFRESH_ON_PREDICATE;

	protected boolean lastTest = false;

	/**
	 * Create a tribe attribute.
	 *
	 * @param predicate The predicate that must be true to apply the attribute or null if it should always be applied.
	 * @param refreshOnPredicate Whether this attribute should only refresh when the predicate switches from false to true.
	 */
	public Attribute(@Nullable Predicate<Player> predicate, boolean refreshOnPredicate) {
		this.PREDICATE = predicate;
		this.REFRESH_ON_PREDICATE = refreshOnPredicate;
	}

	/**
	 * Create a tribe attribute.
	 *
	 * @param predicate The predicate that must be true to apply the attribute or null if it should always be applied.
	 */
	public Attribute(@Nullable Predicate<Player> predicate) {
		this(predicate, false);
	}

	/**
	 * Check if the player meets the predicate, if there is one. Otherwise, this will return true.
	 *
	 * @param player The player to check.
	 */
	public boolean meetsPredicate(@NonNull Player player) {
		final boolean TEST = PREDICATE == null || PREDICATE.test(player);
		final boolean RESULT = ((!REFRESH_ON_PREDICATE || !lastTest) && TEST);
		lastTest = TEST;
		return RESULT;
	}

	/**
	 * Tick this attribute. Subclasses should use <code>meetsPredicate(player)</code> in this method.
	 *
	 * @param player The player to apply to.
	 */
	public void tick(Player player) {
		if (meetsPredicate(player)) {
			apply(player);
		} else {
			unapply(player);
		}
	};

	/**
	 * Apply this attribute. This is only used when the player changes tribes or activates attributes.
	 * Subclasses should use <code>meetsPredicate(player)</code> in this method.
	 *
	 * @param player The player to apply to.
	 */
	public abstract void apply(Player player);

	/**
	 * Unapply this attribute. This is only used when the player changes tribes or deactivates attributes.
	 * Subclasses should use <code>meetsPredicate(player)</code> in this method.
	 *
	 * @param player The player to unapply to.
	 */
	public abstract void unapply(Player player);
}
