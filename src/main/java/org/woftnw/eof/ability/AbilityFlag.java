package org.woftnw.eof.ability;

import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.entity.Player;

public class AbilityFlag {
    public static StateFlag ABILITY_FLAG;

    public void LoadFlag() {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();

        try {
            StateFlag abilityFlag = new StateFlag("eof-abilities", true);
            registry.register(abilityFlag);
            ABILITY_FLAG = abilityFlag;
        } catch (FlagConflictException e) {
            Flag<?> existing = registry.get("eof-abilities");
            if (existing instanceof StateFlag) {
                ABILITY_FLAG = (StateFlag) existing;
            } else {
                // We do not have a logger
            }
        }
    }
    public static boolean getState(Player player) {
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        if (!query.testState(localPlayer.getLocation(), localPlayer, ABILITY_FLAG)) {
            return true;
        }
        return false;
    }
}
