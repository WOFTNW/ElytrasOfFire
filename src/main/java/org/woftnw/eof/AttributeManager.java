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

	private final ElytrasOfFire PLUGIN;

	private final Map<Tribe, List<Attribute>> attributes = new HashMap<>();
	private final BukkitTask task;

	/**
	 * Create an attribute scheduler.
	 *
	 * @param plugin The plugin instance.
	 */
	public AttributeManager(@NonNull ElytrasOfFire plugin) {
		PLUGIN = plugin;

		task = plugin.getServer().getScheduler().runTaskTimer(plugin, this::tick, TICK_DELAY, TICK_PERIOD);
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
		PlayerUtility playerUtility = PLUGIN.getPlayerUtility();

		for (Tribe tribe : attributes.keySet()) {
			ArrayList<Player> players = new ArrayList<>(List.copyOf(PLUGIN.getServer().getOnlinePlayers()));
			players.removeIf(player -> playerUtility.getPlayerTribe(player) != tribe);

			for (Attribute attribute : attributes.getOrDefault(tribe, new ArrayList<>())) {
				for (Player player : players) {
					if (playerUtility.arePlayerPlayerAttributesEnabled(player)) attribute.tick(player);
				}
			}
		}
	}

	public void apply(@NonNull Player player) throws NoTribeException {
		PlayerUtility playerUtility = PLUGIN.getPlayerUtility();

		final Tribe TRIBE = playerUtility.getPlayerTribe(player);
		if (TRIBE == null) {
			throw new NoTribeException();
		}
		final List<Attribute> TRIBE_ATTRIBUTES = attributes.get(TRIBE);
		for (int i = 0; i < TRIBE_ATTRIBUTES.size(); i++) {
			Attribute ATTRIBUTE = TRIBE_ATTRIBUTES.get(i);
			try {
				ATTRIBUTE.apply(player);
			} catch (IllegalArgumentException e) {
				PLUGIN.getLogger().warning(String.format("Tried to apply attribute %s of tribe %s, but the target already has it.", i, TRIBE.getPDCValue()));
			}
		}
	}

	public void unapply(@NonNull Player player) {
		PlayerUtility playerUtility = PLUGIN.getPlayerUtility();

		final List<Attribute> TRIBE_ATTRIBUTES = attributes.get(playerUtility.getPlayerTribe(player));
		for (final Attribute ATTRIBUTE : TRIBE_ATTRIBUTES) {
			ATTRIBUTE.unapply(player);
		}
	}
}
