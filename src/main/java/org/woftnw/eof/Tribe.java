package org.woftnw.eof;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

/**
 * Represents a dragon tribe.
 */
public enum Tribe {
	HIVEWING(
			"HiveWing",
			"Hive",
			NamedTextColor.GOLD,
			List.of(
					new Attribute(new PotionEffect(PotionEffectType.STRENGTH, 60, 1))
			)
	),
	ICEWING(
			"IceWing",
			"Ice",
			NamedTextColor.AQUA,
			List.of(
					new Attribute(new PotionEffect(PotionEffectType.STRENGTH, 60, 1))
			)
	),
	LEAFWING(
			"LeafWing",
			"Leaf",
			NamedTextColor.DARK_GREEN,
			List.of(
					new Attribute(new PotionEffect(PotionEffectType.SPEED, 60, 0)),
					new Attribute(new PotionEffect(PotionEffectType.STRENGTH, 60, 0))
			)
	),
	MUDWING(
			"MudWing",
			"Mud",
			NamedTextColor.RED,
			List.of(
					new Attribute(new PotionEffect(PotionEffectType.STRENGTH, 60, 1)),
					new Attribute(new PotionEffect(PotionEffectType.SPEED, 60, 0), Entity::isInWater),
					new Attribute(new PotionEffect(PotionEffectType.WATER_BREATHING, 20 * 150, 0), Entity::isInWater, true)
			)
	),
	NIGHTWING(
			"NightWing",
			"Night",
			NamedTextColor.DARK_PURPLE,
			List.of(
					new Attribute(new PotionEffect(PotionEffectType.SPEED, 60, 0)),
					new Attribute(new PotionEffect(PotionEffectType.STRENGTH, 60, 0)),
					new Attribute(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 30, 0)),
					new Attribute(new PotionEffect(PotionEffectType.INVISIBILITY, 60, 0), player -> !player.getWorld().isDayTime() && player.isSneaking())
			)
	),
	RAINWING(
			"RainWing",
			"Rain",
			NamedTextColor.GREEN,
			List.of(
					new Attribute(new PotionEffect(PotionEffectType.SPEED, 60, 1))
			)
	),
	SANDWING(
			"SandWing",
			"Sand",
			NamedTextColor.YELLOW,
			List.of(
					new Attribute(new PotionEffect(PotionEffectType.SPEED, 60, 0)),
					new Attribute(new PotionEffect(PotionEffectType.STRENGTH, 60, 0))
			)
	),
	SEAWING(
			"SeaWing",
			"Sea",
			NamedTextColor.BLUE,
			List.of(
					new Attribute(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 30, 0)),
					new Attribute(new PotionEffect(PotionEffectType.CONDUIT_POWER, 60, 0), Entity::isInWater),
					new Attribute(new PotionEffect(PotionEffectType.SPEED, 60, 1), LivingEntity::isSwimming)
			)
	),
	SILKWING(
			"SilkWing",
			"Silk",
			NamedTextColor.LIGHT_PURPLE,
			List.of(
					new Attribute(new PotionEffect(PotionEffectType.SPEED, 60, 1))
			)
	),
	SKYWING(
			"SkyWing",
			"Sky",
			NamedTextColor.DARK_RED,
			List.of(
					new Attribute(new PotionEffect(PotionEffectType.SPEED, 60, 2)),
					new Attribute(new PotionEffect(PotionEffectType.STRENGTH, 60, 0))
			)
	);

	private final String tribeName;
	private final String simpleName;
	private final TextColor color;

	private final List<Attribute> attributes;

	Tribe(String tribeName, String simpleName, TextColor color, List<Attribute> attributes) {
		this.tribeName = tribeName;
		this.simpleName = simpleName;
		this.color = color;
		this.attributes = attributes;
	}

	/**
	 * Get the name of the tribe.
	 * <p>
	 * Example: <code>MUDWING.getTribeName() // "MudWing"</code>
	 *
	 * @return The tribe name.
	 */
	public String getTribeName() {
		return tribeName;
	}

	/**
	 * Get the simple name of the tribe.
	 * <p>
	 * Example: <code>MUDWING.getSimpleName() // "Mud"</code>
	 *
	 * @return The simple name.
	 */
	public String getSimpleName() {
		return simpleName;
	}

	/**
	 * Get the text color of a tribe to be used in a {@link net.kyori.adventure.text.TextComponent}.
	 *
	 * @return The {@link TextColor} of the tribe.
	 */
	public TextColor getTextColor() {
		return color;
	}

	/**
	 * Get the value that is used in the persistent data container.
	 *
	 * @return The value for the PDC.
	 */
	public String getPDCValue() {
		return getSimpleName();
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}
}
