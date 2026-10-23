package org.woftnw.eof;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ability.Activation;
import org.woftnw.eof.ability.Archetype;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.kyori.adventure.text.Component.keybind;
import static net.kyori.adventure.text.Component.text;

/**
 * A utility class for creating ability items.
 */
public class AbilityItemUtility {
	final static TextColor LABEL_COLOR = NamedTextColor.DARK_GRAY;
	final static TextColor NEGATIVE_EFFECT_COLOR = NamedTextColor.RED;
	final static TextColor POSITIVE_EFFECT_COLOR = NamedTextColor.GREEN;

	private static @NonNull Component label(String label) {
		return text(String.format("- %s:", label)).color(LABEL_COLOR).decorate(TextDecoration.ITALIC);
	}

	private static Component activationComponent(@NonNull Activation activation) {
		String[] keybinds = activation.getKeybinds();
		Component message = text("").color(MessageUtility.INFO_TEXT_COLOR);
		for (int i = 0; i < keybinds.length; i++) {
			String activationKeybind = keybinds[i];
			message = message.append(keybind(activationKeybind));
			if (i < keybinds.length - 1) {
				message = message.append(text(" + "));
			}
		}
		return message;
	}

	private static @NonNull List<Component> effectList(Map<String, Long> effects, boolean negative) {
		final List<Component> LINES = new ArrayList<>();
		TextColor color = POSITIVE_EFFECT_COLOR;
		if (negative) color = NEGATIVE_EFFECT_COLOR;

		for (String effectDescriptor : effects.keySet()) {
			TextComponent component = text(effectDescriptor).color(color);
			final Long EFFECT_LENGTH = effects.get(String.format("• %s", effectDescriptor));
			if (EFFECT_LENGTH != 0) {
				final long EFFECT_LENGTH_SECS = Math.round((float) EFFECT_LENGTH / 20);
				component = component.append(text(String.format(" (%ss)", EFFECT_LENGTH_SECS)).color(MessageUtility.INFO_TEXT_COLOR));
			}
			LINES.add(component);
		}
		return LINES;
	}

	/**
	 * Create an ItemStack for an ability item.
	 *
	 * @param material The base material. This is shown if no resource pack is available.
	 * @param name The name of the ability.
	 * @param tribe The tribe this ability belongs to.
	 * @param archetype The archetype of the ability.
	 * @param activation The activation type of the ability.
	 * @param negativeEffects The negative effects and their lengths as a <code>Map<String, Long></code>.
	 * @param positiveEffects The positive effects and their lengths as a <code>Map<String, Long></code>.
	 * @param itemModel The path of the custom item model to use.
	 * @param cooldown The cooldown length of the ability.
	 * @return The resulting ItemStack.
	 */
	public static @NonNull ItemStack createItem(
			Material material,
			TextComponent name,
			@NonNull Tribe tribe,
			@NonNull Archetype archetype,
			Activation activation,
			@NonNull Map<String, Long> negativeEffects,
			Map<String, Long> positiveEffects,
			String itemModel,
			long cooldown
	) {
		final ItemStack ITEM_STACK = new ItemStack(material, 1);

		ItemMeta itemMeta = ITEM_STACK.getItemMeta();
		itemMeta.customName(name);

		final List<Component> LORE = new ArrayList<>();

		LORE.add(label("Tribe"));
		LORE.add(text(tribe.getTribeName()).color(tribe.getTextColor()));
		LORE.add(label("Archetype"));
		LORE.add(text(archetype.getName()).color(MessageUtility.INFO_TEXT_COLOR));
		LORE.add(label("Activation"));
		LORE.add(activationComponent(activation));
		if (!negativeEffects.isEmpty() && !positiveEffects.isEmpty()) {
			LORE.add(label("Effects"));

			LORE.addAll(effectList(negativeEffects, true));
			LORE.addAll(effectList(positiveEffects, false));
		}
		if (cooldown != 0) {
			LORE.add(label("Cooldown"));
			final long COOLDOWN_SECS = Math.round((float) cooldown / 20);
			LORE.add(text(String.format("%ss", COOLDOWN_SECS)).color(MessageUtility.INFO_TEXT_COLOR));
		}

		itemMeta.lore(LORE);
		itemMeta.setItemModel(NamespacedKey.fromString(itemModel));

		ITEM_STACK.setItemMeta(itemMeta);

		return ITEM_STACK;
	}

	/**
	 * Create an ItemStack for an ability item.
	 *
	 * @param material The base material. This is shown if no resource pack is available.
	 * @param name The name of the ability.
	 * @param tribe The tribe this ability belongs to.
	 * @param archetype The archetype of the ability.
	 * @param activation The activation type of the ability.
	 * @param negativeEffects The negative effects and their lengths as a <code>Map<String, Long></code>.
	 * @param positiveEffects The positive effects and their lengths as a <code>Map<String, Long></code>.
	 * @param itemModel The path of the custom item model to use.
	 * @return The resulting ItemStack.
	 */
	public static @NonNull ItemStack createItem(
			Material material,
			TextComponent name,
			@NonNull Tribe tribe,
			@NonNull Archetype archetype,
			Activation activation,
			@NonNull Map<String, Long> negativeEffects,
			Map<String, Long> positiveEffects,
			String itemModel
	) {
		return createItem(material, name, tribe, archetype, activation, negativeEffects, positiveEffects, itemModel, 0);
	}
}
