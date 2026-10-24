package org.woftnw.eof;

import org.jspecify.annotations.Nullable;
import org.woftnw.eof.ability.Ability;
import org.woftnw.eof.attribute.Attribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TribeManager {
	private final ArrayList<Tribe> TRIBES;
	private final AttributeManager ATTRIBUTE_MANAGER;
	private final AbilityManager ABILITY_MANAGER;
	private final ElytrasOfFire PLUGIN;

	public TribeManager(ElytrasOfFire plugin) {
		TRIBES = new ArrayList<>();
		ATTRIBUTE_MANAGER = new AttributeManager(plugin);
		ABILITY_MANAGER = new AbilityManager(plugin);
		PLUGIN = plugin;
	}

	public void disable() {
		ATTRIBUTE_MANAGER.disable();
	}

	/**
	 * Register a tribe to make it known to Elytras of Fire.
	 *
	 * @param tribe The tribe to register.
	 */
	public void register(Tribe tribe) {
		TRIBES.add(tribe);
		for (Attribute attribute : tribe.getAttributes()) {
			ATTRIBUTE_MANAGER.registerAttribute(tribe, attribute);
		}
		for (Ability ability : tribe.getAbilities()) {
			ABILITY_MANAGER.registerAbility(ability);
		}
		final int ATTRIBUTE_COUNT = tribe.getAttributes().length;
		final int ABILITY_COUNT = tribe.getAbilities().length;

		PLUGIN.getLogger().info(String.format(
				"Registered tribe %s with %s attribute%s and %s abilit%s",
				tribe.getPDCValue(),
				ATTRIBUTE_COUNT,
				MessageUtility.singularOrPlural(ATTRIBUTE_COUNT, "", "s"),
				ABILITY_COUNT,
				MessageUtility.singularOrPlural(ABILITY_COUNT, "y", "ies")
		));
	}

	/**
	 * Add an attribute to a tribe.
	 *
	 * @param tribe The tribe to add this attribute to.
	 * @param attribute The attribute to add.
	 */
	public void registerAttribute(Tribe tribe, Attribute attribute) {
		ATTRIBUTE_MANAGER.registerAttribute(tribe, attribute);
	}

	/**
	 * Get a list of all the tribes.
	 *
	 * @return A list of all registered tribes.
	 */
	public List<Tribe> getTribes() {
		return List.copyOf(TRIBES);
	}

	/**
	 * Get a Tribe from a PCD value.
	 *
	 * @param value The value as stored in the PCD.
	 * @return The corresponding Tribe or null of no match.
	 */
	public @Nullable Tribe getTribeFromPDCValue(String value) {
		for (final Tribe TRIBE : TRIBES) {
			if (Objects.equals(TRIBE.getPDCValue(), value)) {
				return TRIBE;
			}
		}
		return null;
	}

	public AbilityManager getAbilityManager() {
		return ABILITY_MANAGER;
	}

	public AttributeManager getAttributeManager() {
		return ATTRIBUTE_MANAGER;
	}
}
