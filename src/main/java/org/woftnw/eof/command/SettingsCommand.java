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

import java.util.Objects;

/**
 * The settings command.
 */
public class SettingsCommand extends PluginCommand {

	public SettingsCommand(ElytrasOfFire plugin) {
		super(plugin);
	}

	@Override
	LiteralArgumentBuilder<CommandSourceStack> getCommandData() {
		LiteralArgumentBuilder<CommandSourceStack> settingsCommand = Commands.literal("settings");

		settingsCommand.executes(this::execute);

		return settingsCommand;
	}

	private int execute(@NonNull CommandContext<CommandSourceStack> context) {
		if (context.getSource().getExecutor() instanceof final Player PLAYER) {
			Dialog dialog = PLUGIN.getDialogManager().getUserSettingsDialog(PLAYER);
			Objects.requireNonNull(context.getSource().getExecutor()).showDialog(dialog);
		} else {
			context.getSource().getSender().sendMessage(MessageUtility.buildErrorMessage("Only players may execute this command."));
			return 0;
		}

		return Command.SINGLE_SUCCESS;
	}


}
