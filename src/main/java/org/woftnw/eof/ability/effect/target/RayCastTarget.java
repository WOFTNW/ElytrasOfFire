package org.woftnw.eof.ability.effect.target;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Targets users hit by a ray cast.
 */
public class RayCastTarget extends RangedTarget {
	private static final float RAY_CAST_POINT_DISTANCE = 0.25f;
	private static final float RAY_CAST_POINT_DETECTION_RADIUS = 0.2f;

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
	public Collection<LivingEntity> getTargets(@NonNull Player user) {
		final Location ORIGIN = user.getEyeLocation();
		final Vector RAY_CAST_POINT_DELTA = ORIGIN.getDirection().multiply(RAY_CAST_POINT_DISTANCE);

		final ArrayList<LivingEntity> TARGETS = new ArrayList<>();

		for (float i = 0; i < RANGE; i += RAY_CAST_POINT_DISTANCE) {
			ORIGIN.add(RAY_CAST_POINT_DELTA);
			final Collection<LivingEntity> FOUND_ENTITIES = ORIGIN.getNearbyLivingEntities(RAY_CAST_POINT_DETECTION_RADIUS);
			TARGETS.addAll(FOUND_ENTITIES);
		}

		TARGETS.removeIf(o -> o == user);

		return Set.copyOf(TARGETS);
	}

	@Override
	public TextColor getTextColor() {
		return NamedTextColor.RED;
	}
}
