package org.woftnw.eof.attribute;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * Represents a tribal attribute that uses a potion effect.
 */
public class PotionEffectAttribute extends Attribute {
	private final PotionEffect EFFECT;

	/**
	 * Create a tribe attribute.
	 *
	 * @param effect The potion effect to apply.
	 * @param predicate The predicate that must be true to apply the attribute or null if it should always be applied.
	 * @param refreshOnPredicate Whether this attribute should only refresh when the predicate switches from false to true.
	 */
	public PotionEffectAttribute(PotionEffect effect, @Nullable Predicate<Player> predicate, boolean refreshOnPredicate) {
		super(predicate, refreshOnPredicate);
		this.EFFECT = effect;
	}

	/**
	 * Create a tribe attribute.
	 *
	 * @param effect The potion effect to apply.
	 * @param predicate The predicate that must be true to apply the attribute or null if it should always be applied.
	 */
	public PotionEffectAttribute(PotionEffect effect, @Nullable Predicate<Player> predicate) {
		this(effect, predicate, false);
	}

	/**
	 * Create a tribe attribute.
	 *
	 * @param effect The potion effect to apply.
	 */
	public PotionEffectAttribute(PotionEffect effect) {
		this(effect, null);
	}

	@Override
	public void apply(Player player) {
		// nothing to do
	}

	@Override
	public void unapply(@NonNull Player player) {
		Collection<PotionEffect> effects = player.getActivePotionEffects();
		List<PotionEffect> effectsToRemove = new ArrayList<>();
		for (PotionEffect playerEffect : effects) {
			if (!EFFECT.getType().equals(playerEffect.getType())) continue;
			if (EFFECT.getDuration() < playerEffect.getDuration()) continue;
			effectsToRemove.add(playerEffect);
		}
		effectsToRemove.forEach(e -> player.removePotionEffect(e.getType()));
	}
}
