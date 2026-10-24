package org.woftnw.eof.ability.effect.target;

import org.bukkit.entity.*;

import java.util.Collection;
import java.util.List;

/**
 * Represents the target of an <code>AbilityEffect</code>.
 */
public interface Target {
	List<LivingEntity> NONE = List.of();

	/**
	 * Get the targets of this effect.
	 *
	 * @param user The effect user.
	 * @return The Players affected.
	 */
	Collection<LivingEntity> getTargets(Player user);
}
