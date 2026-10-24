package org.woftnw.eof.ability.mudwing;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.woftnw.eof.ability.Activation;
import org.woftnw.eof.ability.RayCastAbility;
import org.woftnw.eof.exception.AbilityActivationException;

import java.util.List;

import static net.kyori.adventure.text.Component.text;

public class FlameBreath extends RayCastAbility {
	private static final TextComponent NAME = text("Flame Breath").color(NamedTextColor.RED);
	private static final PotionEffect[] SELF_EFFECTS = new PotionEffect[0];
	private static final Activation ACTIVATION_METHOD = Activation.SNEAK_USE;
	private static final Particle PARTICLE = Particle.FLAME;

	private static final float DAMAGE = 2.5f;
	private static final int FIRE_TICKS = 20 * 3;

	public FlameBreath(ItemStack activationItem) {
		super(NAME, SELF_EFFECTS, activationItem, ACTIVATION_METHOD, PARTICLE);
	}

	@Override
	public void activate(Player player) throws AbilityActivationException {
		super.activate(player);
		List<Player> hitPlayers = super.getHitPlayers(player);
		// TODO: remove frostbite

		for (Player hitPlayer : hitPlayers) {
			hitPlayer.damage(DAMAGE);
			hitPlayer.setFireTicks(FIRE_TICKS);
			// TODO: remove frostbite
		}
	}
}
