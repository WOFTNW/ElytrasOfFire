package org.woftnw.eof;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import org.woftnw.eof.attribute.Attribute;
import org.woftnw.eof.command.RootCommand;

public class ElytrasOfFire extends JavaPlugin {

	private PlayerUtility playerUtility;
	private TribeManager tribeManager;

	@Override
	public void onEnable() {
		playerUtility = new PlayerUtility(this);
		tribeManager = new TribeManager(this);

		// Register commands
		this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
			LiteralCommandNode<CommandSourceStack> rootCommand = new RootCommand(this).getCommandData().build();
			event.registrar().register(rootCommand);
		});
	}

	public TribeManager getTribeManager() {
		return tribeManager;
	}

	@Override
	public void onDisable() {
		tribeManager.disable();
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
