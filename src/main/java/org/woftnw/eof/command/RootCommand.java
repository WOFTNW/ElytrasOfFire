package org.woftnw.eof.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.woftnw.eof.ElytrasOfFire;

public class RootCommand extends PluginCommand {

	public RootCommand(ElytrasOfFire plugin) {
		super(plugin);
	}

	@Override
	public LiteralArgumentBuilder<CommandSourceStack> getCommandData() {
		LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("eof");
		root.then(new TribeCommand(PLUGIN).getCommandData());
		root.then(new AttributesCommand(PLUGIN).getCommandData());
		root.then(new AbilitiesCommand(PLUGIN).getCommandData());
		return root;
	}
}
