package org.woftnw.eof.ability.effect.target;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.Collection;

/**
 * Applies to both the user of the effect and others.
 */
public class AllTarget extends RangedTarget {
	public AllTarget(float range) {
		super(range);
	}

	@Override
	public Collection<LivingEntity> getTargets(@NonNull Player user) {
		return user.getLocation().getNearbyLivingEntities(RANGE);
	}

	@Override
	public TextColor getTextColor() {
		return NamedTextColor.YELLOW;
	}
}
