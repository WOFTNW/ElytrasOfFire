package org.woftnw.eof;

import io.papermc.paper.connection.PlayerGameConnection;
import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.event.player.PlayerCustomClickEvent;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MenuType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ability.ActiveAbility;
import org.woftnw.eof.exception.NoTribeException;
import org.woftnw.eof.exception.NotEnoughAbilitySlotsException;

import java.util.ArrayList;
import java.util.List;

import static net.kyori.adventure.text.Component.text;

/**
 * Manages Dialogs.
 */
public class DialogManager implements Listener {
	private final static String ABILITY_SLOT_MENU_HOVER_TEXT = "configure_ability_slots";

	/**
	 * The ElytrasOfFire plugin instance.
	 */
	private final ElytrasOfFire PLUGIN;

	/**
	 * Create a new DialogManager. There should only be one instance that belongs to the ElytrasOfFire plugin.
	 *
	 * @param plugin The ElytrasOfFire plugin instance.
	 */
	public DialogManager(@NonNull ElytrasOfFire plugin) {
		PLUGIN = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Get the user settings dialog for a given <code>Player</code>.
	 *
	 * @param player The player whose user settings dialog to get.
	 * @return The user settings Dialog for the player.
	 */
	public @NonNull Dialog getUserSettingsDialog(Player player) {
		final PlayerUtility PLAYER_UTILITY = PLUGIN.getPlayerUtility();

		final boolean ATTRIBUTES_ENABLED = PLAYER_UTILITY.arePlayerPlayerAttributesEnabled(player);
		final boolean ABILITIES_ENABLED = PLAYER_UTILITY.arePlayerPlayerAbilitiesEnabled(player);

		return Dialog.create(builder -> builder.empty()
				.base(
						DialogBase.builder(text("Elytras of Fire Player Settings"))
								.canCloseWithEscape(true)
								.inputs(List.of(
										DialogInput.bool("enable_attributes", text("Enable Attributes"))
												.initial(ATTRIBUTES_ENABLED)
												.build(),
										DialogInput.bool("enable_abilities", text("Enable Abilities"))
												.initial(ABILITIES_ENABLED)
												.build()
								))
								.build()
				)
				.type(DialogType.multiAction(
						List.of(
								ActionButton.create(
										text("Configure Ability Item Slots"),
										text("Change the hotbar slots that your abilities are placed in."),
										256,
										DialogAction.customClick(Key.key("elytrasoffire:configure_ability_slots"), null)
									)
						),
						ActionButton.builder(text("Save and Apply"))
								.action(DialogAction.customClick(Key.key("elytrasoffire:save_player_settings"), null))
								.build(),
						1
				))
		);
	}

	public InventoryView getAbilitySlotMenu(Player player) throws NoTribeException {
		InventoryView inventory = MenuType.GENERIC_9X1.builder()
				.title(text("Move Ability Items").hoverEvent(HoverEvent.showText(text(ABILITY_SLOT_MENU_HOVER_TEXT))))
				.build(player);

		PlayerUtility PLAYER_UTILITY = PLUGIN.getPlayerUtility();

		final Tribe TRIBE = PLAYER_UTILITY.getPlayerTribe(player);
		if (TRIBE == null) throw new NoTribeException();
		final List<ActiveAbility> ACTIVE_ABILITIES = TRIBE.getActiveAbilities();

		List<ActiveAbility> unassignedAbilities = new ArrayList<>();

		for (ActiveAbility ABILITY : ACTIVE_ABILITIES) {
			final Integer SLOT_ID = PLAYER_UTILITY.getPlayerAbilitySlot(player, ABILITY);
			if (SLOT_ID == null) {
				unassignedAbilities.add(ABILITY);
			} else {
				inventory.setItem(SLOT_ID, ABILITY.getActivationItem());
			}
		}

		unassignedAbilities = unassignedAbilities.reversed();

		for (int i = 8; i >= 0; i--) {
			if (unassignedAbilities.isEmpty()) break;
			if (inventory.getItem(i) == null) continue;
			inventory.setItem(i, unassignedAbilities.getFirst().getActivationItem());
			unassignedAbilities.removeFirst();
		}

		return inventory;
	}

	@EventHandler
	public void onInventoryClose(@NonNull InventoryCloseEvent event) {
		final Player PLAYER = (Player) event.getPlayer();
		final InventoryView VIEW = event.getView();

		final HoverEvent<?> HOVER_EVENT = VIEW.title().hoverEvent();
		final boolean IS_ABILITY_SLOT_MENU = HOVER_EVENT != null && !ABILITY_SLOT_MENU_HOVER_TEXT.equals(HOVER_EVENT.toString());
		if (!IS_ABILITY_SLOT_MENU) return;

		final PlayerUtility PLAYER_UTILITY = PLUGIN.getPlayerUtility();
		final Tribe TRIBE = PLAYER_UTILITY.getPlayerTribe(PLAYER);
		if (TRIBE == null) {
			// This shouldn't happen.
			PLAYER.sendMessage(MessageUtility.buildErrorMessage("Unable to save slots because your tribe could not be identified."));
			return;
		}

		@Nullable ItemStack @NotNull [] contents = event.getInventory().getContents();
		for (int i = 0; i < contents.length; i++) {
			ItemStack ITEM = contents[i];
			if (ITEM == null) continue;
			for (ActiveAbility ability : TRIBE.getActiveAbilities()) {
				if (ability.getActivationItem().isSimilar(ITEM)) {
					PLAYER_UTILITY.setPlayerAbilitySlot(PLAYER, ability, i);
				}
			}
		}

		PLAYER.sendMessage(MessageUtility.buildMessage("Saved ability item slots."));
	}

	@EventHandler
	public void onDialogSubmit(@NonNull PlayerCustomClickEvent event) {
		if (!(event.getCommonConnection() instanceof PlayerGameConnection connection)) {
			return;
		}
		final Player PLAYER = connection.getPlayer();

		final DialogResponseView VIEW = event.getDialogResponseView();
		if (VIEW == null) {
			return;
		}

		Key key = event.getIdentifier();

		switch (key.asString()) {
			case "elytrasoffire:save_player_settings":
				savePlayerSettings(VIEW, PLAYER);
				break;
			case "elytrasoffire:configure_ability_slots":
				try {
					getAbilitySlotMenu(PLAYER).open();
				} catch (NoTribeException e) {
					PLAYER.sendMessage(MessageUtility.buildErrorMessage("You must join a tribe first."));
				}
				break;
		}

	}

	private void savePlayerSettings(@NonNull DialogResponseView view, Player player) {
		PlayerUtility PLAYER_UTILITY = PLUGIN.getPlayerUtility();

		final Boolean ENABLE_ATTRIBUTES = view.getBoolean("enable_attributes");
		final Boolean ENABLE_ABILITIES = view.getBoolean("enable_abilities");

		if (ENABLE_ABILITIES != null) {
			try {
				PLAYER_UTILITY.setPlayerAbilitiesEnabled(player, ENABLE_ABILITIES);
			} catch (NotEnoughAbilitySlotsException e) {
				player.sendMessage(MessageUtility.buildErrorMessage("Your ability slots are not clear, so your abilities cannot be enabled."));
			}
		}
		if (ENABLE_ATTRIBUTES != null) {
			try {
				PLAYER_UTILITY.setPlayerAttributesEnabled(player, ENABLE_ATTRIBUTES);
			} catch (NoTribeException e) {
				player.sendMessage(MessageUtility.buildErrorMessage("You are not in a tribe, so your attributes cannot be enabled."));
			}
		}
	}
}
