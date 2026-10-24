package org.woftnw.eof.ability.effect.target;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Targets the LivingEntity attacked by the user.
 */
public class MeleeTarget implements Target {
	protected final ElytrasOfFire PLUGIN;

	public MeleeTarget(ElytrasOfFire plugin) {
		PLUGIN = plugin;
	}

	@Override
	public Collection<LivingEntity> getTargets(@NonNull Player user) {
		final UUID ATTACK_TARGET_UUID = PLUGIN.getPlayerUtility().getPlayerAttackTarget(user);
		if (ATTACK_TARGET_UUID == null) return NONE;

		final Entity ENTITY = Bukkit.getEntity(ATTACK_TARGET_UUID);
		if (!(ENTITY instanceof final LivingEntity LIVING_ENTITY)) return NONE;

		return List.of(LIVING_ENTITY);
	}
}
