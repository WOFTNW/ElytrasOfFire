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
import org.woftnw.eof.Tribe;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class TribeArgument implements CustomArgumentType.Converted<Tribe, String> {
	private static final DynamicCommandExceptionType ERROR_INVALID_TRIBE = new DynamicCommandExceptionType(tribe -> new LiteralMessage(tribe + " is not a valid tribe!"));

	@Override
	public @NonNull Tribe convert(final @NonNull String nativeType) throws CommandSyntaxException {
		try {
			return Tribe.valueOf(nativeType.toUpperCase(Locale.ROOT));
		} catch (IllegalArgumentException _) {
			throw ERROR_INVALID_TRIBE.create(nativeType);
		}
	}

	@Override
	public <S> @NonNull CompletableFuture<Suggestions> listSuggestions(final @NonNull CommandContext<S> context, final @NonNull SuggestionsBuilder builder) {
		for (final Tribe flavor : Tribe.values()) {
			final String name = flavor.toString();

			// Only suggest if the flavor name matches the user input
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
