package org.woftnw.eof;

import org.bukkit.Bukkit;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ability.Ability;
import org.woftnw.eof.ability.Activation;
import org.woftnw.eof.ability.ActiveAbility;
import org.woftnw.eof.exception.AbilityActivationException;
import org.woftnw.eof.exception.NoTribeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AbilityManager implements Listener {
	private final ElytrasOfFire PLUGIN;
	private final List<Ability> ABILITIES;
	private final PlayerUtility PLAYER_UTILITY;

	public AbilityManager(@NonNull ElytrasOfFire plugin) {
		PLUGIN = plugin;
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
	 */
	public void givePlayerAbilities(@NonNull Player player) throws NoTribeException {
		final PlayerInventory INVENTORY = player.getInventory();
		putAbilityItemsInInventory(player, INVENTORY);
	}

	public void putAbilityItemsInInventory(Player player, Inventory inventory) throws NoTribeException {
		final Tribe TRIBE = PLAYER_UTILITY.getPlayerTribe(player);
		if (TRIBE == null) throw new NoTribeException();

		final List<ActiveAbility> ACTIVE_ABILITIES = TRIBE.getActiveAbilities();

		final List<ActiveAbility> UNASSIGNED_ABILITIES = new ArrayList<>();
		final List<ItemStack> ITEMS_TO_MOVE = new ArrayList<>();

		// For abilities with assigned slot, put them in assigned slot
		for (ActiveAbility ABILITY : ACTIVE_ABILITIES) {
			final Integer SLOT_ID = PLAYER_UTILITY.getPlayerAbilitySlot(player, ABILITY);
			if (SLOT_ID == null) {
				UNASSIGNED_ABILITIES.add(ABILITY);
			} else {
				ItemStack EXISTING_ITEM = inventory.getItem(SLOT_ID);
				if (EXISTING_ITEM != null && !EXISTING_ITEM.isEmpty()) {
					ITEMS_TO_MOVE.add(EXISTING_ITEM);
				}
				inventory.setItem(SLOT_ID, ABILITY.getActivationItem());
			}
		}

		// For unassigned abilities, put them in rightmost slots not already taken by abilities.
		for (int slotId = 8; slotId >= 0; slotId--) {
			if (UNASSIGNED_ABILITIES.isEmpty()) break;
			final ItemStack EXISTING_ITEM = inventory.getItem(slotId);
			if (EXISTING_ITEM != null && !EXISTING_ITEM.isEmpty()) {
				boolean canPutHere = true;
				for (final ActiveAbility ABILITY : ACTIVE_ABILITIES) {
					if (EXISTING_ITEM.isSimilar(ABILITY.getActivationItem())) {
						canPutHere = false;
						break;
					}
				}
				if (!canPutHere) continue;
				ITEMS_TO_MOVE.add(EXISTING_ITEM);
			};
			inventory.setItem(slotId, UNASSIGNED_ABILITIES.getLast().getActivationItem());
			UNASSIGNED_ABILITIES.removeLast();
		}

		// Give back items that were in the way
		for (final ItemStack ITEM : ITEMS_TO_MOVE) {
			player.give(ITEM);
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

		// I have not tested whether this is specific to a melee attack or if this is also true on a player's arrow or
		// other type of attack.
		if (!event.getDamageSource().getDamageType().equals(DamageType.PLAYER_ATTACK)) return;

		if (!(event.getEntity() instanceof final LivingEntity ENTITY)) return;
		PLUGIN.getPlayerUtility().setPlayerAttackTarget(PLAYER, ENTITY);

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
		if (!(event.getWhoClicked() instanceof final Player PLAYER)) return;

		// If this is the ability slot configuration menu, don't do anything.
		// TODO: Movement within the config inventory is fine, but movement between player inventory and config
		//  inventory is not.
		if (DialogManager.isAbilitySlotMenu(event.getView())) return;

		for (final Ability ABILITY : ABILITIES) {
			if (!(ABILITY instanceof ActiveAbility ability)) continue;
			if (!ability.getActivationItem().equals(ITEM)) continue;
			event.setCancelled(true);

			Bukkit.getScheduler().runTask(PLUGIN, () -> {
				removeAbilitiesFromInventory(Objects.requireNonNull(event.getClickedInventory()));
				try {
					givePlayerAbilities(PLAYER);
				} catch (NoTribeException e) {
					// This should not happen.
					PLUGIN.getLogger().warning("Failed to reset player abilities after inventory click: " + e.getMessage());
					PLUGIN.getPlayerUtility().disablePlayerAbilities(PLAYER);
				}
			});
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
			default -> {
			    return false;
			}
		}
	}
}
