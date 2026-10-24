package org.woftnw.eof;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.exception.NoTribeException;
import org.woftnw.eof.exception.NotEnoughAbilitySlotsException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Contains useful player utilities. This should only be created once on enable by this plugin.
 */
public class PlayerUtility {

	// Player data is stored in the persistent data container (PDC).
	// See https://docs.papermc.io/paper/dev/pdc/

	private static final boolean ATTRIBUTES_ENABLED_DEFAULT = false;
	private static final boolean ABILITIES_ENABLED_DEFAULT = false;

	private final ElytrasOfFire PLUGIN;
	private final NamespacedKey TRIBE_KEY;
	private final NamespacedKey ATTRIBUTES_ENABLED_KEY;
	private final NamespacedKey ABILITIES_ENABLED_KEY;
	private final NamespacedKey ATTACK_TARGET_KEY;
	private final NamespacedKey ABILITY_DATA_KEY;

	/**
	 * Create a PlayerUtility.
	 *
	 * @param plugin The plugin instance.
	 */
	public PlayerUtility(ElytrasOfFire plugin) {
		PLUGIN = plugin;
		TRIBE_KEY = new NamespacedKey(plugin, "tribe");
		ATTRIBUTES_ENABLED_KEY = new NamespacedKey(plugin, "attributes_enabled");
		ABILITIES_ENABLED_KEY = new NamespacedKey(plugin, "abilities_enabled");
		ATTACK_TARGET_KEY = new NamespacedKey(plugin, "attack_target");
		ABILITY_DATA_KEY = new NamespacedKey(plugin, "ability_data");
	}

	public void setPlayerAttackTarget(@NonNull Player player, @NonNull LivingEntity target) {
		PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
		persistentDataContainer.set(ATTACK_TARGET_KEY, PersistentDataType.STRING, target.getUniqueId().toString());
	}

	@Nullable
	public UUID getPlayerAttackTarget(@NonNull Player player) {
		PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
		String uuidString = persistentDataContainer.get(ATTACK_TARGET_KEY, PersistentDataType.STRING);
		if (uuidString == null) return null;
		return UUID.fromString(uuidString);
	}

	/**
	 * Enable or disable a player's tribal attributes.
	 *
	 * @param player The player whose state to change.
	 * @param enabled Whether to enable attributes.
	 */
	public void setPlayerAttributesEnabled(@NonNull Player player, boolean enabled) throws NoTribeException {
		final PersistentDataContainer PDC = player.getPersistentDataContainer();
		PDC.set(ATTRIBUTES_ENABLED_KEY, PersistentDataType.BOOLEAN, enabled);

		final AttributeManager ATTRIBUTE_MANAGER = PLUGIN.getTribeManager().getAttributeManager();
		if (enabled) {
			ATTRIBUTE_MANAGER.apply(player);
		} else {
			ATTRIBUTE_MANAGER.unapply(player);
		}
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
	public void setPlayerAbilitiesEnabled(@NonNull Player player, boolean enabled) throws NotEnoughAbilitySlotsException {
		AbilityManager abilityManager = PLUGIN.getTribeManager().getAbilityManager();
		if (enabled) {
			abilityManager.givePlayerAbilities(player);
		} else {
			abilityManager.removeAbilitiesFromInventory(player.getInventory());
		}

		PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
		persistentDataContainer.set(ABILITIES_ENABLED_KEY, PersistentDataType.BOOLEAN, enabled);
	}

	/**
	 * Disable a player's tribal abilities.
	 *
	 * @param player The player whose state to change.
	 */
	public void disablePlayerAbilities(@NonNull Player player) {
		AbilityManager abilityManager = PLUGIN.getTribeManager().getAbilityManager();
		abilityManager.removeAbilitiesFromInventory(player.getInventory());

		PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
		persistentDataContainer.set(ABILITIES_ENABLED_KEY, PersistentDataType.BOOLEAN, false);
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
		return PLUGIN.getTribeManager().getTribeFromPDCValue(tribeString);
	}

	public void setEntityAbilityData(@NonNull LivingEntity target, String dataString, boolean apply) {
		PersistentDataContainer persistentDataContainer = target.getPersistentDataContainer();
		List<String> strings = persistentDataContainer.get(ABILITY_DATA_KEY, PersistentDataType.LIST.strings());
		boolean updated = false;

		if (strings == null) strings = new ArrayList<>();

		if (strings.contains(dataString) && !apply) {
			updated = true;
			strings.remove(dataString);
		} else if (apply) {
			updated = true;
			strings.add(dataString);
		}

		if (updated) persistentDataContainer.set(ABILITY_DATA_KEY, PersistentDataType.LIST.strings(), strings);
	}
}
