package org.woftnw.eof;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import org.woftnw.eof.command.RootCommand;

public class ElytrasOfFire extends JavaPlugin {

	private PlayerUtility playerUtility;

	@Override
	public void onEnable() {
		getLogger().info("Elytras of Fire is being enabled.");
		playerUtility = new PlayerUtility(this);

		// Register commands
		this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
			LiteralCommandNode<CommandSourceStack> rootCommand = new RootCommand(this).getCommandData().build();
			event.registrar().register(rootCommand);
		});
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
