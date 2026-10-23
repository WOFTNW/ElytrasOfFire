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
import org.woftnw.eof.Tribe;
import org.woftnw.eof.command.argument.TribeArgument;

public class TribeCommand extends PluginCommand {

	public TribeCommand(ElytrasOfFire plugin) {
		super(plugin);
	}

	@Override
	public LiteralArgumentBuilder<CommandSourceStack> getCommandData() {
		LiteralArgumentBuilder<CommandSourceStack> tribeCommand = Commands.literal("tribe");

		LiteralArgumentBuilder<CommandSourceStack> joinSubcommand = Commands.literal("join");
		joinSubcommand.requires(source -> source.getSender().hasPermission("eof.tribe.set"));
		joinSubcommand.then(Commands.argument("tribe", new TribeArgument()));
		joinSubcommand.executes(this::executeJoin);

		return tribeCommand;
	}

	private int executeJoin(@NonNull CommandContext<CommandSourceStack> context) {
		final Tribe TRIBE = context.getArgument("tribe", Tribe.class);

		Entity executor = context.getSource().getExecutor();
		if (!(executor instanceof Player player)) {
			respondError(context, "You must specify a target.");
			return 0;
		}

		PLUGIN.getPlayerUtility().setPlayerTribe(player, TRIBE);
		respond(context, String.format("You have joined tribe %s", TRIBE.getTribeName()));
		return Command.SINGLE_SUCCESS;
	}
}
