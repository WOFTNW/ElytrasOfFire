package org.woftnw.eof;

import io.papermc.paper.persistence.PersistentDataKey;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.woftnw.eof.io.TribePersistentDataType;
import org.woftnw.eof.io.UUIDPersistentDataType;

import java.util.List;
import java.util.UUID;

/**
 * Contains {@link PersistentDataKey}s that Elytras of Fire uses.
 */
public class EoFPersistentDataKeys {
	private static final JavaPlugin PLUGIN = ElytrasOfFire.getPlugin(ElytrasOfFire.class);
	
	public static final PersistentDataKey<Tribe> TRIBE_KEY = PersistentDataKey.of(new NamespacedKey(PLUGIN, "tribe"), new TribePersistentDataType());
	public static final PersistentDataKey<Boolean> ATTRIBUTES_ENABLED_KEY = PersistentDataKey.of(new NamespacedKey(PLUGIN, "attributes_enabled"), PersistentDataType.BOOLEAN);;
	public static final PersistentDataKey<Boolean> ABILITIES_ENABLED_KEY = PersistentDataKey.of(new NamespacedKey(PLUGIN, "abilities_enabled"), PersistentDataType.BOOLEAN);;
	public static final PersistentDataKey<UUID> ATTACK_TARGET_KEY = PersistentDataKey.of(new NamespacedKey(PLUGIN, "attack_target"), new UUIDPersistentDataType());;
	public static final PersistentDataKey<List<String>> ABILITY_DATA_KEY = PersistentDataKey.of(new NamespacedKey(PLUGIN, "ability_data"), PersistentDataType.LIST.strings());;
	public static final PersistentDataKey<PersistentDataContainer> ABILITY_SLOT_CONFIG_KEY = PersistentDataKey.of(new NamespacedKey(PLUGIN, "ability_slot_config"), PersistentDataType.TAG_CONTAINER);;
	public static final PersistentDataKey<List<String>> DISABLED_ABILITIES_KEY = PersistentDataKey.of(new NamespacedKey(PLUGIN, "ability_disable_config"), PersistentDataType.LIST.strings());;
}
