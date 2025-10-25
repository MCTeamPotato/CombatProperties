package me.kall.combatproperties.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CombatConfig {
    public static final ForgeConfigSpec INSTANCE;
    public static final ForgeConfigSpec.BooleanValue DISABLE_SHIELD_BLOCK, DISABLE_CRIT_HIT, NOTIFICATION_BLOCK, NOTIFICATION_CRIT;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("Combat Properties Settings");
        DISABLE_SHIELD_BLOCK = builder.define("DisableShieldBlock", true);
        DISABLE_CRIT_HIT = builder.define("DisableCritHit", true);
        NOTIFICATION_BLOCK = builder.comment("If this is enabled, we will send a client chat message to the player who are using shield to block attackers", "Notification only appears once per player").define("BlockNotification", true);
        NOTIFICATION_CRIT = builder.comment("If this is enabled, we will send a client chat message to the player who are trying to critically hit their enemies.", "Notification only appears once per player").define("CritNotification", true);
        builder.pop();
        INSTANCE = builder.build();
    }
}
