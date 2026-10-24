package org.woftnw.eof.ability.effect;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.ability.effect.target.Target;

import java.util.Collection;

public class PotionEffectAbilityEffect extends AbilityEffect {
	protected final PotionEffect POTION_EFFECT;

	/**
	 * Create an PotionEffectAbilityEffect.
	 *
	 * @param target       The target of the effect.
	 * @param potionEffect The potion effect.
	 */
	public PotionEffectAbilityEffect(ElytrasOfFire plugin, Target target, PotionEffect potionEffect) {
		super(plugin, target);
		POTION_EFFECT = potionEffect;
	}

	/**
	 * Get the potion effect.
	 *
	 * @return The potion effect.
	 */
	public PotionEffect getPotionEffect() {
		return POTION_EFFECT;
	}

	@Override
	public void apply(Player user) {
		final Collection<LivingEntity> TARGETS = TARGET.getTargets(user);
		for (final LivingEntity TARGET : TARGETS) {
			TARGET.addPotionEffect(POTION_EFFECT);
		}
	}
}
