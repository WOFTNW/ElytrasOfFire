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
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.exception.NoTribeException;
import org.woftnw.eof.exception.NotEnoughAbilitySlotsException;

import java.util.List;

import static net.kyori.adventure.text.Component.text;

public class DialogManager implements Listener {
	final ElytrasOfFire PLUGIN;

	public DialogManager(@NonNull ElytrasOfFire plugin) {
		PLUGIN = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

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
				.type(DialogType.confirmation(
						ActionButton.builder(text("Save and Apply", NamedTextColor.GREEN))
								.action(DialogAction.customClick(Key.key("elytrasoffire:save_player_settings"), null))
								.build(),
						ActionButton.builder(text("Discard Changes", NamedTextColor.RED))
								.action(null)
								.build()
				))
		);
	}

	@EventHandler
	void onDialogSubmit(@NonNull PlayerCustomClickEvent event) {
		if (!(event.getCommonConnection() instanceof PlayerGameConnection connection)) {
			return;
		}
		final Player PLAYER = connection.getPlayer();
		PlayerUtility PLAYER_UTILITY = PLUGIN.getPlayerUtility();

		final DialogResponseView VIEW = event.getDialogResponseView();
		if (VIEW == null) {
			return;
		}

		final Boolean ENABLE_ATTRIBUTES = VIEW.getBoolean("enable_attributes");
		final Boolean ENABLE_ABILITIES = VIEW.getBoolean("enable_abilities");

		Key key = event.getIdentifier();
		if (key.equals(Key.key("elytrasoffire:save_player_settings"))) {
			if (ENABLE_ABILITIES != null) {
				try {
					PLAYER_UTILITY.setPlayerAbilitiesEnabled(PLAYER, ENABLE_ABILITIES);
				} catch (NotEnoughAbilitySlotsException e) {
					PLAYER.sendMessage(MessageUtility.buildErrorMessage("Your ability slots are not clear, so your abilities cannot be enabled."));
				}
			}
			if (ENABLE_ATTRIBUTES != null) {
				try {
					PLAYER_UTILITY.setPlayerAttributesEnabled(PLAYER, ENABLE_ATTRIBUTES);
				} catch (NoTribeException e) {
					PLAYER.sendMessage(MessageUtility.buildErrorMessage("You are not in a tribe, so your attributes cannot be enabled."));
				}
			}
		}
	}
}
