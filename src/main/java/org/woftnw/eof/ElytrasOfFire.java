package org.woftnw.eof;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import org.woftnw.eof.attributes.Attribute;
import org.woftnw.eof.command.RootCommand;

public class ElytrasOfFire extends JavaPlugin {

	private PlayerUtility playerUtility;
	private AttributeScheduler attributeScheduler;

	@Override
	public void onEnable() {
		playerUtility = new PlayerUtility(this);
		attributeScheduler = new AttributeScheduler(this);

		for (Tribe tribe : Tribe.values()) {
			for (Attribute attribute : tribe.getAttributes()) {
				attributeScheduler.registerAttribute(tribe, attribute);
			}
		}

		// Register commands
		this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
			LiteralCommandNode<CommandSourceStack> rootCommand = new RootCommand(this).getCommandData().build();
			event.registrar().register(rootCommand);
		});
	}

	@Override
	public void onDisable() {
		attributeScheduler.stop();
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
