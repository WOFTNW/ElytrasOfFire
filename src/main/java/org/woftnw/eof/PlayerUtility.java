package org.woftnw.eof;

import io.papermc.paper.persistence.PersistentDataKey;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ability.ActiveAbility;
import org.woftnw.eof.exception.NoTribeException;
import org.woftnw.eof.exception.TribeNotFoundException;

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

	private static final ElytrasOfFire PLUGIN = ElytrasOfFire.getPlugin(ElytrasOfFire.class);

	/**
	 * Set the data of a {@link LivingEntity}'s {@link PersistentDataContainer} using a {@link NamespacedKey} and {@link PersistentDataType}.
	 * <p>
	 * It is easier to use a {@link PersistentDataKey}, which stores both the <code>NamespacedKey</code> and <code>PersistentDataType</code>. The <code>PersistentDataKey</code>s used by Elytras of Fire can be found in the {@link EoFPersistentDataKeys} class.
	 * </p>
	 * <p>
	 * You can create your own like this:
	 * </p>
	 * <pre>{@code
	 * public static final PersistentDataKey<String> TRACKED_VALUE io.papermc.paper.persistence.PersistentDataType.of(
	 *     new NamespacedKey(ExamplePlugin.getPlugin(ExamplePlugin), "my_config_value"),
	 *     PersistentDataType.STRING
	 * )
	 * }</pre>
	 *
	 * @param entity The entity to act upon.
	 * @param key    The <code>NamespacedKey</code> to write to.
	 * @param type   The <code>PersistentDataType</code>.
	 * @param value  The value to write.
	 * @param <C>    The retrieved object type when applying this tag type.
	 * @param <P>    The primary object type that is stored in the given tag.
	 * @see PersistentDataKey
	 */
	public static <C, P> void setPlayerData(@NonNull LivingEntity entity, @NonNull NamespacedKey key, PersistentDataType<P, C> type, C value) {
		PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
		persistentDataContainer.set(key, type, value);
	}

	/**
	 * Set the data of a {@link LivingEntity}'s {@link PersistentDataContainer} use a {@link PersistentDataKey}.
	 *
	 * @param entity The entity to act upon.
	 * @param key    The <code>PersistentDataKey</code> to write to.
	 * @param value  The value to write.
	 * @param <C>    The retrieved object type when applying this tag type.
	 */
	public static <C> void setPlayerData(@NonNull LivingEntity entity, @NonNull PersistentDataKey<C> key, C value) {
		setPlayerData(entity, key.key(), key.dataType(), value);
	}

	/**
	 * Get the data of a {@link LivingEntity}'s {@link PersistentDataContainer} using a {@link NamespacedKey} and {@link PersistentDataType}.
	 *
	 * @param entity The entity to act upon.
	 * @param key    The <code>NamespacedKey</code> to write to.
	 * @param type   The <code>PersistentDataType</code>.
	 * @param <C>    The retrieved object type when applying this tag type.
	 * @param <P>    The primary object type that is stored in the given tag.
	 * @return The possibly-<code>null</code> value currently stored.
	 */
	public static <C, P> C getPlayerData(@NonNull LivingEntity entity, @NonNull NamespacedKey key, PersistentDataType<P, C> type) {
		PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
		return persistentDataContainer.get(key, type);
	}

	public static <C> C getPlayerData(@NonNull LivingEntity entity, @NonNull PersistentDataKey<C> key) {
		return getPlayerData(entity, key.key(), key.dataType());
	}

	public static <C, P> C getPlayerDataOrDefault(@NonNull LivingEntity entity, @NonNull NamespacedKey key, PersistentDataType<P, C> type, C defaultValue) {
		PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
		C value = persistentDataContainer.get(key, type);
		if (value == null) return defaultValue;
		else return value;
	}

	public static <C> C getPlayerDataOrDefault(@NonNull LivingEntity entity, @NonNull PersistentDataKey<C> key, C defaultValue) {
		return getPlayerDataOrDefault(entity, key.key(), key.dataType(), defaultValue);
	}

	public static <C> void setItemItemInList(@NonNull LivingEntity target, PersistentDataKey<List<C>> key, C item, boolean include) {
		List<C> items = getPlayerData(target, key);
		boolean updated = false;

		if (items == null) items = new ArrayList<>();

		if (items.contains(item) && !include) {
			updated = true;
			items.remove(item);
		} else if (include) {
			updated = true;
			items.add(item);
		}

		if (updated) setPlayerData(target, key, items);
	}

	public static <C> boolean isItemItemInList(@NonNull LivingEntity target, PersistentDataKey<List<C>> key, C item) {
		List<C> items = getPlayerData(target, key);
		boolean updated = false;

		if (items == null) items = new ArrayList<>();

		return items.contains(item);
	}

	public static void setPlayerAttackTarget(@NonNull LivingEntity entity, @NonNull LivingEntity target) {
		setPlayerData(entity, EoFPersistentDataKeys.ATTACK_TARGET_KEY, target.getUniqueId());
	}

	@Nullable
	public static UUID getPlayerAttackTarget(@NonNull Player player) {
		return getPlayerData(player, EoFPersistentDataKeys.ATTACK_TARGET_KEY);
	}

	/**
	 * Enable or disable a player's tribal attributes.
	 *
	 * @param player  The player whose state to change.
	 * @param enabled Whether to enable attributes.
	 */
	public static void setPlayerAttributesEnabled(@NonNull Player player, boolean enabled) throws NoTribeException, TribeNotFoundException {
		setPlayerData(player, EoFPersistentDataKeys.ATTRIBUTES_ENABLED_KEY, enabled);

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
	public static boolean arePlayerPlayerAttributesEnabled(@NonNull Player player) {
		return getPlayerDataOrDefault(player, EoFPersistentDataKeys.ATTRIBUTES_ENABLED_KEY, ATTRIBUTES_ENABLED_DEFAULT);
	}

	/**
	 * Enable or disable a player's tribal abilities.
	 *
	 * @param player  The player whose state to change.
	 * @param enabled Whether to enable abilities.
	 */
	public static void setPlayerAbilitiesEnabled(@NonNull Player player, boolean enabled) throws NoTribeException, TribeNotFoundException {
		AbilityManager abilityManager = PLUGIN.getTribeManager().getAbilityManager();
		abilityManager.removeAbilitiesFromInventory(player.getInventory());
		if (enabled) {
			abilityManager.givePlayerAbilities(player);
		}

		setPlayerData(player, EoFPersistentDataKeys.ABILITIES_ENABLED_KEY, enabled);
	}

	/**
	 * Disable a player's tribal abilities.
	 *
	 * @param player The player whose state to change.
	 */
	public static void disablePlayerAbilities(@NonNull Player player) {
		AbilityManager abilityManager = PLUGIN.getTribeManager().getAbilityManager();
		abilityManager.removeAbilitiesFromInventory(player.getInventory());

		setPlayerData(player, EoFPersistentDataKeys.ABILITIES_ENABLED_KEY, false);
	}

	/**
	 * Check whether a player's tribal abilities are enabled.
	 *
	 * @param player The player whose state to get.
	 * @return Whether abilities are enabled or disabled.
	 */
	public static boolean arePlayerPlayerAbilitiesEnabled(@NonNull Player player) {
		return getPlayerDataOrDefault(player, EoFPersistentDataKeys.ABILITIES_ENABLED_KEY, ABILITIES_ENABLED_DEFAULT);
	}

	/**
	 * Set a player's tribe.
	 *
	 * @param player The player whose tribe to change.
	 * @param tribe  The tribe to change to.
	 */
	public static void setPlayerTribe(@NonNull Player player, @NonNull Tribe tribe) {
		setPlayerData(player, EoFPersistentDataKeys.TRIBE_KEY, tribe);
	}

	/**
	 * Get a player's tribe.
	 *
	 * @param player The player whose tribe to get.
	 * @return The tribe of the player, or null if nonexistent or unrecognized.
	 */
	@Nullable
	public static Tribe getPlayerTribe(@NonNull Player player) throws TribeNotFoundException {
		return getPlayerData(player, EoFPersistentDataKeys.TRIBE_KEY);
	}

	/**
	 * Apply or remove an ability data string on a {@link LivingEntity}.
	 *
	 * @param target     The target to act upon.
	 * @param dataString The data string.
	 * @param apply      <code>true</code> to apply it, <code>false</code> to remove it.
	 */
	public static void setEntityAbilityData(@NonNull LivingEntity target, String dataString, boolean apply) {
		setItemItemInList(target, EoFPersistentDataKeys.ABILITY_DATA_KEY, dataString, apply);
	}

	/**
	 * Check if an entity has a given ability data string.
	 *
	 * @param target     The target to check.
	 * @param dataString The ability data string to check.
	 */
	public static void isEntityAbilityData(@NonNull LivingEntity target, String dataString) {
		isItemItemInList(target, EoFPersistentDataKeys.ABILITY_DATA_KEY, dataString);
	}

	/**
	 * Set the slot that a specific ability should use for a given player.
	 *
	 * @param player    The player to act upon.
	 * @param abilityId The <code>NamespacedKey</code> of the {@link ActiveAbility}.
	 * @param slotId    The numeric ID of the slot this ability should use.
	 */
	public static void setPlayerAbilitySlot(@NonNull Player player, @NonNull NamespacedKey abilityId, int slotId) {
		final PersistentDataContainer EMPTY_CONTAINER = getEmptyContainer(player);
		final PersistentDataContainer DATA_CONTAINER = getPlayerDataOrDefault(player, EoFPersistentDataKeys.ABILITY_SLOT_CONFIG_KEY, EMPTY_CONTAINER);

		DATA_CONTAINER.set(abilityId, PersistentDataType.INTEGER, slotId);

		setPlayerData(player, EoFPersistentDataKeys.ABILITY_SLOT_CONFIG_KEY, DATA_CONTAINER);
	}

	/**
	 * Get an empty {@link PersistentDataContainer}.
	 *
	 * @param player The player to use the context of.
	 * @return An empty Persistent Data Container.
	 */
	private static @NonNull PersistentDataContainer getEmptyContainer(@NonNull Player player) {
		return player.getPersistentDataContainer().getAdapterContext().newPersistentDataContainer();
	}

	/**
	 * Set the slot that a specific ability should use for a given player.
	 *
	 * @param player  The player to act upon.
	 * @param ability The <code>ActiveAbility</code>.
	 * @param slotId  The numeric ID of the slot this ability should use.
	 */
	public static void setPlayerAbilitySlot(@NonNull Player player, @NonNull ActiveAbility ability, int slotId) {
		setPlayerAbilitySlot(player, ability.getId(), slotId);
	}

	/**
	 * Get the slot ID of the ability for a given player.
	 *
	 * @param player    The player to act upon.
	 * @param abilityId The <code>NamespacedKey</code> of the {@link ActiveAbility}.
	 * @return The numeric ID of the slot this ability uses.
	 */
	@Nullable
	public static Integer getPlayerAbilitySlot(@NonNull Player player, @NonNull NamespacedKey abilityId) {
		final PersistentDataContainer EMPTY_CONTAINER = getEmptyContainer(player);
		final PersistentDataContainer DATA_CONTAINER = getPlayerDataOrDefault(player, EoFPersistentDataKeys.ABILITY_SLOT_CONFIG_KEY, EMPTY_CONTAINER);

		return DATA_CONTAINER.get(abilityId, PersistentDataType.INTEGER);
	}

	/**
	 * Get the slot ID of the ability for a given player.
	 *
	 * @param player  The player to act upon.
	 * @param ability The <code>NamespacedKey</code> of the {@link ActiveAbility}.
	 * @return The numeric ID of the slot this ability uses.
	 */
	@Nullable
	public static Integer getPlayerAbilitySlot(@NonNull Player player, @NonNull ActiveAbility ability) {
		return getPlayerAbilitySlot(player, ability.getId());
	}

	/**
	 * Set a player's {@link ActiveAbility} as enabled or disabled.
	 *
	 * @param target    The player to act upon.
	 * @param abilityId The <code>NamespacedKey</code> of the <code>ActiveAbility</code>.
	 * @param enabled  <code>true</code> to enable, <code>false</code> to disable.
	 */
	public static void setAbilityEnabled(@NonNull LivingEntity target, @NonNull NamespacedKey abilityId, boolean enabled) {
		setItemItemInList(target, EoFPersistentDataKeys.DISABLED_ABILITIES_KEY, abilityId.asString(), !enabled);
	}

	/**
	 * Set a player's {@link ActiveAbility} as enabled or disabled.
	 *
	 * @param target   The player to act upon.
	 * @param ability  The <code>ActiveAbility</code>.
	 * @param enabled <code>true</code> to enable, <code>false</code> to disable.
	 */
	public static void setAbilityEnabled(@NonNull LivingEntity target, @NonNull ActiveAbility ability, boolean enabled) {
		setAbilityEnabled(target, ability.getId(), enabled);
	}

	/**
	 * Check if a player's {@link ActiveAbility} is enabled or disabled.
	 *
	 * @param target    The player to act upon.
	 * @param abilityId The <code>NamespacedKey</code> of the <code>ActiveAbility</code>.
	 * @return <code>true</code> if enabled, <code>false</code> if disabled.
	 */
	public static boolean isAbilityEnabled(@NonNull LivingEntity target, @NonNull NamespacedKey abilityId) {
		return !isItemItemInList(target, EoFPersistentDataKeys.DISABLED_ABILITIES_KEY, abilityId.asString());
	}

	/**
	 * Check if a player's {@link ActiveAbility} is enabled or disabled.
	 *
	 * @param target  The player to act upon.
	 * @param ability The <code>ActiveAbility</code>.
	 * @return <code>true</code> if enabled, <code>false</code> if disabled.
	 */
	public static boolean isAbilityEnabled(@NonNull LivingEntity target, @NonNull ActiveAbility ability) {
		return isAbilityEnabled(target, ability.getId());
	}
}
