package org.woftnw.eof;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ability.Ability;
import org.woftnw.eof.ability.Activation;
import org.woftnw.eof.ability.ActiveAbility;
import org.woftnw.eof.exception.AbilityActivationException;
import org.woftnw.eof.exception.NotEnoughAbilitySlotsException;

import java.util.ArrayList;
import java.util.List;

public class AbilityManager implements Listener {
	private static final int ABILITY_LIMIT = 3;

	private final List<Ability> ABILITIES;
	private final PlayerUtility PLAYER_UTILITY;

	public AbilityManager(@NonNull ElytrasOfFire plugin) {
		ABILITIES = new ArrayList<>();
		PLAYER_UTILITY = plugin.getPlayerUtility();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public void registerAbility(Ability ability) {
		ABILITIES.add(ability);
	}

	/**
	 * Give a player their active ability items.
	 *
	 * @param player The player to give abilities to.
	 * @throws NotEnoughAbilitySlotsException If the player doesn't have enough slots for their abilities.
	 */
	public void givePlayerAbilities(Player player) throws NotEnoughAbilitySlotsException {
		final Tribe TRIBE = PLAYER_UTILITY.getPlayerTribe(player);
		if (TRIBE == null) {
			throw new NullPointerException();
		}
		final List<ActiveAbility> TRIBE_ACTIVE_ABILITIES = TRIBE.getActiveAbilities();

		int slotsNeeded = Math.min(ABILITY_LIMIT, TRIBE_ACTIVE_ABILITIES.size());
		if (!arePlayerAbilitySlotsClear(player, slotsNeeded)) throw new NotEnoughAbilitySlotsException();

		for (int i = 0; i < slotsNeeded; i++) {
			final ActiveAbility ABILITY = TRIBE_ACTIVE_ABILITIES.get(i);
			player.getInventory().setItem(9 - slotsNeeded + i, ABILITY.getActivationItem());
		}
	}

	/**
	 * Remove abilities from an inventory.
	 *
	 * @param inventory The inventory to remove ability items from.
	 */
	public void removeAbilitiesFromInventory(@NonNull Inventory inventory) {
		final int SIZE = inventory.getSize();
		for (int i = 0; i < SIZE; i++) {
			final ItemStack ITEM = inventory.getItem(i);

			for (final Ability ABILITY : ABILITIES) {
				if (!(ABILITY instanceof ActiveAbility ability)) continue;
				if (!ability.getActivationItem().isSimilar(ITEM)) continue;
				inventory.setItem(i, null);
			}
		}
	}

	private boolean arePlayerAbilitySlotsClear(@NonNull Player player, int slotsNeeded) {
		for (int i = 0; i < slotsNeeded; i++) {
			if (player.getInventory().getItem(8 - i) != null) return false;
		}
		return true;
	}

	@EventHandler
	public void onPlayerInteract(@NonNull PlayerInteractEvent event) {
		final Player PLAYER = event.getPlayer();
		final ItemStack ITEM = event.getItem();
		if (ITEM == null) return;

		for (final Ability ABILITY : ABILITIES) {
			if (!(ABILITY instanceof ActiveAbility ability)) continue;
			if (!ability.getActivationItem().equals(ITEM) || !interactMatchesActivation(event, ability.getActivationMethod())) continue;
			try {
				ability.activate(PLAYER);
			} catch (AbilityActivationException e) {
				// Do nothing
			}
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(@NonNull EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof final Player PLAYER)) return;
		final ItemStack ITEM = PLAYER.getInventory().getItemInMainHand();

		for (final Ability ABILITY : ABILITIES) {
			if (!(ABILITY instanceof ActiveAbility ability)) continue;
			if (!ability.getActivationItem().equals(ITEM) || !ability.getActivationMethod().equals(Activation.MELEE)) continue;
			try {
				ability.activate(PLAYER);
			} catch (AbilityActivationException e) {
				// Do nothing
			}
		}
	}

	// Don't allow tampering with ability items
	@EventHandler
	public void onInventoryClick(@NonNull InventoryClickEvent event) {
		final ItemStack ITEM = event.getCurrentItem();

		for (final Ability ABILITY : ABILITIES) {
			if (!(ABILITY instanceof ActiveAbility ability)) continue;
			if (!ability.getActivationItem().equals(ITEM)) continue;
			event.setCancelled(true);
		}
	}

	private boolean interactMatchesActivation(@NonNull PlayerInteractEvent event, @NonNull Activation activation) {
		switch (activation) {
			case Activation.SNEAK_USE -> {
				return (event.getPlayer().isSneaking() && event.getAction().isRightClick());
			}
			case Activation.USE -> {
				return event.getAction().isRightClick();
			}
		}

		return false;
	}
}
