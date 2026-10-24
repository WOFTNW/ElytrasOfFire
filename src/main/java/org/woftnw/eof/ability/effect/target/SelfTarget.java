package org.woftnw.eof.ability.effect.target;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

/**
 * Targets the user.
 */
public class SelfTarget implements Target {
	@Override
	public Collection<LivingEntity> getTargets(Player user) {
		return List.of(user);
	}

	@Override
	public TextColor getTextColor() {
		return NamedTextColor.GREEN;
	}
}
