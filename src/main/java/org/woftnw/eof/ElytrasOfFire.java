package org.woftnw.eof;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;
import org.woftnw.eof.ability.effect.AbilityEffect;
import org.woftnw.eof.command.RootCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * The main ElytrasOfFire plugin class.
 */
public class ElytrasOfFire extends JavaPlugin {

	/**
	 * Stores all available {@link AbilityEffect} classes.
	 * You can register one with <code>registerAbilityEffect()</code>.
	 */
	private final Map<NamespacedKey, Class<? extends AbilityEffect>> abilityEffectTypes = new HashMap<>();
	private PlayerUtility playerUtility;
	private TribeManager tribeManager;

	@Override
	public void onEnable() {
		playerUtility = new PlayerUtility(this);
		tribeManager = new TribeManager(this);

		// Register commands
		this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
			LiteralCommandNode<CommandSourceStack> rootCommand = new RootCommand(this).getCommandData().build();
			event.registrar().register(rootCommand);
		});
	}

	/**
	 * Register an ability effect type. This will make it available to the feature loader.
	 * For example:
	 * <pre>
	 * // Get the ElytrasOfFire plugin.
	 * RegisteredServiceProvider<ElytrasOfFire> provider = Bukkit.getServicesManager().getRegistration(ElytrasOfFire.class);
	 * if (provider != null) {
	 *     ElytrasOfFire eof = provider.getProvider();
	 *     // Create a key for the effect type.
	 *     NamespacedKey key = new NamespacedKey(this, "my_custom_ability_effect");
	 *     // Register the effect type. In this case, MyCustomAbilityEffect is a class that extends AbilityEffect.
	 *     eof.registerAbilityEffectType(key, MyCustomAbilityEffect.class);
	 * }
	 * </pre>
	 *
	 * @param key                The namespaced key of the ability effect type.
	 *                           The namespace should be of the origin plugin, not ElytrasOfFire.
	 * @param abilityEffectClass The class of the ability effect which extends {@link AbilityEffect}.
	 * @see AbilityEffect
	 */
	public void registerAbilityEffectType(@NonNull NamespacedKey key, @NonNull Class<? extends AbilityEffect> abilityEffectClass) {
		abilityEffectTypes.put(key, abilityEffectClass);
	}

	/**
	 * Get a copy of the ability effect type map.
	 *
	 * @return An unmodifiable map of keys and ability effect classes that have been registered.
	 */
	public Map<NamespacedKey, Class<? extends AbilityEffect>> getAbilityEffectTypes() {
		return Map.copyOf(abilityEffectTypes);
	}

	/**
	 * Get the <code>TribeManager</code> instance.
	 *
	 * @return The <code>TribeManager</code> instance.
	 */
	public TribeManager getTribeManager() {
		return tribeManager;
	}

	@Override
	public void onDisable() {
		tribeManager.disable();
	}

	/**
	 * Get the PlayerUtility.
	 *
	 * @return The PlayerUtility instance.
	 */
	public PlayerUtility getPlayerUtility() {
		return playerUtility;
	}
}
