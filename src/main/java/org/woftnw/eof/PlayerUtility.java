package org.woftnw.eof;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

/**
 * Contains useful player utilities. This should only be created once on enable by this plugin.
 */
public class PlayerUtility {

	// Player data is stored in the persistent data container (PDC).
	// See https://docs.papermc.io/paper/dev/pdc/

	private final NamespacedKey TRIBE_KEY;

	/**
	 * Create a PlayerUtility.
	 *
	 * @param plugin The plugin instance.
	 */
	public PlayerUtility(JavaPlugin plugin) {
		this.TRIBE_KEY = new NamespacedKey(plugin, "tribe");
	}

	/**
	 * Set a player's tribe.
	 *
	 * @param player The player whose tribe to change.
	 * @param tribe The tribe to change to.
	 */
	public void setPlayerTribe(@NonNull Player player, @NonNull Tribe tribe) {
		PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
		persistentDataContainer.set(TRIBE_KEY, PersistentDataType.STRING, tribe.getPDCValue());
	}

	/**
	 * Get a player's tribe.
	 *
	 * @param player The player whose tribe to get.
	 * @return The tribe of the player, or null if nonexistent or unrecognized.
	 */
	@Nullable
	public Tribe getPlayerTribe(@NonNull Player player) {
		PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
		String tribeString = persistentDataContainer.get(TRIBE_KEY, PersistentDataType.STRING);
		return TribeUtility.getTribeFromPDCValue(tribeString);
	}
}
