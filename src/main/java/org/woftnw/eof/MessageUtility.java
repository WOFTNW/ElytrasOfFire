package org.woftnw.eof;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.jspecify.annotations.NonNull;

import static net.kyori.adventure.text.Component.text;

/**
 * A utility class for creating messages.
 */
public class MessageUtility {

	public static final TextColor INFO_TEXT_COLOR = NamedTextColor.GRAY;

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
		return PREFIX.append(text(message).color(INFO_TEXT_COLOR));
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

	/**
	 * Choose a singular or plural form depending on a number.
	 *
	 * @param number The number to compare.
	 * @param singular The singular form to use if the number is 1.
	 * @param plural The plural form to use otherwise.
	 * @return The resulting form choice.
	 */
	public static String singularOrPlural(int number, String singular, String plural) {
		if (number == 1) return singular;
		else return plural;
	}
}
