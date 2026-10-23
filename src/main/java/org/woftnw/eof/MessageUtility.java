package org.woftnw.eof;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jspecify.annotations.NonNull;

import static net.kyori.adventure.text.Component.text;

/**
 * A utility class for creating messages.
 */
public class MessageUtility {

	private static final Component PREFIX = text()
			.content("[").color(NamedTextColor.GRAY)
			.append(text("E").color(NamedTextColor.RED))
			.append(text("o").color(NamedTextColor.GOLD))
			.append(text("F").color(NamedTextColor.RED))
			.append(text("] ").color(NamedTextColor.GRAY))
			.build();

	/**
	 * Build a formatted chat message.
	 *
	 * @param message The string to turn into a message.
	 * @return A formatted {@link Component}.
	 */
	public static @NonNull Component buildMessage(String message) {
		return PREFIX.append(text(message).color(NamedTextColor.GRAY));
	}

	/**
	 * Build a formatted chat message.
	 *
	 * @param message The message to format.
	 * @return A formatted {@link Component}.
	 */
	public static @NonNull Component buildMessage(Component message) {
		return PREFIX.append(message);
	}

	/**
	 * Build an error-formatted chat message.
	 *
	 * @param message The string to turn into a message.
	 * @return A formatted {@link Component}.
	 */
	public static @NonNull Component buildErrorMessage(String message) {
		return buildMessage(text(message).color(NamedTextColor.RED));
	}
}
