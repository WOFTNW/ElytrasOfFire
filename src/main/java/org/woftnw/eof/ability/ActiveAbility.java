package org.woftnw.eof.ability;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.woftnw.eof.AbilityItemUtility;
import org.woftnw.eof.Tribe;
import org.woftnw.eof.ability.effect.AbilityEffect;
import org.woftnw.eof.exception.AbilityActivationException;
import org.woftnw.eof.exception.AbilityCooldownException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an {@link Ability} that is activated using an item.
 */
public class ActiveAbility extends Ability {
	private final ItemStack ACTIVATION_ITEM;
	private final Activation ACTIVATION_METHOD;
	private final int COOLDOWN;
	private final boolean TOGGLE;

	private final List<Player> USERS_ACTIVE = new ArrayList<>();

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
	 * @param cooldown               The time after activation before this ability can be activated again.
	 * @param toggle				 Whether this is a toggle ability. Toggle abilities activate every tick if the player is using them (hold + use). When the player presses use again or selects another item, the ability stops activating.
	 */
	public ActiveAbility(JavaPlugin plugin, TextComponent name, AbilityEffect[] effects, @Nullable Material activationItemMaterial, @Nullable String itemModel, Tribe tribe, Activation activationMethod, int cooldown, boolean toggle) {
		super(name, effects);
		COOLDOWN = cooldown;
		TOGGLE = toggle;
		ACTIVATION_ITEM = AbilityItemUtility.createItem(
				plugin,
				activationItemMaterial,
				name,
				tribe,
				activationMethod,
				effects,
				itemModel,
				cooldown
		);
		ACTIVATION_METHOD = activationMethod;

		final long DELAY = 0;
		final long PERIOD = 1;

		if (toggle) {
			plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
				for (int i = USERS_ACTIVE.size() - 1; i >= 0; i--) {
					Player user = USERS_ACTIVE.get(i);
					if (!user.getInventory().getItemInMainHand().equals(ACTIVATION_ITEM)) {
						USERS_ACTIVE.remove(i);
					} else {
						try {
							super.activate(user);
						} catch (AbilityActivationException e) {
							// Do nothing.
						}
					}
				}
			}, DELAY, PERIOD);
		}
	}

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
	 * @param cooldown               The time after activation before this ability can be activated again.
	 */
	public ActiveAbility(JavaPlugin plugin, TextComponent name, AbilityEffect[] effects, @Nullable Material activationItemMaterial, @Nullable String itemModel, Tribe tribe, Activation activationMethod, int cooldown) {
		this(plugin, name, effects, activationItemMaterial, itemModel, tribe, activationMethod, cooldown, false);
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

	@Override
	public void activate(@NonNull Player user) throws AbilityActivationException {
		if (TOGGLE) {
			if (USERS_ACTIVE.contains(user)) {
				USERS_ACTIVE.remove(user);
			} else {
				USERS_ACTIVE.add(user);
			}
		} else {
			int cooldown = user.getCooldown(ACTIVATION_ITEM);
			if (cooldown > 0) {
				throw new AbilityCooldownException(cooldown);
			}
			super.activate(user);
			user.setCooldown(ACTIVATION_ITEM, this.COOLDOWN);
		}
	}
}
