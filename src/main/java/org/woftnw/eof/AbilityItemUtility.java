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
import org.jspecify.annotations.Nullable;
import org.woftnw.eof.ability.Ability;
import org.woftnw.eof.ability.Activation;
import org.woftnw.eof.ability.Archetype;
import org.woftnw.eof.ability.effect.AbilityEffect;

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
	final static Material DEFAULT_MATERIAL = Material.GRAY_DYE;

	private static @NonNull Component label(String label) {
		return text(String.format("- %s:", label)).color(LABEL_COLOR).decorate(TextDecoration.ITALIC);
	}

	private static Component activationComponent(@NonNull Activation activation) {
		String[] keybinds = activation.getKeybinds();
		Component message = text("").color(MessageUtility.INFO_TEXT_COLOR).decoration(TextDecoration.ITALIC, false);
		for (int i = 0; i < keybinds.length; i++) {
			String activationKeybind = keybinds[i];
			message = message.append(keybind(activationKeybind));
			if (i < keybinds.length - 1) {
				message = message.append(text(" + "));
			}
		}
		return message;
	}

	private static @NonNull List<Component> effectList(AbilityEffect @NonNull [] effects) {
		final List<Component> LINES = new ArrayList<>();

		for (final AbilityEffect EFFECT : effects) {
			final Component DESCRIPTION = EFFECT.getDescription();
			final TextColor COLOR = EFFECT.getTarget().getTextColor();
			final TextComponent LINE = text("• ").color(COLOR).decoration(TextDecoration.ITALIC, false)
					.append(DESCRIPTION).decoration(TextDecoration.ITALIC, false);
			LINES.add(LINE);
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
	 * @param effects The AbilityEffects.
	 * @param itemModel The path of the custom item model to use.
	 * @param cooldown The cooldown length of the ability.
	 * @return The resulting ItemStack.
	 */
	public static @NonNull ItemStack createItem(
			@Nullable Material material,
			@NonNull TextComponent name,
			@NonNull Tribe tribe,
			@NonNull Archetype archetype,
			@NonNull Activation activation,
			@NonNull AbilityEffect[] effects,
			@Nullable String itemModel,
			int cooldown
	) {
		if (material == null) material = DEFAULT_MATERIAL;
		final ItemStack ITEM_STACK = new ItemStack(material, 1);

		ItemMeta itemMeta = ITEM_STACK.getItemMeta();
		itemMeta.customName(name.decoration(TextDecoration.ITALIC, false));

		final List<Component> LORE = new ArrayList<>();

		LORE.add(label("Tribe"));
		LORE.add(text(tribe.getTribeName()).color(tribe.getTextColor()).decoration(TextDecoration.ITALIC, false));
		LORE.add(label("Archetype"));
		LORE.add(text(archetype.getName()).color(MessageUtility.INFO_TEXT_COLOR).decoration(TextDecoration.ITALIC, false));
		LORE.add(label("Activation"));
		LORE.add(activationComponent(activation));
		if (effects.length != 0) {
			LORE.add(label("Effects"));
			LORE.addAll(effectList(effects));
		}
		if (cooldown != 0) {
			LORE.add(label("Cooldown"));
			final long COOLDOWN_SECS = Math.round((float) cooldown / 20);
			LORE.add(text(String.format("%ss", COOLDOWN_SECS)).color(MessageUtility.INFO_TEXT_COLOR).decoration(TextDecoration.ITALIC, false));
		}

		itemMeta.lore(LORE);
		if (itemModel != null) itemMeta.setItemModel(NamespacedKey.fromString(itemModel));

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
	 * @param effects The AbilityEffects.
	 * @param itemModel The path of the custom item model to use.
	 * @return The resulting ItemStack.
	 */
	public static @NonNull ItemStack createItem(
			@Nullable Material material,
			@NonNull TextComponent name,
			@NonNull Tribe tribe,
			@NonNull Archetype archetype,
			@NonNull Activation activation,
			@NonNull AbilityEffect[] effects,
			@Nullable String itemModel
	) {
		return createItem(material, name, tribe, archetype, activation, effects, itemModel, 0);
	}
}
