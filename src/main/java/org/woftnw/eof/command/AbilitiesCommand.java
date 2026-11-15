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
import org.woftnw.eof.exception.NoTribeException;
import org.woftnw.eof.exception.TribeNotFoundException;

public class AbilitiesCommand extends PluginCommand {

	@Override
	LiteralArgumentBuilder<CommandSourceStack> getCommandData() {
		LiteralArgumentBuilder<CommandSourceStack> abilitiesCommand = Commands.literal("abilities");
		abilitiesCommand.requires(source -> source.getSender().hasPermission("eof.abilities.set"));
		abilitiesCommand.executes(this::execute);

		return abilitiesCommand;
	}

	private int execute(@NonNull CommandContext<CommandSourceStack> context) {
		Entity executor = context.getSource().getExecutor();
		if (!(executor instanceof Player player)) {
			respondError(context, "You must specify a target.");
			return 0;
		}

		boolean currentlyEnabled = PlayerUtility.arePlayerPlayerAbilitiesEnabled(player);
		boolean newState = !currentlyEnabled;

		try {
			PlayerUtility.setPlayerAbilitiesEnabled(player, newState);
		} catch (NoTribeException e) {
			respondError(context, "You must join a tribe first.");
			return 0;
		} catch (TribeNotFoundException e) {
			respondError(context, e.getMessage());
			return 0;
		}

		String newStateDescriptor;
		if (newState) newStateDescriptor = "on";
		else newStateDescriptor = "off";

		respond(context, String.format("Toggled tribal abilities %s.", newStateDescriptor));
		return Command.SINGLE_SUCCESS;
	}
}
