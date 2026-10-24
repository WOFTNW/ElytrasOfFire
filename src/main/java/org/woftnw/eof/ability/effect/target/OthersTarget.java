package org.woftnw.eof.ability.effect.target;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.Collection;

/**
 * Targets all instances of {@link LivingEntity} within range except for the user.
 */
public class OthersTarget extends RangedTarget {
	public OthersTarget(float range) {
		super(range);
	}

	@Override
	public Collection<LivingEntity> getTargets(@NonNull Player user) {
		Collection<LivingEntity> nearbyLivingEntities = user.getLocation().getNearbyLivingEntities(RANGE);
		nearbyLivingEntities.remove(user);
		return nearbyLivingEntities;
	}

	@Override
	public TextColor getTextColor() {
		return NamedTextColor.RED;
	}
}
