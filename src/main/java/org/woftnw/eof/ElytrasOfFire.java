package org.woftnw.eof;

import org.bukkit.plugin.java.JavaPlugin;

public class ElytrasOfFire extends JavaPlugin {

	private PlayerUtility playerUtility;

	@Override
	public void onEnable() {
		getLogger().info("Elytras of Fire is being enabled.");
		playerUtility = new PlayerUtility(this);
	}

	/**
	 * Get the PlayerUtility.
	 *
	 * @return The PlayerUtility instance.
	 */
	public PlayerUtility getPlayerUtility() {
		return playerUtility;
	}
}
