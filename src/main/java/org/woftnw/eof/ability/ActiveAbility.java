package org.woftnw.eof.ability;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.List;

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
	 * @param name The name of the ability.
	 * @param selfEffects The effects applied to the user.
	 * @param activationItem The item used for activation.
	 * @param activationMethod The activation method.
	 */
	public ActiveAbility(TextComponent name, PotionEffect[] selfEffects, ItemStack activationItem, Activation activationMethod, Archetype archetype) {
		super(name, selfEffects);
		ACTIVATION_ITEM = activationItem;
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
