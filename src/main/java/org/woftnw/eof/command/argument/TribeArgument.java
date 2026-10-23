package org.woftnw.eof.command.argument;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.papermc.paper.command.brigadier.argument.CustomArgumentType;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.Tribe;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class TribeArgument implements CustomArgumentType.Converted<Tribe, String> {
	private static final DynamicCommandExceptionType ERROR_INVALID_TRIBE = new DynamicCommandExceptionType(tribe -> new LiteralMessage(tribe + " is not a valid tribe!"));

	private final ElytrasOfFire PLUGIN;

	public TribeArgument(ElytrasOfFire plugin) {
		PLUGIN = plugin;
	}

	@Override
	public @NonNull Tribe convert(final @NonNull String nativeType) throws CommandSyntaxException {
		final List<Tribe> TRIBES = PLUGIN.getTribeManager().getTribes();
		for (Tribe tribe : TRIBES) {
			if (tribe.getTribeName().toUpperCase(Locale.ROOT).equals(nativeType.toUpperCase(Locale.ROOT))) {
				return tribe;
			}
		}
		throw ERROR_INVALID_TRIBE.create(nativeType);
	}

	@Override
	public <S> @NonNull CompletableFuture<Suggestions> listSuggestions(final @NonNull CommandContext<S> context, final @NonNull SuggestionsBuilder builder) {
		final List<Tribe> TRIBES = PLUGIN.getTribeManager().getTribes();
		for (final Tribe tribe : TRIBES) {
			final String name = tribe.getTribeName();

			// Only suggest if the tribe name matches the user input
			if (name.startsWith(builder.getRemainingLowerCase())) {
				builder.suggest(name);
			}
		}

		return builder.buildFuture();
	}

	@Override
	public @NonNull ArgumentType<String> getNativeType() {
		return StringArgumentType.word();
	}
}
