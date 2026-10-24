package org.woftnw.eof.ability.effect;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.woftnw.eof.ElytrasOfFire;
import org.woftnw.eof.ability.effect.target.Target;

import java.util.Collection;

public class DataAbilityEffect extends AbilityEffect {
	private final String DATA_STRING;
	private final boolean APPLY;

	/**
	 * Create an AbilityEffect.
	 *
	 * @param target     The target of the effect.
	 * @param dataString The String to store.
	 */
	public DataAbilityEffect(ElytrasOfFire plugin, Target target, String dataString, boolean apply) {
		super(plugin, target);
		DATA_STRING = dataString;
		APPLY = apply;
	}

	public String getDataString() {
		return DATA_STRING;
	}

	@Override
	public void apply(Player user) {
		final Collection<LivingEntity> TARGETS = TARGET.getTargets(user);
		for (final LivingEntity TARGET : TARGETS) {
			PLUGIN.getPlayerUtility().setEntityAbilityData(TARGET, DATA_STRING, APPLY);
		}
	}
}
