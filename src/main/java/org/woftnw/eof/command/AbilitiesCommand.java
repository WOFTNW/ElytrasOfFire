package org.woftnw.eof.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.PlayerUtility;

public class AbilitiesCommand extends PluginCommand {

	public AbilitiesCommand(ElytrasOfFire plugin) {
		super(plugin);
	}

	@Override
	LiteralArgumentBuilder<CommandSourceStack> getCommandData() {
		LiteralArgumentBuilder<CommandSourceStack> abilitiesCommand = Commands.literal("abilities");
		abilitiesCommand.requires(source -> source.getSender().hasPermission("eof.abilities.set"));
		abilitiesCommand.executes(this::execute);

		return null;
	}

	private int execute(@NonNull CommandContext<CommandSourceStack> context) {
		Entity executor = context.getSource().getExecutor();
		if (!(executor instanceof Player player)) {
			respondError(context, "You must specify a target.");
			return 0;
		}

		PlayerUtility playerUtility = PLUGIN.getPlayerUtility();
		boolean currentlyEnabled = playerUtility.arePlayerPlayerAbilitiesEnabled(player);
		boolean newState = !currentlyEnabled;
		playerUtility.setPlayerAbilitiesEnabled(player, newState);

		String newStateDescriptor;
		if (newState) newStateDescriptor = "on";
		else newStateDescriptor = "off";

		respond(context, String.format("Toggled tribal abilities %s.", newStateDescriptor));
		return Command.SINGLE_SUCCESS;
	}
}
