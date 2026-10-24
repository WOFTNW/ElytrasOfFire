package org.woftnw.eof.ability.effect.target;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

/**
 * Targets users hit by a ray cast.
 */
public class RayCastTarget extends RangedTarget {
	/**
	 * The number of rays.
	 */
	private final int RAY_COUNT;
	/**
	 * The spread between each ray.
	 */
	private final float RAY_SPREAD;
	/**
	 * The arc of each ray.
	 */
	private final float RAY_ARC;

	/**
	 * Create a RayCastTarget.
	 * @param range The maximum range of the effect.
	 */
	public RayCastTarget(float range) {
		super(range);
		// TODO: Implement these
		RAY_COUNT = 1;
		RAY_SPREAD = 0;
		RAY_ARC = 0;
	}

	@Override
	public Collection<LivingEntity> getTargets(Player user) {
		// TODO: Implement.
		return List.of();
	}
}
