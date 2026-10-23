package org.woftnw.eof.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.MessageUtility;

/**
 * Represents a plugin command.
 */
public abstract class PluginCommand {

	/**
	 * The plugin instance.
	 */
	protected final ElytrasOfFire PLUGIN;

	/**
	 * Set up this command.
	 *
	 * @param plugin The plugin instance.
	 */
	public PluginCommand(ElytrasOfFire plugin) {
		this.PLUGIN = plugin;
	}

	/**
	 * Respond to the sender with a message.
	 *
	 * @param context The command context.
	 * @param message The message to send.
	 */
	public static void respond(@NonNull CommandContext<CommandSourceStack> context, String message) {
		final Component MESSAGE = MessageUtility.buildMessage(message);
		context.getSource().getSender().sendMessage(MESSAGE);
	}

	/**
	 * Respond to the sender with a message.
	 *
	 * @param context The command context.
	 * @param message The message to send.
	 */
	public static void respond(@NonNull CommandContext<CommandSourceStack> context, Component message) {
		final Component MESSAGE = MessageUtility.buildMessage(message);
		context.getSource().getSender().sendMessage(MESSAGE);
	}

	/**
	 * Respond to the sender with a red error message.
	 *
	 * @param context The command context.
	 * @param message The message to send.
	 */
	public static void respondError(@NonNull CommandContext<CommandSourceStack> context, String message) {
		final Component MESSAGE = MessageUtility.buildErrorMessage(message);
		context.getSource().getSender().sendMessage(MESSAGE);
	}

	/**
	 * Get the command data for this command.
	 *
	 * @return The command data.
	 */
	abstract LiteralArgumentBuilder<CommandSourceStack> getCommandData();
}
