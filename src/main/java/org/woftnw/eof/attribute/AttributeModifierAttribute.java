package org.woftnw.eof.attribute;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.woftnw.eof.ElytrasOfFire;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Represents a tribal attribute that uses an attribute modifier.
 */
public class AttributeModifierAttribute extends Attribute {
	private final org.bukkit.attribute.Attribute ATTRIBUTE_TYPE;
	private final AttributeModifier ATTRIBUTE_MODIFIER;

	/**
	 * Create a tribe attribute using an attribute modifier.
	 *
	 * @param attributeType The attribute to modify.
	 * @param plugin The plugin instance.
	 * @param amount The amount to use for the attribute modifier.
	 * @param operation The operation to use to apply the attribute modifier.
	 * @param predicate The predicate that must be true to apply the attribute or null if it should always be applied.
	 * @param refreshOnPredicate Whether this attribute should only refresh when the predicate switches from false to true.
	 */
	public AttributeModifierAttribute(
			org.bukkit.attribute.@NonNull Attribute attributeType,
			ElytrasOfFire plugin,
			double amount,
			AttributeModifier.Operation operation,
			@Nullable Predicate<Player> predicate,
			boolean refreshOnPredicate
	) {
		super(predicate, refreshOnPredicate);
		NamespacedKey key = NamespacedKey.fromString(String.format("attribute_%s", attributeType.key().asMinimalString()), plugin);
		ATTRIBUTE_TYPE = attributeType;
		ATTRIBUTE_MODIFIER = new AttributeModifier(Objects.requireNonNull(key), amount, operation);
	}

	/**
	 * Create a tribe attribute using an attribute modifier.
	 *
	 * @param attributeType The attribute modifier type to apply.
	 * @param plugin The plugin instance.
	 * @param amount The amount to use for the attribute modifier.
	 * @param operation The operation to use to apply the attribute modifier.
	 * @param predicate The predicate that must be true to apply the attribute or null if it should always be applied.
	 */
	public AttributeModifierAttribute(
			org.bukkit.attribute.@NonNull Attribute attributeType,
			ElytrasOfFire plugin,
			double amount,
			AttributeModifier.Operation operation,
			@Nullable Predicate<Player> predicate
	) {
		this(attributeType, plugin, amount, operation, predicate, false);
	}

	/**
	 * Create a tribe attribute using an attribute modifier.
	 *
	 * @param attributeType The attribute modifier type to apply.
	 * @param plugin The plugin instance.
	 * @param amount The amount to use for the attribute modifier.
	 * @param operation The operation to use to apply the attribute modifier.
	 */
	public AttributeModifierAttribute(
			org.bukkit.attribute.@NonNull Attribute attributeType,
			ElytrasOfFire plugin,
			double amount,
			AttributeModifier.Operation operation
	) {
		this(attributeType, plugin, amount, operation, null, false);
	}

	@Override
	public void tick(Player player) {
		if (PREDICATE == null) return;

		final boolean TEST = PREDICATE.test(player);
		if (TEST && !lastTest) {
			apply(player);
		} else if (!TEST && lastTest) {
			unapply(player);
		}
	}

	@Override
	public void apply(@NonNull Player player) {
		AttributeInstance attribute = Objects.requireNonNull(player.getAttribute(ATTRIBUTE_TYPE));
		attribute.addModifier(ATTRIBUTE_MODIFIER);
	}

	@Override
	public void unapply(@NonNull Player player) {
		AttributeInstance attribute = Objects.requireNonNull(player.getAttribute(ATTRIBUTE_TYPE));
		attribute.removeModifier(ATTRIBUTE_MODIFIER);
	}
}
