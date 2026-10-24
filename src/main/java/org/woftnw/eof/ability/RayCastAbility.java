package org.woftnw.eof.ability;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ability.effect.AbilityEffect;
import org.woftnw.eof.exception.AbilityActivationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an ability that is fired from the player's facing direction to a certain distance.
 */
public class RayCastAbility extends ActiveAbility {
	private static final float PARTICLE_RANGE = 0.2f;
	private static final float PARTICLE_SPEED = 0.05f;
	private static final int PARTICLE_COUNT = 1;
	private static final float RAY_CAST_HIT_RANGE = 0.5f;
	private static final Archetype ARCHETYPE = Archetype.PRIMARY;

	protected final Particle PARTICLE;
	protected float range;

	/**
	 * Create a new RayCastAbility
	 *
	 * @param name             The name of the ability.
	 * @param selfEffects      The effects applied to the user.
	 * @param activationItem   The item used for activation.
	 * @param activationMethod The activation method.
	 * @param particle         The particle to show in the activation.
	 */
	public RayCastAbility(TextComponent name, AbilityEffect[] selfEffects, ItemStack activationItem, Activation activationMethod, Particle particle) {
		super(name, selfEffects, activationItem, activationMethod, ARCHETYPE);
		PARTICLE = particle;
	}

	/**
	 * Get the particle for this ray cast ability.
	 *
	 * @return The particle this ability uses.
	 */
	public Particle getParticle() {
		return PARTICLE;
	}

	/**
	 * Get the vector that defines the direction and distance each increment of the ray cast should be.
	 *
	 * @param origin The origin location.
	 * @return The calculated vector.
	 */
	private @NonNull Vector getIncrementalRayCastVector(@NonNull Location origin) {
		Vector vector = origin.toVector().normalize();
		vector.multiply(PARTICLE_RANGE);
		return vector;
	}

	/**
	 * Get the number of points in the ray cast by dividing the range by the particle range.
	 *
	 * @return The calculated float.
	 */
	private float getRayCastPointCount() {
		return range / PARTICLE_RANGE;
	}

	/**
	 * Create a line of particles from a player.
	 *
	 * @param source The player to fire from.
	 */
	public void createParticles(@NonNull Player source) {
		final Location summonLocation = source.getLocation();
		final World WORLD = summonLocation.getWorld();
		Vector vector = getIncrementalRayCastVector(summonLocation);

		for (int i = 0; i < getRayCastPointCount(); i++) {
			summonLocation.add(vector);
			WORLD.spawnParticle(
					PARTICLE,
					summonLocation,
					PARTICLE_COUNT,
					PARTICLE_RANGE,
					PARTICLE_RANGE,
					PARTICLE_RANGE,
					PARTICLE_SPEED
			);
		}
	}

	/**
	 * Get players hit by the ray cast.
	 *
	 * @param source The player to fire from.
	 * @return A list of players hit by the ray cast.
	 */
	public List<Player> getHitPlayers(@NonNull Player source) {
		List<Player> players = new ArrayList<>();

		Location summonLocation = source.getLocation();
		Vector vector = getIncrementalRayCastVector(summonLocation);

		for (int i = 0; i < getRayCastPointCount(); i++) {
			summonLocation.add(vector);
			players.addAll(summonLocation.getNearbyPlayers(RAY_CAST_HIT_RANGE));
		}

		return players;
	}

	@Override
	public void activate(Player player) throws AbilityActivationException {
		super.activate(player);
		createParticles(player);
	}
}
