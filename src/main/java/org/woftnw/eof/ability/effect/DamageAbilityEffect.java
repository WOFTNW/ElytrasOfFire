package org.woftnw.eof.ability.effect;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.ability.effect.target.Target;

import java.util.Collection;

public class DamageAbilityEffect extends AbilityEffect {
	/**
	 * The damage of the effect.
	 */
	protected final float DAMAGE;

	/**
	 * Create an AbilityEffect.
	 *
	 * @param target The target of the effect.
	 * @param damage The damage of the effect.
	 */
	public DamageAbilityEffect(ElytrasOfFire plugin, Target target, float damage) {
		super(plugin, target);
		DAMAGE = damage;
	}

	/**
	 * Get the damage of this effect.
	 *
	 * @return The damage of this effect.
	 */
	public float getDamage() {
		return DAMAGE;
	}

	@Override
	public Component getDescription() {
		return Component.text(String.format("%s ❤ damage", DAMAGE)).color(TARGET.getTextColor());
	}

	@Override
	public void apply(Player user) {
		final Collection<LivingEntity> TARGETS = TARGET.getTargets(user);
		for (final LivingEntity TARGET : TARGETS) {
			TARGET.damage(DAMAGE);
		}
	}
}
