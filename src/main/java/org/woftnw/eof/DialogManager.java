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

import java.util.List;

import static net.kyori.adventure.text.Component.text;

/**
 * Manages Dialogs.
 */
public class DialogManager implements Listener {
	public final static String ABILITY_SLOT_MENU_HOVER_TEXT = "configure_ability_slots";

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

		PLUGIN.getTribeManager().getAbilityManager().putAbilityItemsInInventory(player, inventory.getTopInventory());

		return inventory;
	}

	public static boolean isAbilitySlotMenu(@NonNull InventoryView inventoryView) {
		final HoverEvent<?> HOVER_EVENT = inventoryView.title().hoverEvent();
		return HOVER_EVENT != null && !ABILITY_SLOT_MENU_HOVER_TEXT.equals(HOVER_EVENT.toString());
	}

	@EventHandler
	public void onInventoryClose(@NonNull InventoryCloseEvent event) {
		final Player PLAYER = (Player) event.getPlayer();
		final InventoryView VIEW = event.getView();

		if (!isAbilitySlotMenu(VIEW)) return;

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

		if (PLAYER_UTILITY.arePlayerPlayerAbilitiesEnabled(PLAYER)) {
			PLAYER_UTILITY.disablePlayerAbilities(PLAYER);
			try {
				PLAYER_UTILITY.setPlayerAbilitiesEnabled(PLAYER, true);
			} catch (NoTribeException e) {
				// Do nothing; not re-enabling the abilities is acceptable behavior.
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
			} catch (NoTribeException e) {
				player.sendMessage(MessageUtility.buildErrorMessage("You must join a tribe first."));
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
