package org.woftnw.eof;

import net.kyori.adventure.text.format.TextColor;
import org.woftnw.eof.ability.Ability;
import org.woftnw.eof.ability.ActiveAbility;
import org.woftnw.eof.attribute.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a dragon tribe.
 */
public class Tribe {
//	HIVEWING(
//			"HiveWing",
//			"Hive",
//			NamedTextColor.GOLD,
//			List.of(
//					new Attribute(new PotionEffect(PotionEffectType.STRENGTH, 60, 1))
//			)
//	),
//	ICEWING(
//			"IceWing",
//			"Ice",
//			NamedTextColor.AQUA,
//			List.of(
//					new Attribute(new PotionEffect(PotionEffectType.STRENGTH, 60, 1))
//			)
//	),
//	LEAFWING(
//			"LeafWing",
//			"Leaf",
//			NamedTextColor.DARK_GREEN,
//			List.of(
//					new AttributeModifierAttribute(org.bukkit.attribute.Attribute.MOVEMENT_SPEED,  60, 0)),
//					new Attribute(new PotionEffect(PotionEffectType.STRENGTH, 60, 0))
//			)
//	),
//	MUDWING(
//			"MudWing",
//			"Mud",
//			NamedTextColor.RED,
//			List.of(
//					new Attribute(new PotionEffect(PotionEffectType.STRENGTH, 60, 1)),
//					new Attribute(new PotionEffect(PotionEffectType.SPEED, 60, 0), Entity::isUnderWater),
//					new Attribute(new PotionEffect(PotionEffectType.WATER_BREATHING, 20 * 150, 0), Entity::isInWater, true)
//			)
//	),
//	NIGHTWING(
//			"NightWing",
//			"Night",
//			NamedTextColor.DARK_PURPLE,
//			List.of(
//					new Attribute(new PotionEffect(PotionEffectType.SPEED, 60, 0)),
//					new Attribute(new PotionEffect(PotionEffectType.STRENGTH, 60, 0)),
//					new Attribute(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 30, 0)),
//					new Attribute(new PotionEffect(PotionEffectType.INVISIBILITY, 60, 0), player -> !player.getWorld().isDayTime() && player.isSneaking())
//			)
//	),
//	RAINWING(
//			"RainWing",
//			"Rain",
//			NamedTextColor.GREEN,
//			List.of(
//					new Attribute(new PotionEffect(PotionEffectType.SPEED, 60, 1))
//			)
//	),
//	SANDWING(
//			"SandWing",
//			"Sand",
//			NamedTextColor.YELLOW,
//			List.of(
//					new Attribute(new PotionEffect(PotionEffectType.SPEED, 60, 0)),
//					new Attribute(new PotionEffect(PotionEffectType.STRENGTH, 60, 0))
//			)
//	),
//	SEAWING(
//			"SeaWing",
//			"Sea",
//			NamedTextColor.BLUE,
//			List.of(
//					new Attribute(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 30, 0)),
//					new Attribute(new PotionEffect(PotionEffectType.CONDUIT_POWER, 60, 0), Entity::isInWater),
//					new Attribute(new PotionEffect(PotionEffectType.SPEED, 60, 1), LivingEntity::isSwimming)
//			)
//	),
//	SILKWING(
//			"SilkWing",
//			"Silk",
//			NamedTextColor.LIGHT_PURPLE,
//			List.of(
//					new Attribute(new PotionEffect(PotionEffectType.SPEED, 60, 1))
//			)
//	),
//	SKYWING(
//			"SkyWing",
//			"Sky",
//			NamedTextColor.DARK_RED,
//			List.of(
//					new Attribute(new PotionEffect(PotionEffectType.SPEED, 60, 2)),
//					new Attribute(new PotionEffect(PotionEffectType.STRENGTH, 60, 0))
//			)
//	),
//	HUMAN(
//			"Human",
//			"Human",
//			NamedTextColor.GRAY,
//			List.of()
//	);

	private final String TRIBE_NAME;
	private final String SIMPLE_NAME;
	private final TextColor COLOR;

	private final ArrayList<Attribute> ATTRIBUTES;
	private final ArrayList<Ability> ABILITIES;

	Tribe(String tribeName, String simpleName, TextColor color) {
		TRIBE_NAME = tribeName;
		SIMPLE_NAME = simpleName;
		COLOR = color;
		ATTRIBUTES = new ArrayList<>();
		ABILITIES = new ArrayList<>();
	}

	/**
	 * Get the name of the tribe.
	 * <p>
	 * Example: <code>MUDWING.getTribeName() // "MudWing"</code>
	 *
	 * @return The tribe name.
	 */
	public String getTribeName() {
		return TRIBE_NAME;
	}

	/**
	 * Get the simple name of the tribe.
	 * <p>
	 * Example: <code>MUDWING.getSimpleName() // "Mud"</code>
	 *
	 * @return The simple name.
	 */
	public String getSimpleName() {
		return SIMPLE_NAME;
	}

	/**
	 * Get the text color of a tribe to be used in a {@link net.kyori.adventure.text.TextComponent}.
	 *
	 * @return The {@link TextColor} of the tribe.
	 */
	public TextColor getTextColor() {
		return COLOR;
	}

	/**
	 * Get the value that is used in the persistent data container.
	 *
	 * @return The value for the PDC.
	 */
	public String getPDCValue() {
		return getSimpleName();
	}

	/**
	 * Get the attributes of this tribe.
	 *
	 * @return The attributes this tribe has.
	 */
	public Attribute[] getAttributes() {
		return ATTRIBUTES.toArray(new Attribute[0]);
	}

	public void addAttribute(Attribute attribute) {
		ATTRIBUTES.add(attribute);
	}

	/**
	 * Get the abilities of this tribe.
	 *
	 * @return The abilities this tribe has.
	 */
	public Ability[] getAbilities() {
		return ABILITIES.toArray(new Ability[0]);
	}

	public void addAbility(Ability ability) {
		ABILITIES.add(ability);
	}

	/**
	 * Get the active abilities of this tribe.
	 *
	 * @return The active abilities this tribe has.
	 */
	public List<ActiveAbility> getActiveAbilities() {
		final List<ActiveAbility> ACTIVE_ABILITIES = new ArrayList<>();
		for (final Ability ABILITY : ABILITIES) {
			if (ABILITY instanceof final ActiveAbility ACTIVE_ABILITY) ACTIVE_ABILITIES.add(ACTIVE_ABILITY);
		}
		return ACTIVE_ABILITIES;
	}
}
