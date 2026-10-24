package org.woftnw.eof.ability.effect;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.ability.effect.target.Target;

import java.util.Collection;

public class IgniteAbilityEffect extends AbilityEffect {
	private final int FIRE_TICKS;

	/**
	 * Create an IgniteAbilityEffect.
	 *
	 * @param target    The target of the effect.
	 * @param fireTicks The number of ticks to set the target on fire for.
	 */
	public IgniteAbilityEffect(ElytrasOfFire plugin, Target target, int fireTicks) {
		super(plugin, target);
		FIRE_TICKS = fireTicks;
	}

	/**
	 * Get the number of ticks to set the target on fire for.
	 *
	 * @return The number of fire ticks.
	 */
	public int getFireTicks() {
		return FIRE_TICKS;
	}

	@Override
	public void apply(Player user) {
		final Collection<LivingEntity> TARGETS = TARGET.getTargets(user);
		for (final LivingEntity TARGET : TARGETS) {
			TARGET.setFireTicks(FIRE_TICKS);
		}
	}
}
