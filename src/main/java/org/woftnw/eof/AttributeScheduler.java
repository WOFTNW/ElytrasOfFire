package org.woftnw.eof;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.attributes.Attribute;

import java.util.*;

public class AttributeScheduler {
	private final ElytrasOfFire PLUGIN;

	private final Map<Tribe, List<Attribute>> attributes = new HashMap<>();
	private final BukkitTask task;

	public AttributeScheduler(@NonNull ElytrasOfFire plugin) {
		PLUGIN = plugin;

		task = plugin.getServer().getScheduler().runTaskTimer(plugin, this::tick, 20, 20);
	}

	public void stop() {
		task.cancel();
	}

	public void registerAttribute(Tribe tribe, Attribute attribute) {
		List<Attribute> attributesOfTribe = attributes.getOrDefault(tribe, new ArrayList<>());
		attributesOfTribe.add(attribute);
		attributes.put(tribe, attributesOfTribe);
	}

	public void tick() {
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
}
