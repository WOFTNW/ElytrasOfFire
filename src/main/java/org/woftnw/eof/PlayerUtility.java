package org.woftnw.eof;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;


public class PlayerUtility {

	// Player data is stored in the persistent data container (PDC).
	// See https://docs.papermc.io/paper/dev/pdc/

	private final NamespacedKey TRIBE_KEY;

	public PlayerUtility(JavaPlugin plugin) {
		this.TRIBE_KEY = new NamespacedKey(plugin, "tribe");
	}

	public void setPlayerTribe(@NonNull Player player, @NonNull Tribe tribe) {
		PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
		persistentDataContainer.set(TRIBE_KEY, PersistentDataType.STRING, tribe.getPDCValue());
	}

	@Nullable
	public Tribe getPlayerTribe(@NonNull Player player) {
		PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
		@Nullable String tribeString = persistentDataContainer.get(TRIBE_KEY, PersistentDataType.STRING);
		return TribeUtility.getTribeFromPDCValue(tribeString);
	}
}
