package org.woftnw.eof;

import org.woftnw.eof.ability.AbilityFlag;
import org.woftnw.eof.attribute.AttributeFlag;

public class InitFlags {
    // Load the flags
    void LoadFlags() {
        new AttributeFlag().LoadFlag();
        new AbilityFlag().LoadFlag();
    }
}
