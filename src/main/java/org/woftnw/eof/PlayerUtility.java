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

	private static final boolean ATTRIBUTES_ENABLED_DEFAULT = false;
	private static final boolean ABILITIES_ENABLED_DEFAULT = false;

	private final NamespacedKey TRIBE_KEY;
	private final NamespacedKey ATTRIBUTES_ENABLED_KEY;
	private final NamespacedKey ABILITIES_ENABLED_KEY;

	/**
	 * Create a PlayerUtility.
	 *
	 * @param plugin The plugin instance.
	 */
	public PlayerUtility(JavaPlugin plugin) {
		this.TRIBE_KEY = new NamespacedKey(plugin, "tribe");
		this.ATTRIBUTES_ENABLED_KEY = new NamespacedKey(plugin, "attributes_enabled");
		this.ABILITIES_ENABLED_KEY = new NamespacedKey(plugin, "abilities_enabled");
	}

	/**
	 * Enable or disable a player's tribal attributes.
	 *
	 * @param player The player whose state to change.
	 * @param enabled Whether to enable attributes.
	 */
	public void setPlayerAttributesEnabled(@NonNull Player player, boolean enabled) {
		PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
		persistentDataContainer.set(ATTRIBUTES_ENABLED_KEY, PersistentDataType.BOOLEAN, enabled);
	}

	/**
	 * Check whether a player's tribal attributes are enabled.
	 *
	 * @param player The player whose state to get.
	 * @return Whether attributes are enabled or disabled.
	 */
	public boolean arePlayerPlayerAttributesEnabled(@NonNull Player player) {
		PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
		return persistentDataContainer.getOrDefault(ATTRIBUTES_ENABLED_KEY, PersistentDataType.BOOLEAN, ATTRIBUTES_ENABLED_DEFAULT);
	}

	/**
	 * Enable or disable a player's tribal abilities.
	 *
	 * @param player The player whose state to change.
	 * @param enabled Whether to enable abilities.
	 */
	public void setPlayerAbilitiesEnabled(@NonNull Player player, boolean enabled) {
		PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
		persistentDataContainer.set(ABILITIES_ENABLED_KEY, PersistentDataType.BOOLEAN, enabled);
	}

	/**
	 * Check whether a player's tribal abilities are enabled.
	 *
	 * @param player The player whose state to get.
	 * @return Whether abilities are enabled or disabled.
	 */
	public boolean arePlayerPlayerAbilitiesEnabled(@NonNull Player player) {
		PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
		return persistentDataContainer.getOrDefault(ABILITIES_ENABLED_KEY, PersistentDataType.BOOLEAN, ABILITIES_ENABLED_DEFAULT);
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
