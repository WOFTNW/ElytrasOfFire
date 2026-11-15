package org.woftnw.eof.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.dialog.Dialog;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.MessageUtility;
import org.woftnw.eof.exception.NoTribeException;

import java.util.Objects;

/**
 * The settings command.
 */
public class SettingsCommand extends PluginCommand {

	@Override
	LiteralArgumentBuilder<CommandSourceStack> getCommandData() {
		LiteralArgumentBuilder<CommandSourceStack> settingsCommand = Commands.literal("settings")
				.then(Commands.literal("availability").executes(this::executeAvailability));

		settingsCommand.executes(this::execute);

		return settingsCommand;
	}

	private int execute(@NonNull CommandContext<CommandSourceStack> context) {
		if (context.getSource().getExecutor() instanceof final Player PLAYER) {
			Dialog dialog = ElytrasOfFire.getPlugin().getDialogManager().getUserSettingsDialog(PLAYER);
			Objects.requireNonNull(context.getSource().getExecutor()).showDialog(dialog);
		} else {
			context.getSource().getSender().sendMessage(MessageUtility.buildErrorMessage("Only players may execute this command."));
			return 0;
		}

		return Command.SINGLE_SUCCESS;
	}

	private int executeAvailability(@NonNull CommandContext<CommandSourceStack> context) {
		if (context.getSource().getExecutor() instanceof final Player PLAYER) {
			Dialog dialog;
			try {
				dialog = ElytrasOfFire.getPlugin().getDialogManager().getUserDisableAbilitiesDialog(PLAYER);
				Objects.requireNonNull(context.getSource().getExecutor()).showDialog(dialog);
			} catch (NoTribeException e) {
				respondError(context, "You must join a tribe first.");
			}
		} else {
			context.getSource().getSender().sendMessage(MessageUtility.buildErrorMessage("Only players may execute this command."));
			return 0;
		}

		return Command.SINGLE_SUCCESS;
	}

}
