package org.woftnw.eof.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.MessageUtility;
import org.woftnw.eof.Tribe;
import org.woftnw.eof.command.argument.TribeArgument;

import java.util.List;

import static net.kyori.adventure.text.Component.text;

public class TribeCommand extends PluginCommand {

	public TribeCommand(ElytrasOfFire plugin) {
		super(plugin);
	}

	@Override
	public LiteralArgumentBuilder<CommandSourceStack> getCommandData() {
		LiteralArgumentBuilder<CommandSourceStack> tribeCommand = Commands.literal("tribe");

		LiteralArgumentBuilder<CommandSourceStack> joinSubcommand = Commands.literal("join");
		joinSubcommand.requires(source -> source.getSender().hasPermission("eof.tribe.set"));
		joinSubcommand.then(
				Commands.argument("tribe", new TribeArgument())
						.executes(this::executeJoin)
						.then(
								Commands.argument("targets", ArgumentTypes.players())
										.requires(source -> source.getSender().hasPermission("eof.tribe.set.others"))
										.executes(this::executeJoinOther)
						)
		);

		tribeCommand.then(joinSubcommand);

		return tribeCommand;
	}

	private int executeJoin(@NonNull CommandContext<CommandSourceStack> context) {
		final Tribe TRIBE = context.getArgument("tribe", Tribe.class);

		Entity executor = context.getSource().getExecutor();
		if (!(executor instanceof Player player)) {
			respondError(context, "You must specify at least one target.");
			return 0;
		}

		PLUGIN.getPlayerUtility().setPlayerTribe(player, TRIBE);

		final Component MESSAGE = text("You have joined tribe ").color(MessageUtility.INFO_TEXT_COLOR)
				.append(text(TRIBE.getTribeName()).color(TRIBE.getTextColor()));
		respond(context, MESSAGE);

		return Command.SINGLE_SUCCESS;
	}

	private int executeJoinOther(@NonNull CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
		final Tribe TRIBE = context.getArgument("tribe", Tribe.class);
		final PlayerSelectorArgumentResolver RESOLVER = context.getArgument("targets", PlayerSelectorArgumentResolver.class);
		final List<Player> TARGETS = RESOLVER.resolve(context.getSource());

		for (final Player PLAYER : TARGETS) {
			PLUGIN.getPlayerUtility().setPlayerTribe(PLAYER, TRIBE);
		}

		final int TARGET_COUNT = TARGETS.size();
		String SP_PLAYER = MessageUtility.singularOrPlural(TARGET_COUNT, "player", "players");
		final Component MESSAGE = text(String.format("Set %s %s tribe ", TARGET_COUNT, SP_PLAYER)).color(MessageUtility.INFO_TEXT_COLOR)
				.append(text(TRIBE.getTribeName()).color(TRIBE.getTextColor()));
		respond(context, MESSAGE);

		return Command.SINGLE_SUCCESS;
	}
}
