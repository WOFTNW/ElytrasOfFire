package org.woftnw.eof.attribute;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;

public class AttributeFlag {
    public static StateFlag ATTRIBUTE_FLAG;

    public void LoadFlag() {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();

        try {
            StateFlag attributesFlag = new StateFlag("eof-attributes", true);
            registry.register(attributesFlag);
            ATTRIBUTE_FLAG = attributesFlag;
        } catch (FlagConflictException e) {
            Flag<?> existing = registry.get("eof-attributes");
            if (existing instanceof StateFlag) {
                ATTRIBUTE_FLAG = (StateFlag) existing;
            } else {
                // We do not have a logger
            }
            }
        }
}
