package org.woftnw.eof;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

/**
 * Represents a dragon tribe.
 */
public enum Tribe {
	HIVEWING("HiveWing", "Hive", NamedTextColor.GOLD),
	ICEWING("IceWing", "Ice", NamedTextColor.AQUA),
	LEAFWING("LeafWing", "Leaf", NamedTextColor.DARK_GREEN),
	MUDWING("MudWing", "Mud", NamedTextColor.RED),
	NIGHTWING("NightWing", "Night", NamedTextColor.DARK_PURPLE),
	RAINWING("RainWing", "Rain", NamedTextColor.GREEN),
	SANDWING("SandWing", "Sand", NamedTextColor.YELLOW),
	SEAWING("SeaWing", "Sea", NamedTextColor.BLUE),
	SILKWING("SilkWing", "Silk", NamedTextColor.LIGHT_PURPLE),
	SKYWING("SkyWing", "Sky", NamedTextColor.DARK_RED);

	private final String tribeName;
	private final String simpleName;
	private final TextColor color;

	Tribe(String tribeName, String simpleName, TextColor color) {
		this.tribeName = tribeName;
		this.simpleName = simpleName;
		this.color = color;
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
}
