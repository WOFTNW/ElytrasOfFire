package org.woftnw.eof.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.woftnw.eof.ElytrasOfFire;

public class RootCommand extends PluginCommand {

	@Override
	public LiteralArgumentBuilder<CommandSourceStack> getCommandData() {
		LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("eof");
		root.then(new TribeCommand().getCommandData());
		root.then(new AttributesCommand().getCommandData());
		root.then(new AbilitiesCommand().getCommandData());
		root.then(new SettingsCommand().getCommandData());
		return root;
	}
}
