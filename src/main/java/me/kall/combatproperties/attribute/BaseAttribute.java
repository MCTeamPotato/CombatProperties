package me.kall.combatproperties.attribute;

import me.kall.combatproperties.CombatProperties;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class BaseAttribute extends RangedAttribute {
    public BaseAttribute(String id) {
        super("attribute." + CombatProperties.MOD_ID + "." + id, 0.00, 0.00, 1.00);
        this.setSyncable(true);
    }

    public static double calChance(double positive, double negative) {
        return Math.max(0.00, Math.min(1.00, positive - negative));
    }
}
