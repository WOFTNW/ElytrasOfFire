package org.woftnw.eof;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.attribute.Attribute;
import org.woftnw.eof.exception.NoTribeException;

import java.util.*;

/**
 * Manages when attributes should tick.
 */
public class AttributeManager {
	private final static long TICK_DELAY = 20;
	private final static long TICK_PERIOD = 20;

	private final Map<Tribe, List<Attribute>> attributes = new HashMap<>();
	private final BukkitTask task;

	/**
	 * Create an attribute scheduler.
	 */
	public AttributeManager() {
		final ElytrasOfFire PLUGIN = ElytrasOfFire.getPlugin();
		task = PLUGIN.getServer().getScheduler().runTaskTimer(PLUGIN, this::tick, TICK_DELAY, TICK_PERIOD);
	}

	/**
	 * Stop ticking all attributes.
	 */
	public void disable() {
		task.cancel();
	}

	/**
	 * Register an attribute. It will begin ticking automatically.
	 *
	 * @param tribe The tribe the attribute belongs to.
	 * @param attribute The attribute.
	 */
	public void registerAttribute(Tribe tribe, Attribute attribute) {
		List<Attribute> attributesOfTribe = attributes.getOrDefault(tribe, new ArrayList<>());
		attributesOfTribe.add(attribute);
		attributes.put(tribe, attributesOfTribe);
	}

	private void tick() {
		for (Tribe tribe : attributes.keySet()) {
			ArrayList<Player> players = new ArrayList<>(List.copyOf(ElytrasOfFire.getPlugin().getServer().getOnlinePlayers()));
			players.removeIf(player -> PlayerUtility.getPlayerTribe(player) != tribe);

			for (Attribute attribute : attributes.getOrDefault(tribe, new ArrayList<>())) {
				for (Player player : players) {
					if (PlayerUtility.arePlayerPlayerAttributesEnabled(player)) attribute.tick(player);
				}
			}
		}
	}

	public void apply(@NonNull Player player) throws NoTribeException {
		final Tribe TRIBE = PlayerUtility.getPlayerTribe(player);
		if (TRIBE == null) {
			throw new NoTribeException();
		}
		final List<Attribute> TRIBE_ATTRIBUTES = attributes.get(TRIBE);
		for (int i = 0; i < TRIBE_ATTRIBUTES.size(); i++) {
			Attribute ATTRIBUTE = TRIBE_ATTRIBUTES.get(i);
			try {
				ATTRIBUTE.apply(player);
			} catch (IllegalArgumentException e) {
				ElytrasOfFire.getPlugin().getLogger().warning(String.format("Tried to apply attribute %s of tribe %s, but the target already has it.", i, TRIBE.getPDCValue()));
			}
		}
	}

	public void unapply(@NonNull Player player) {
		final List<Attribute> TRIBE_ATTRIBUTES = attributes.get(PlayerUtility.getPlayerTribe(player));
		for (final Attribute ATTRIBUTE : TRIBE_ATTRIBUTES) {
			ATTRIBUTE.unapply(player);
		}
	}
}
