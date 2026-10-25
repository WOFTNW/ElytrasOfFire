package org.woftnw.eof.ability;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.Nullable;
import org.woftnw.eof.AbilityItemUtility;
import org.woftnw.eof.Tribe;
import org.woftnw.eof.ability.effect.AbilityEffect;

/**
 * Represents a {@link CooldownAbility} that is activated using an item.
 */
public class ActiveAbility extends CooldownAbility {
	private final ItemStack ACTIVATION_ITEM;
	private final Activation ACTIVATION_METHOD;
	private final Archetype ARCHETYPE;

	/**
	 * Create a new ActiveAbility
	 *
	 * @param plugin                 The origin plugin instance.
	 * @param name                   The name of the ability.
	 * @param effects                The effects to apply on activation. May be empty.
	 * @param activationItemMaterial The material for the activation item or <code>null</code> for the default.
	 * @param itemModel              The item model key for the activation item or <code>null</code> if none.
	 * @param tribe                  The tribe this ability is for.
	 * @param activationMethod       The activation method.
	 * @param archetype              The {@link Archetype} of this ability.
	 * @param cooldown               The time after activation before this ability can be activated again.
	 */
	public ActiveAbility(JavaPlugin plugin, TextComponent name, AbilityEffect[] effects, @Nullable Material activationItemMaterial, @Nullable String itemModel, Tribe tribe, Activation activationMethod, Archetype archetype, int cooldown) {
		super(name, effects, cooldown);
		ACTIVATION_ITEM = AbilityItemUtility.createItem(
				plugin,
				activationItemMaterial,
				name,
				tribe,
				archetype,
				activationMethod,
				effects,
				itemModel,
				cooldown
		);
		ACTIVATION_METHOD = activationMethod;
		ARCHETYPE = archetype;
	}

	/**
	 * Get the activation item.
	 *
	 * @return The activation item for this ability.
	 */
	public ItemStack getActivationItem() {
		return ACTIVATION_ITEM;
	}

	/**
	 * Get the activation method.
	 *
	 * @return The activation method for this ability.
	 */
	public Activation getActivationMethod() {
		return ACTIVATION_METHOD;
	}

	/**
	 * Get the archetype.
	 *
	 * @return The archetype for this ability.
	 */
	public Archetype getArchetype() {
		return ARCHETYPE;
	}
}
